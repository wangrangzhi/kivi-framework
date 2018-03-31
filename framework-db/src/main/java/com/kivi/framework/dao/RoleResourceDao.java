package com.kivi.framework.dao;

import com.kivi.framework.db.dao.IDao;
import com.kivi.framework.persist.model.KtfRoleResource;
import com.kivi.framework.vo.web.RoleResourceVO;

/**
 */
public interface RoleResourceDao extends IDao<KtfRoleResource>  {
	/**
	 * 给role添加一个或多个resource
	 * @param roleResources
	 */
    void addRoleResources(RoleResourceVO roleResources);
    
    /**
     * 删除角色关联的一个或多个resource
     * @param roleId
     * @return
     */
    int deleteRoleResources(Long roleId ) ;
}
