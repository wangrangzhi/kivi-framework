package com.kivi.framework.web.shiro.factory;

import org.apache.shiro.authc.SimpleAuthenticationInfo;

import com.kivi.framework.web.shiro.vo.ShiroUserVO;
import com.kivi.framework.web.vo.UserVO;

/**
 * 定义shirorealm所需数据的接口
 *
 */
public interface IShiro {

	/**
	 * 根据账号获取登录用户
	 *
	 * @param account
	 *            账号
	 */
	UserVO user(String account);

	/**
	 * 根据系统用户获取Shiro的用户
	 *
	 * @param user
	 *            系统用户
	 */
	ShiroUserVO shiroUser(UserVO user);

	/**
	 * 获取shiro的认证信息
	 */
	SimpleAuthenticationInfo info(ShiroUserVO shiroUser, UserVO user, String realmName);

	org.apache.shiro.cache.CacheManager getShiroCacheManager();

}
