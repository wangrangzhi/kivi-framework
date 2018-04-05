package com.kivi.framework.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kivi.framework.dao.RoleDao;
import com.kivi.framework.dao.RoleResourceDao;
import com.kivi.framework.db.dao.BaseDao;
import com.kivi.framework.persist.mapper.KtfRoleMapperEx;
import com.kivi.framework.persist.model.KtfRole;
import com.kivi.framework.vo.web.RoleVO;

@Repository
public class RoleDaoImpl extends BaseDao<KtfRole> implements RoleDao {

	@Autowired
	private RoleResourceDao roleResourceDao;

	@Autowired
	private KtfRoleMapperEx jtfRoleMapperEx;

	@Override
	@Transactional
	public void deleteRole(Long roleId) {
		// 删除角色
		super.delete(roleId);

		// 删除角色资源
		roleResourceDao.deleteRoleResources(roleId);
	}

	@Override
	public List<RoleVO> listRoleByUserId(Long id) {
		return jtfRoleMapperEx.listRoleByUserId(id);
	}

	@Override
	public List<RoleVO> listRoleWithSelected(Long id) {
		return jtfRoleMapperEx.listRoleWithSelected(id);
	}

	public List<String> listRoleNameByIds(Long[] ids) {
		return jtfRoleMapperEx.listRoleNameByIds(Arrays.asList(ids));
	}

}
