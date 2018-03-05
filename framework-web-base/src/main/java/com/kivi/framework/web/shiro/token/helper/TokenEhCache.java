package com.kivi.framework.web.shiro.token.helper;

import java.util.concurrent.atomic.AtomicInteger;

import com.kivi.framework.cache.constant.KtfCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class TokenEhCache implements ITokenCache {

	private final CacheManager cacheManager;

	private String userTokenCacheName = KtfCache.SHIRO_LOGIN_TOKEN_CACHE;

	public TokenEhCache(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public String getAndSetLoginFlag(String tokenKey) {
		Cache cache = cacheManager.getCache(KtfCache.SHIRO_LOGIN_USER_CACHE);
		Element element = cache.get(tokenKey);
		if (element == null) {
			Element e = new Element(tokenKey, ONLINE);
			cache.put(e);
			return null;
		} else {
			Object objectValue = element.getObjectValue();
			if (objectValue == null) {
				return null;
			} else {
				return (String) objectValue;
			}
		}
	}

	@Override
	public void setLoginFlagExpiration(String tokenKey, int seconds) {
		Cache cache = cacheManager.getCache(KtfCache.SHIRO_LOGIN_USER_CACHE);
		Element element = cache.get(tokenKey);
		if (element != null) {
			element.setTimeToLive(seconds);
		}

	}

	@Override
	public int getAndIncrementLoginCount(String key) {
		Cache cache = cacheManager.getCache(KtfCache.SHIRO_LOGIN_COUNT_CACHE);
		Element element = cache.get(key);
		if (element == null) {
			AtomicInteger count = new AtomicInteger();
			Element e = new Element(key, count);
			cache.put(e);
			return count.incrementAndGet();
		} else {
			AtomicInteger count = (AtomicInteger) element.getObjectValue();
			if (count == null) {
				return 0;
			} else {
				return count.incrementAndGet();
			}

		}
	}

	@Override
	public void setLoginLockFlag(String key, int seconds) {
		Cache cache = cacheManager.getCache(KtfCache.SHIRO_IS_LOCK_CACHE);
		Element e = new Element(key, LOCKED);
		e.setTimeToLive(seconds);

		cache.put(e);
	}

	@Override
	public boolean getLoginLockFlag(String key) {
		Cache cache = cacheManager.getCache(KtfCache.SHIRO_IS_LOCK_CACHE);
		Element element = cache.get(key);
		if (element == null) {
			return false;
		} else {
			Object objectValue = element.getObjectValue();
			if (objectValue == null) {
				return false;
			} else {
				return LOCKED.equals(objectValue);
			}
		}
	}

	@Override
	public String getUserToken(String userCode) {
		Cache cache = getUserTokenCache();
		if (cache == null) {
			return null;
		} else {
			Element element = cache.get(userCode);
			if (element == null) {
				return null;
			} else {
				Object objectValue = element.getObjectValue();
				if (objectValue == null) {
					return null;
				} else {
					return (String) objectValue;
				}
			}
		}
	}

	public Cache getUserTokenCache() {
		Cache cache = cacheManager.getCache(userTokenCacheName);
		return cache;
	}

	@Override
	public void updateUserToken(String userCode, String token, long seconds) {
		Cache cache = getUserTokenCache();
		Element e = new Element(userCode, token);
		e.setTimeToLive(new Long(seconds).intValue());
		cache.put(e);

	}

	@Override
	public void deleteUserToken(String userCode) {
		Cache cache = getUserTokenCache();
		cache.remove(userCode);
	}

	public String getUserTokenCacheName() {
		return userTokenCacheName;
	}

	public void setUserTokenCacheName(String userTokenCacheName) {
		this.userTokenCacheName = userTokenCacheName;
	}

}
