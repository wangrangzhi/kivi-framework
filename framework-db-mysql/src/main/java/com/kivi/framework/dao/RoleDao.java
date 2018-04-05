package com.kivi.framework.dao;

import java.util.List;

import com.kivi.framework.db.dao.IDao;
import com.kivi.framework.persist.model.KtfRole;
import com.kivi.framework.vo.web.RoleVO;

public interface RoleDao extends IDao<KtfRole> {

	/**
	 * 根据Ids对应的role名称
	 * 
	 * @param ids
	 * @return
	 */
	List<String> listRoleNameByIds(Long[] ids);

	/**
	 * 根据用户列出全部角色信息
	 * 
	 * @param id
	 * @return
	 */
	public List<RoleVO> listRoleByUserId(Long id);

	/**
	 * 返回全部角色列表，标记那个角色被用用户选中
	 * 
	 * @param id
	 * @return
	 */
	public List<RoleVO> listRoleWithSelected(Long id);

	/**
	 * 删除角色 同时删除角色资源表中的数据
	 * 
	 * @param roleid
	 */
	public void deleteRole(Long roleid);
}
