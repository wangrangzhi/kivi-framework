package com.kivi.framework.web.shiro.token.manager.impl;

import org.apache.shiro.authc.AuthenticationException;

import com.kivi.framework.web.shiro.token.StatelessToken;
import com.kivi.framework.web.shiro.token.manager.AbstractTokenManager;
import com.kivi.framework.web.shiro.util.JwtUtil;

import io.jsonwebtoken.Claims;

/**
 * 简单的JwtTokenManager 服务端生成token发送给客户端 ，客户端自己保存token 无法防范Replay attack
 * 但是需要客户端知道共享密钥,
 * 
 */
public class SimpleJwtTokenManagerImpl extends AbstractTokenManager {

	private JwtUtil jwtUtil;

	public JwtUtil getJwtUtil() {
		return jwtUtil;
	}

	public void setJwtUtil(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public String createStringToken(String userCode) {
		try {
			return jwtUtil.createJWT(null, userCode);
		} catch (Exception e) {
			logger.error("token创建失败", e);
		}
		return null;
	}

	@Override
	public StatelessToken getToken(String authentication) {
		Claims parseJWT = null;
		try {
			parseJWT = jwtUtil.parseJWT(authentication);
		} catch (Exception e) {
			throw new AuthenticationException("token:解析失败:" + authentication, e);
		}

		StatelessToken accessToken = super.getToken(parseJWT.getSubject() + "_" + authentication);

		return accessToken;
	}

	@Override
	public boolean check(String authentication) {
		StatelessToken accessToken = super.getToken(authentication);
		if (accessToken == null) {
			return false;
		}
		return this.checkToken(accessToken);
	}

	@Override
	public boolean checkToken(StatelessToken accessToken) {
		Claims parseJWT = parseClaims(accessToken);
		checkStatelessTokenUserCode(parseJWT, accessToken);
		return super.checkMemoryToken(accessToken);
	}

	private void checkStatelessTokenUserCode(Claims parseJWT, StatelessToken accessToken) {
		String subject = parseJWT.getSubject();
		if (!accessToken.getUsername().equals(subject)) {
			throw new AuthenticationException("token:所属用户和签名不一致" + accessToken.getToken());
		}
	}

	private Claims parseClaims(StatelessToken accessToken) {
		Claims parseJWT = null;
		try {
			parseJWT = jwtUtil.parseJWT(accessToken.getToken());
		} catch (Exception e) {
			throw new AuthenticationException("token:解析失败:" + accessToken.getToken(), e);
		}
		return parseJWT;
	}

}
