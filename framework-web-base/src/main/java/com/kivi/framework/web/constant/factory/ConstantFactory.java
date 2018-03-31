package com.kivi.framework.web.constant.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.cache.constant.KtfCacheKey;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.constant.enums.KtfStatus;
import com.kivi.framework.dao.ResourceDao;
import com.kivi.framework.dao.RoleDao;
import com.kivi.framework.dao.UserDao;
import com.kivi.framework.dao.UserRoleDao;
import com.kivi.framework.persist.dao.KtfDeptMapper;
import com.kivi.framework.persist.dao.KtfDictMapper;
import com.kivi.framework.persist.dao.KtfNoticeMapper;
import com.kivi.framework.persist.model.KtfDept;
import com.kivi.framework.persist.model.KtfDict;
import com.kivi.framework.persist.model.KtfNotice;
import com.kivi.framework.persist.model.KtfResource;
import com.kivi.framework.persist.model.KtfRole;
import com.kivi.framework.persist.model.KtfUser;
import com.kivi.framework.persist.model.KtfUserRole;
import com.kivi.framework.util.Convert;
import com.kivi.framework.util.kit.CollectionKit;
import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.util.kit.StrKit;
import com.kivi.framework.vo.web.UserVO;

import tk.mybatis.mapper.entity.Example;

/**
 * 常量的生产工厂
 *
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

	private RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private UserRoleDao userRoleDao = SpringContextHolder.getBean(UserRoleDao.class);
	private UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private ResourceDao resourceDao = SpringContextHolder.getBean(ResourceDao.class);

	private KtfDeptMapper deptMapper = SpringContextHolder.getBean(KtfDeptMapper.class);
	private KtfDictMapper dictMapper = SpringContextHolder.getBean(KtfDictMapper.class);
	private KtfNoticeMapper noticeMapper = SpringContextHolder.getBean(KtfNoticeMapper.class);

	public static IConstantFactory me() {
		return SpringContextHolder.getBean("constantFactory");
	}

	public UserVO getUser(String account) {

		return userDao.getUserByAccount(account);
	}

	/**
	 * 根据用户id获取用户名称
	 *
	 */
	@Override
	public String getUserNameById(Long userId) {
		KtfUser user = userDao.selectByKey(userId);
		if (user != null) {
			return user.getName();
		} else {
			return "--";
		}
	}

	/**
	 * 根据用户id获取用户账号
	 *
	 */
	@Override
	public String getUserAccountById(Long userId) {
		KtfUser user = userDao.selectByKey(userId);
		if (user != null) {
			return user.getAccount();
		} else {
			return "--";
		}
	}

	@Override
	public String getRoleIdsByUserId(Long userId) {
		KtfUserRole entity = new KtfUserRole();
		List<KtfUserRole> list = userRoleDao.selectByEntity(entity);

		StringBuilder builder = StrKit.builder();
		for (KtfUserRole ur : list) {
			builder.append(ur.getRoleId()).append(",");
		}

		return StrKit.removeSuffix(builder.toString(), ",");
	}

	/**
	 * 通过角色ids获取角色名称
	 */
	@Override
	@Cacheable(value = KtfCache.CONSTANT, key = "'" + KtfCacheKey.ROLES_NAME + "'+#roleIds")
	public String getRoleName(String roleIds) {
		Long[] roles = Convert.toLongArray(true, roleIds);

		List<String> names = roleDao.listRoleNameByIds(roles);

		return CollectionKit.join(names, ",");
	}

	/**
	 * 通过角色id获取角色名称
	 */
	@Override
	@Cacheable(value = KtfCache.CONSTANT, key = "'" + KtfCacheKey.SINGLE_ROLE_NAME + "'+#roleId")
	public String getSingleRoleName(Long roleId) {
		if (0 == roleId) {
			return "--";
		}
		KtfRole roleObj = roleDao.selectByKey(roleId);
		if (ObjectKit.isNotEmpty(roleObj) && ObjectKit.isNotEmpty(roleObj.getName())) {
			return roleObj.getName();
		}
		return "--";
	}

	/**
	 * 通过角色id获取角色英文名称
	 */
	@Override
	@Cacheable(value = KtfCache.CONSTANT, key = "'" + KtfCacheKey.SINGLE_ROLE_TIP + "'+#roleId")
	public String getSingleRoleTip(Long roleId) {
		if (0 == roleId) {
			return "--";
		}
		KtfRole roleObj = roleDao.selectByKey(roleId);
		if (ObjectKit.isNotEmpty(roleObj) && ObjectKit.isNotEmpty(roleObj.getName())) {
			return roleObj.getTips();
		}
		return "--";
	}

	/**
	 * 获取部门名称
	 */
	@Override
	@Cacheable(value = KtfCache.CONSTANT, key = "'" + KtfCacheKey.DEPT_NAME + "'+#deptId")
	public String getDeptName(Long deptId) {
		KtfDept dept = deptMapper.selectByPrimaryKey(deptId);
		if (ObjectKit.isNotEmpty(dept) && ObjectKit.isNotEmpty(dept.getFullname())) {
			return dept.getFullname();
		}
		return "";
	}

	/**
	 * 获取菜单的名称们(多个)
	 */
	@Override
	public String getMenuNames(String menuIds) {
		Long[] ids = Convert.toLongArray(true, menuIds);

		List<String> names = resourceDao.listResourceNameByIds(ids);

		return CollectionKit.join(names, ",");
	}

	/**
	 * 获取菜单名称
	 */
	@Override
	public String getMenuName(Long menuId) {
		if (ObjectKit.isEmpty(menuId)) {
			return "--";
		} else {
			KtfResource menu = resourceDao.selectByKey(menuId);
			if (menu == null) {
				return "--";
			} else {
				return menu.getName();
			}
		}
	}

	/**
	 * 获取菜单名称通过编号
	 */
	@Override
	public String getMenuNameByCode(String code) {
		if (ObjectKit.isEmpty(code)) {
			return "";
		} else {
			KtfResource param = new KtfResource();
			param.setCode(code);
			List<KtfResource> menus = resourceDao.selectByEntity(param);
			if (menus == null || menus.isEmpty()) {
				return "";
			} else {
				return menus.get(0).getName();
			}
		}
	}

	/**
	 * 获取字典名称
	 */
	@Override
	public String getDictName(Long dictId) {
		if (ObjectKit.isEmpty(dictId)) {
			return "";
		} else {
			KtfDict dict = dictMapper.selectByPrimaryKey(dictId);
			if (dict == null) {
				return "";
			} else {
				return dict.getName();
			}
		}
	}

	/**
	 * 获取通知标题
	 */
	@Override
	public String getNoticeTitle(Long id) {
		if (ObjectKit.isEmpty(id)) {
			return "";
		} else {
			KtfNotice notice = noticeMapper.selectByPrimaryKey(id);
			if (notice == null) {
				return "";
			} else {
				return notice.getTitle();
			}
		}
	}

	/**
	 * 根据字典名称和字典中的值获取对应的名称
	 */
	@Override
	public String getDictsByName(String name, Integer val) {
		KtfDict temp = new KtfDict();
		temp.setName(name);
		KtfDict dict = dictMapper.selectOne(temp);
		if (dict == null) {
			return "";
		} else {
			Example example = new Example(KtfDict.class);
			example.createCriteria().andEqualTo("pid", dict.getId());
			List<KtfDict> dicts = dictMapper.selectByExample(example);
			for (KtfDict item : dicts) {
				if (item.getNum() != null && item.getNum().equals(val)) {
					return item.getName();
				}
			}
			return "";
		}
	}

	/**
	 * 获取性别名称
	 */
	@Override
	public String getSexName(Integer sex) {
		return getDictsByName("性别", sex);
	}

	/**
	 * 获取用户登录状态
	 */
	@Override
	public String getStatusName(Integer status) {
		return KtfStatus.valueOf(status);
	}

	/**
	 * 获取菜单状态
	 */
	@Override
	public String getMenuStatusName(Integer status) {
		return KtfStatus.valueOf(status);
	}

	/**
	 * 查询字典
	 */
	@Override
	public List<KtfDict> findInDict(Long id) {
		if (ObjectKit.isEmpty(id)) {
			return null;
		} else {
			Example example = new Example(KtfDict.class);
			example.createCriteria().andEqualTo("pid", id);
			List<KtfDict> dicts = dictMapper.selectByExample(example);
			if (dicts == null || dicts.size() == 0) {
				return null;
			} else {
				return dicts;
			}
		}
	}

	/**
	 * 获取被缓存的对象(用户删除业务)
	 */
	@Override
	public String getCacheObject(String para) {
		return null;
	}

	/**
	 * 获取子部门id
	 */
	@Override
	public List<Long> getSubDeptId(Long deptid) {
		Example example = new Example(KtfDept.class);
		example.createCriteria().andLike("pids", "%[" + deptid + "]%");

		List<KtfDept> depts = this.deptMapper.selectByExample(example);

		ArrayList<Long> deptids = new ArrayList<>();

		if (depts != null && depts.size() > 0) {
			for (KtfDept dept : depts) {
				deptids.add(dept.getId());
			}
		}

		return deptids;
	}

	/**
	 * 获取所有父部门id
	 */
	@Override
	public List<Long> getParentDeptIds(Long deptid) {
		KtfDept dept = deptMapper.selectByPrimaryKey(deptid);
		String pids = dept.getPids();
		String[] split = pids.split(",");
		ArrayList<Long> parentDeptIds = new ArrayList<>();
		for (String s : split) {
			parentDeptIds.add(Long.valueOf(StrKit.removeSuffix(StrKit.removePrefix(s, "["), "]")));
		}
		return parentDeptIds;
	}

	@Override
	public Set<String> getPermissionsByUserId(Long userId) {
		return resourceDao.selectPermissions(userId);
	}

}
