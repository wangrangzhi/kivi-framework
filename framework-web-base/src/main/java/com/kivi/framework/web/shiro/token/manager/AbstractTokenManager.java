package com.kivi.framework.web.shiro.token.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.kivi.framework.web.shiro.token.StatelessToken;
import com.kivi.framework.web.shiro.token.helper.ITokenCache;

public abstract class AbstractTokenManager implements TokenManager {

	/**
	 * 失效时间 单位秒
	 */
	protected long expirateTime;

	protected final Logger logger = LoggerFactory.getLogger(AbstractTokenManager.class);

	protected String userTokenPrefix = "login_token_";

	protected ITokenCache tokenCache;

	@Override
	public StatelessToken createToken(String userCode, String password) {
		StatelessToken tokenModel = null;
		String token = tokenCache.getUserToken(userTokenPrefix + userCode);
		if (StringUtils.isEmpty(token)) {
			token = createStringToken(userCode);
		}
		tokenCache.updateUserToken(userTokenPrefix + userCode, token, expirateTime);
		tokenModel = new StatelessToken(userCode, password, token);
		return tokenModel;
	}

	public abstract String createStringToken(String userCode);

	protected boolean checkMemoryToken(StatelessToken model) {
		if (model == null) {
			return false;
		}

		String userCode = (String) model.getPrincipal();
		String credentials = model.getToken();
		String token = tokenCache.getUserToken(userTokenPrefix + userCode);
		if (token == null || !credentials.equals(token)) {
			return false;
		}
		return true;
	}

	@Override
	public StatelessToken getToken(String authentication) {
		if (StringUtils.isEmpty(authentication)) {
			return null;
		}
		String[] au = authentication.split("_");
		if (au.length <= 1) {
			return null;
		}
		String userCode = au[0];
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < au.length; i++) {
			sb.append(au[i]);
			if (i < au.length - 1) {
				sb.append("_");
			}
		}
		return new StatelessToken(userCode, sb.toString());
	}

	@Override
	public boolean check(String authentication) {
		StatelessToken token = getToken(authentication);
		if (token == null) {
			return false;
		}
		return checkMemoryToken(token);
	}

	@Override
	public void deleteToken(String userCode) {
		tokenCache.deleteUserToken(userTokenPrefix + userCode);
	}

	public long getExpirateTime() {
		return expirateTime;
	}

	public void setExpirateTime(long expirateTime) {
		this.expirateTime = expirateTime;
	}

	public ITokenCache getTokenCache() {
		return tokenCache;
	}

	public void setTokenCache(ITokenCache tokenCache) {
		this.tokenCache = tokenCache;
	}

}
