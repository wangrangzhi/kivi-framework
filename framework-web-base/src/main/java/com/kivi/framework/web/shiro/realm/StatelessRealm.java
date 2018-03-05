package com.kivi.framework.web.shiro.realm;

import java.util.Arrays;

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

import com.kivi.framework.util.kit.ObjectKit;
import com.kivi.framework.web.shiro.factory.IShiro;
import com.kivi.framework.web.shiro.factory.ShiroFactroy;
import com.kivi.framework.web.shiro.token.StatelessToken;
import com.kivi.framework.web.shiro.token.manager.TokenManager;
import com.kivi.framework.web.shiro.util.ShiroKit;
import com.kivi.framework.web.shiro.vo.ShiroUserVO;
import com.kivi.framework.web.vo.UserVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatelessRealm extends AuthorizingRealm {

	private TokenManager tokenManager;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof StatelessToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 根据用户名查权限，请根据需求实现
		ShiroUserVO shiroUser = (ShiroUserVO) principals.getPrimaryPrincipal();
		if (!shiroUser.getObjectName().equals(this.getName())) {
			return null;
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(Arrays.asList(shiroUser.getPermissions()));
		info.addRoles(Arrays.asList(shiroUser.getRoles()));

		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		StatelessToken statelessToken = (StatelessToken) token;

		String userCode = (String) statelessToken.getPrincipal();

		IShiro shiroFactory = ShiroFactroy.me();
		UserVO user = shiroFactory.user(userCode);
		ShiroUserVO shiroUser = shiroFactory.shiroUser(user);
		shiroUser.setObjectName(this.getName());
		if (!ObjectKit.equals(statelessToken.getPassword(), statelessToken.getToken())) {
			// 首次登录
			SimpleAuthenticationInfo info = shiroFactory.info(shiroUser, user, super.getName());
			return info;
		} else {
			// 非首次登录
			boolean checkToken = tokenManager.checkToken(statelessToken);
			if (checkToken) {
				user.setPassword(ShiroKit.md5(statelessToken.getToken(), user.getSalt()));
				SimpleAuthenticationInfo info = shiroFactory.info(shiroUser, user, super.getName());
				return info;
			} else {
				throw new AuthenticationException("token认证失败");
			}

		}
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
