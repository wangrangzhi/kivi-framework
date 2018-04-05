package com.kivi.framework.dao;

import com.kivi.framework.db.dao.IDao;
import com.kivi.framework.persist.model.KtfUser;
import com.kivi.framework.vo.web.UserVO;

public interface UserDao extends IDao<KtfUser> {

	/**
	 * 根据用户账号查询用户信息
	 * 
	 * @param acctount
	 * @return
	 */
	UserVO getUserByAccount(String account);
}
