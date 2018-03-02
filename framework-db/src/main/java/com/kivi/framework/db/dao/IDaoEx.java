package com.kivi.framework.db.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 通用接口
 */
@Repository
public interface IDaoEx<T> extends IDao<T> {

	int saveNotNull(Map<String, Object> map);

}
