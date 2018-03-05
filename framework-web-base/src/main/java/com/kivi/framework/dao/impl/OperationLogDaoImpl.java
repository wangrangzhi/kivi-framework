package com.kivi.framework.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kivi.framework.dao.OperationLogDao;
import com.kivi.framework.db.dao.BaseDao;
import com.kivi.framework.persist.model.KtfOperationLog;
import com.kivi.framework.util.kit.BeanKit;

@Repository
public class OperationLogDaoImpl extends BaseDao<KtfOperationLog> implements OperationLogDao {

	@Override
	public int saveNotNull(Map<String, Object> map) {
		KtfOperationLog entity = BeanKit.mapToBean(map, KtfOperationLog.class);

		return super.mapper.insertSelective(entity);
	}

}
