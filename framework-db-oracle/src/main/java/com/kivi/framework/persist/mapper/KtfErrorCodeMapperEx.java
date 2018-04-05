package com.kivi.framework.persist.mapper;

import java.util.List;

import com.kivi.framework.persist.model.KtfErrorCode;

/**
 * 重要提示：不使用Mapper工具的Mapper需要和*mapper.xml文件在相同的包中，
 * 否则会报错误：org.apache.ibatis.binding.BindingException: Invalid bound statement
 * (not found)
 * 
 * @author Eric
 *
 */
public interface KtfErrorCodeMapperEx {

    /**
     * 批量保存
     * 
     * @param list
     */
    public void insertBatch( List<KtfErrorCode> list );

}
