package com.kivi.framework.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kivi.framework.dao.RoleResourceDao;
import com.kivi.framework.db.dao.BaseDao;
import com.kivi.framework.persist.model.KtfRoleResource;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.web.RoleResourceVO;

import tk.mybatis.mapper.entity.Example;

@Repository
public class RoleResourceDaoImpl extends BaseDao<KtfRoleResource> implements RoleResourceDao {

	@Override
	@Transactional
	public void addRoleResources(RoleResourceVO roleResources) {
		// 删除原权限
		this.deleteRoleResources(roleResources.getRoleId());

		// 添加
		if (StrKit.isNotEmpty(roleResources.getResourcesId())) {
			String[] resourcesArr = roleResources.getResourcesId().split(",");
			for (String resourcesId : resourcesArr) {
				KtfRoleResource r = new KtfRoleResource();
				r.setRoleId(roleResources.getRoleId());
				r.setResourceId(Long.parseLong(resourcesId));
				mapper.insert(r);
			}
		}
	}

	@Override
	public int deleteRoleResources(Long roleId) {
		// 删除
		Example example = new Example(KtfRoleResource.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("role_id", roleId);

		return mapper.deleteByExample(example);
	}

}
