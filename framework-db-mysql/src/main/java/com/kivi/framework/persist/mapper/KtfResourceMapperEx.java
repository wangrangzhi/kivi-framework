package com.kivi.framework.persist.mapper;

import java.util.List;
import java.util.Map;

import com.kivi.framework.vo.web.ResourceVO;

public interface KtfResourceMapperEx {

    /**
     * 根据IDs查询资源名称
     * 
     * @param ids
     * @return
     */
    List<String> listResourceNameByIds( List<Long> ids );

    /**
     * 根据用户查询资源列表
     * 
     * @param map
     * @return
     */
    public List<ResourceVO> listUserResources( Map<String, Object> map );

    /**
     * 根据角色Id查询资源列表
     * 
     * @param rid
     * @return
     */
    public List<ResourceVO> listRoleResources( Long rid );
}
