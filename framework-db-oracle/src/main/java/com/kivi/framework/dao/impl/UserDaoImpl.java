package com.kivi.framework.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kivi.framework.dao.UserDao;
import com.kivi.framework.db.dao.BaseDao;
import com.kivi.framework.persist.mapper.KtfUserMapperEx;
import com.kivi.framework.persist.model.KtfUser;
import com.kivi.framework.vo.web.UserVO;

@Repository
public class UserDaoImpl extends BaseDao<KtfUser> implements UserDao {

	@Autowired
	private KtfUserMapperEx jtfUserMapperEx;

	@Override
	public UserVO getUserByAccount(String acctount) {

		UserVO vo = jtfUserMapperEx.selectUserByAccount(acctount);
		return vo;
	}

}
