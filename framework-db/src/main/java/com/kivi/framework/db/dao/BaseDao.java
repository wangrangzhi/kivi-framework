package com.kivi.framework.db.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kivi.framework.db.page.PageInfoKtf;
import com.kivi.framework.exception.DaoException;
import com.kivi.framework.util.kit.BeanKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.vo.page.PageReqVO;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

/**
 */
public abstract class BaseDao<T> implements IDao<T> {
    private static final Logger log         = LoggerFactory.getLogger(BaseDao.class);

    private Class<T>            entityClass;

    private ThreadLocal<T>      entityLocal = new ThreadLocal<T>() {
                                                protected T initialValue() {
                                                    T obj = null;
                                                    try {
                                                        obj = entityClass.newInstance();
                                                    }
                                                    catch (InstantiationException | IllegalAccessException e) {
                                                        log.error("实例化对象" + entityClass.getName() + "异常", e);
                                                    }

                                                    return obj;
                                                }
                                            };

    @Autowired
    protected Mapper<T>         mapper;

    @SuppressWarnings( "unchecked" )
    public BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = ((Class<T>) params[0]);
    }

    protected T getEntityObject() {
        T obj = BeanKit.fillBeanNull(entityLocal.get());
        return obj;
    }

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey( Object key ) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public T save( T entity ) {
        try {
            int row = mapper.insert(entity);
            if (row != 1)
                throw new DaoException("插入数据影响行数不等于1");
        }
        catch (Exception e) {
            throw new DaoException("插入数据异常：" + e.getMessage());
        }

        return entity;
    }

    @Override
    public T saveNotNull( T entity ) {
        try {
            int row = mapper.insertSelective(entity);
            if (row != 1)
                throw new DaoException("插入数据影响行数不等于1");
        }
        catch (Exception e) {
            throw new DaoException("插入数据异常：" + e.getMessage(), e);
        }

        return entity;
    }

    @Override
    public int delete( Object key ) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public T updateAll( T entity ) {
        try {
            int row = mapper.updateByPrimaryKey(entity);
            if (row != 1)
                throw new DaoException("更新数据影响行数不等于1");
        }
        catch (Exception e) {
            throw new DaoException("更新数据异常：" + e.getMessage());
        }
        return entity;
    }

    @Override
    public T updateNotNull( T entity ) {
        try {
            int row = mapper.updateByPrimaryKeySelective(entity);
            if (row != 1)
                throw new DaoException("更新数据影响行数不等于1");
        }
        catch (Exception e) {
            throw new DaoException("更新数据异常：" + e.getMessage());
        }
        return entity;
    }

    @Override
    public List<T> selectByExample( Object example ) {
        return mapper.selectByExample(example);
    }

    public PageInfoKtf<T> selectPageByExample( Object example, PageReqVO pageReq ) {
        int page = pageReq.getOffset() / pageReq.getLimit() + 1;
        PageHelper.startPage(page, pageReq.getLimit());
        List<T> list = mapper.selectByExample(example);
        return new PageInfoKtf<T>(new PageInfo<>(list));
    }

    @Override
    public PageInfoKtf<T> selectByPage( T entity, PageReqVO pageReq ) {
        int page = pageReq.getOffset() / pageReq.getLimit() + 1;
        PageHelper.startPage(page, pageReq.getLimit());
        List<T> list = this.selectByEntity(entity);
        return new PageInfoKtf<T>(new PageInfo<>(list));
    }

    @Override
    public int count( T entity ) {

        return mapper.selectCount(entity);
    }

    @Override
    public int count() {
        Example example = new Example(entityClass);

        return mapper.selectCountByExample(example);
    }

    @Override
    public List<T> selectByEntity( T entity ) {
        Map<String, Object> map = BeanKit.beanToMap(entity);

        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();

        Iterator<Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Object> entry = it.next();
            if (ObjectKit.isEmpty(entry.getValue()))
                continue;
            criteria.andEqualTo(entry.getKey(), entry.getValue());
        }

        return this.selectByExample(example);
    }

    /**
     * 查询全部资源
     * 
     * @return
     */
    public List<T> queryAll() {
        Example example = new Example(entityClass);
        return this.selectByExample(example);
    }

    // TODO 其他...
}
