package com.kivi.framework.web.shiro.token.manager.impl;

import java.util.Date;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.util.StringUtils;

import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.web.shiro.token.StatelessToken;
import com.kivi.framework.web.shiro.token.helper.ITokenCache;
import com.kivi.framework.web.shiro.token.manager.AbstractTokenManager;
import com.kivi.framework.web.shiro.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * 
 * 采用时间戳 +共享秘钥+黑名单 （类似Zendesk的做法） 防范Replay Attacks的tokenManager
 * 
 *
 */
public class RaFilterJwtTokenManagerImpl extends AbstractTokenManager {

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
	public boolean check(String authentication) {
		StatelessToken accessToken = super.getToken(authentication);
		if (accessToken == null) {
			return false;
		}
		return this.checkToken(accessToken);
	}

	@Override
	protected boolean checkMemoryToken(StatelessToken accessToken) {
		if (StringUtils.isEmpty(accessToken)) {
			throw new NullPointerException("accessToken can not be null");
		}
		String userCode = accessToken.getUsername();
		// 判断用户是否已经登陆
		String token = tokenCache.getUserToken(userTokenPrefix + userCode);
		if (StringUtils.isEmpty(token)) {
			throw new AuthenticationException("缓存中无该用户:" + userCode + "的token信息，用户未登录");
		}
		tokenCache.updateUserToken(userTokenPrefix + userCode, token, expirateTime);
		return true;
	}

	@Override
	public boolean checkToken(StatelessToken accessToken) {
		checkMemoryToken(accessToken);

		Claims parseJWT = parseClaims(accessToken);

		checkStatelessTokenUserCode(parseJWT, accessToken);

		expireAtTokenKey(parseJWT);

		checkExpirateTime(parseJWT);
		return true;
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

	private void expireAtTokenKey(Claims parseJWT) {
		// 黑名单方式防止Replay Attacks 需要客户端知道User ID 共享秘钥 消息增加jti
		String id = parseJWT.getId();
		if (StringUtils.isEmpty(id)) {
			// jti为空不采用黑名单
			return;
		}
		String tokenKey = parseJWT.getSubject() + "_" + id;
		String andSetLoginId = tokenCache.getAndSetLoginFlag(tokenKey);
		// 该key已经被使用过
		if (!StringUtils.isEmpty(andSetLoginId) && ITokenCache.ONLINE.equals(andSetLoginId)) {
			throw new ExpiredJwtException(null, parseJWT, "该token已经被使用");
		}
		// 未使用进行键值过期检查
		long time = parseJWT.getExpiration().getTime() - System.currentTimeMillis();
		if (time > 0) {
			tokenCache.setLoginFlagExpiration(tokenKey, (int) (time / 1000));
		}

	}

	private void checkExpirateTime(Claims parseJWT) {
		// 检查时间 正常情况下上一步时间校验失败解析就会出错
		Date issuedAt = parseJWT.getIssuedAt();
		Date expiration = parseJWT.getExpiration();
		String sIssuedAt = DateTimeKit.formatDateTime(issuedAt);
		logger.debug("issuedAt:{}", sIssuedAt);
		String sExpiration = DateTimeKit.formatDateTime(expiration);
		logger.debug("expiration:{}", sExpiration);
		if (issuedAt == null || expiration == null) {
			logger.info("签名中不含时间信息");
			return;
		}
		long currentTimeMillis = System.currentTimeMillis();
		String sNow = DateTimeKit.now();
		logger.debug("now:{}", sNow);
		if (currentTimeMillis > expiration.getTime() || currentTimeMillis < issuedAt.getTime()) {
			logger.info("token时间校验失败,当前时间{}，创建时间{}，失效时间" + sExpiration, sNow, sIssuedAt);
			throw new AuthenticationException(
					String.format("token时间校验失败,当前时间%s，创建时间%s，失效时间%s", sNow, sIssuedAt, sExpiration));
		}
	}

}
