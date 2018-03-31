package com.kivi.framework.persist.mapper;

import java.util.List;

import com.kivi.framework.vo.web.RoleVO;

public interface KtfRoleMapperEx {

	/**
	 * 根据用户列出全部角色信息
	 * 
	 * @param id
	 * @return
	 */
	public List<RoleVO> listRoleByUserId(Long userId);

	/**
	 * 返回全部角色列表，标记那个角色被用用户选中
	 * 
	 * @param id
	 * @return
	 */
	public List<RoleVO> listRoleWithSelected(Long userId);

	/**
	 * 根据Ids对应的role名称
	 * 
	 * @param ids
	 * @return
	 */
	List<String> listRoleNameByIds(List<Long> ids);

}
