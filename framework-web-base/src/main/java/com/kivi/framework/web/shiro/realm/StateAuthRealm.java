package com.kivi.framework.web.shiro.realm;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.kivi.framework.vo.web.UserVO;
import com.kivi.framework.web.shiro.factory.IShiro;
import com.kivi.framework.web.shiro.factory.ShiroFactroy;
import com.kivi.framework.web.shiro.token.StateAuthToken;
import com.kivi.framework.web.shiro.util.ShiroKit;
import com.kivi.framework.web.shiro.vo.ShiroUserVO;

import lombok.Getter;
import lombok.Setter;

/**
 * 有状态Realm
 */
@Setter
@Getter
public class StateAuthRealm extends AuthorizingRealm {

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof StateAuthToken;
	}

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		// 获取用户的输入的账号.
		StateAuthToken token = (StateAuthToken) authcToken;
		String name = token.getUsername();

		IShiro shiroFactory = ShiroFactroy.me();
		UserVO user = shiroFactory.user(name);
		ShiroUserVO shiroUser = shiroFactory.shiroUser(user);
		shiroUser.setObjectName(this.getName());

		SimpleAuthenticationInfo info = shiroFactory.info(shiroUser, user, super.getName());

		Logger.getLogger(getClass()).info("身份认证成功，登录用户：" + name);
		return info;
	}

	/**
	 * 此方法调用 hasRole,hasPermission的时候才会进行回调.
	 *
	 * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException {

		/*
		 * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行， 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
		 * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了， 缓存过期之后会再次执行。
		 */
		ShiroUserVO shiroUser = (ShiroUserVO) principals.getPrimaryPrincipal();
		if (!shiroUser.getObjectName().equals(this.getName())) {
			return null;
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(Arrays.asList(shiroUser.getPermissions()));
		info.addRoles(Arrays.asList(shiroUser.getRoles()));
		return info;
	}

	/**
	 * 设置认证加密方式
	 */
	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
		md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
		md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
		super.setCredentialsMatcher(md5CredentialsMatcher);
	}

}
