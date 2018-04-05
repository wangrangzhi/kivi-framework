package com.kivi.framework.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kivi.framework.dao.ResourceDao;
import com.kivi.framework.db.dao.BaseDao;
import com.kivi.framework.persist.mapper.KtfResourceMapperEx;
import com.kivi.framework.persist.model.KtfResource;
import com.kivi.framework.vo.web.ResourceVO;

@Repository
public class ResourceDaoImpl extends BaseDao<KtfResource> implements ResourceDao {

	@Autowired
	private KtfResourceMapperEx jtfResourceMapperEx;

	@Override
	public List<ResourceVO> listUserResources(Long userId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);

		return jtfResourceMapperEx.listUserResources(map);
	}

	@Override
	public List<ResourceVO> listRoleResources(Long rid) {
		return jtfResourceMapperEx.listRoleResources(rid);
	}

	@Override
	public Set<String> selectPermissionUrl(Long rid) {
		List<ResourceVO> list = this.listRoleResources(rid);
		Set<String> permissions = new HashSet<String>();
		for (ResourceVO r : list) {
			permissions.add(r.getUrl());
		}

		return permissions;
	}

	@Override
	public List<String> listResourceNameByIds(Long[] ids) {
		return jtfResourceMapperEx.listResourceNameByIds(Arrays.asList(ids));
	}

	@Override
	public Set<String> selectPermissions(Long userId) {
		List<ResourceVO> list = this.listUserResources(userId);

		Set<String> permissions = new HashSet<String>();
		for (ResourceVO r : list) {
			permissions.add(r.getUrl());
		}

		return permissions;
	}

}
