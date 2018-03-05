package com.kivi.framework.web.shiro.token.helper;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;

import com.kivi.framework.cache.constant.KtfCache;
import com.kivi.framework.util.kit.StrKit;

public class TokenRedis implements ITokenCache {

	private final RedisOperations<String, String> redisOperations;

	private final ValueOperations<String, String> opsForValue;

	public TokenRedis(RedisOperations<String, String> redisOperations) {
		this.redisOperations = redisOperations;
		this.opsForValue = redisOperations.opsForValue();
	}

	@Override
	public String getAndSetLoginFlag(String key) {

		String redisKey = StrKit.join("-", KtfCache.SHIRO_LOGIN_USER_CACHE, key);

		return opsForValue.getAndSet(redisKey, ONLINE);
	}

	@Override
	public void setLoginFlagExpiration(String key, int seconds) {
		String redisKey = StrKit.join("-", KtfCache.SHIRO_LOGIN_USER_CACHE, key);
		redisOperations.expire(redisKey, seconds, TimeUnit.SECONDS);
	}

	@Override
	public int getAndIncrementLoginCount(String key) {
		String redisKey = StrKit.join("-", KtfCache.SHIRO_LOGIN_COUNT_CACHE, key);

		Long val = opsForValue.increment(redisKey, 1);
		return val == null ? 0 : (int) val.longValue();
	}

	@Override
	public void setLoginLockFlag(String key, int seconds) {
		String redisKey = StrKit.join("-", KtfCache.SHIRO_IS_LOCK_CACHE, key);
		opsForValue.set(redisKey, LOCKED, seconds, TimeUnit.SECONDS);
	}

	@Override
	public boolean getLoginLockFlag(String key) {
		String redisKey = StrKit.join("-", KtfCache.SHIRO_IS_LOCK_CACHE, key);
		String val = opsForValue.get(redisKey);
		if (val == null)
			return false;
		return LOCKED.equals(val);
	}

	@Override
	public String getUserToken(String userCode) {
		String redisKey = StrKit.join("-", KtfCache.SHIRO_LOGIN_TOKEN_CACHE, userCode);
		return opsForValue.get(redisKey);
	}

	@Override
	public void updateUserToken(String userCode, String token, long seconds) {
		String redisKey = StrKit.join("-", KtfCache.SHIRO_LOGIN_TOKEN_CACHE, userCode);

		opsForValue.set(redisKey, token, seconds, TimeUnit.SECONDS);
	}

	@Override
	public void deleteUserToken(String userCode) {
		String redisKey = StrKit.join("-", KtfCache.SHIRO_LOGIN_TOKEN_CACHE, userCode);
		redisOperations.delete(redisKey);
	}

}
