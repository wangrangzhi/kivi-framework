package com.kivi.framework.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kivi.framework.dao.LoginLogDao;
import com.kivi.framework.db.dao.BaseDao;
import com.kivi.framework.persist.model.KtfLoginLog;
import com.kivi.framework.util.kit.BeanKit;

@Repository
public class LoginLogDaoImpl extends BaseDao<KtfLoginLog> implements LoginLogDao {

	@Override
	public int saveNotNull(Map<String, Object> map) {
		KtfLoginLog entity = BeanKit.mapToBean(map, KtfLoginLog.class);

		return super.mapper.insertSelective(entity);
	}

}
