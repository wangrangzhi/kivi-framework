package com.kivi.framework.db.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kivi.framework.db.page.PageInfoKtf;
import com.kivi.framework.vo.page.PageReqVO;


/**
 * 通用接口
 */
@Repository
public interface IDao<T> {

	T selectByKey(Object key);

	T save(T entity);
	
	T saveNotNull(T entity);

	int delete(Object key);

	T updateAll(T entity);

	T updateNotNull(T entity);

	/**
	 * 根据条件查询
	 * 
	 * @param example
	 * @return
	 */
	List<T> selectByExample(Object example);

	/**
	 * 根据条件查询
	 * 
	 * @param entity
	 * @return
	 */
	List<T> selectByEntity(T entity);

	/**
	 * 查询总数量
	 * 
	 * @return
	 */
	int count(T entity);

	int count();
	
	/**
	 * 分页查询
	 * 
	 * @return
	 */
	PageInfoKtf<T> selectByPage(T entity, PageReqVO pageReq);
}
