package com.kivi.framework.dao;

import java.util.List;
import java.util.Set;

import com.kivi.framework.db.dao.IDao;
import com.kivi.framework.persist.model.KtfResource;
import com.kivi.framework.web.vo.ResourceVO;

/**
 */
public interface ResourceDao extends IDao<KtfResource> {

	/**
	 * 根据ids查询资源名称
	 * 
	 * @param ids
	 * @return
	 */
	public List<String> listResourceNameByIds(Long[] ids);

	/**
	 * 根据用户ID询用户的权限列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<ResourceVO> listUserResources(Long userId);

	/**
	 * 根据角色ID查询权限列表
	 * 
	 * @param rid
	 * @return
	 */
	public List<ResourceVO> listRoleResources(Long rid);

	/**
	 * 根据角色id获取权限URL
	 * 
	 * @param rid
	 * @return
	 */
	Set<String> selectPermissionUrl(Long rid);

	Set<String> selectPermissions(Long userId);
}
