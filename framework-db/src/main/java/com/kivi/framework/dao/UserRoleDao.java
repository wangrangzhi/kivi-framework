package com.kivi.framework.dao;

import com.kivi.framework.db.dao.IDao;
import com.kivi.framework.persist.model.KtfUserRole;
import com.kivi.framework.vo.web.UserRoleVO;

/**
 */
public interface UserRoleDao extends IDao<KtfUserRole> {

	/**
	 * 增加用户角色
	 * 
	 * @param userRole
	 */
	void addUserRole(UserRoleVO userRole);

	/**
	 * 删除用户的关联角色
	 * 
	 * @param userId
	 * @return
	 */
	int deleteUserRole(Long userId);
}
