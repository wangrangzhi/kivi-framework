package com.kivi.framework.web.shiro.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCache;

import com.kivi.framework.cache.redis.factory.KtfRedisCacheManager;

public class ShiroRedisCacheManager implements CacheManager {

	private static final Logger logger = LoggerFactory.getLogger(ShiroRedisCacheManager.class);

	// fast lookup by name map
	private final ConcurrentMap<String, Cache<?, ?>> caches = new ConcurrentHashMap<String, Cache<?, ?>>();

	private final KtfRedisCacheManager redisCacheManager;

	/**
	 * The Redis key prefix for caches
	 */
	private String keyPrefix = "shiro_redis_cache:";

	public ShiroRedisCacheManager(KtfRedisCacheManager redisCacheManager) {
		this.redisCacheManager = redisCacheManager;
	}

	/**
	 * Returns the Redis session keys prefix.
	 * 
	 * @return The prefix
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * Sets the Redis sessions key prefix.
	 * 
	 * @param keyPrefix
	 *            The prefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("获取名称为: " + name + " 的RedisCache实例");

		Cache<K, V> c = (Cache<K, V>) caches.get(name);

		if (c == null) {

			RedisCache cache = redisCacheManager.createCache(name);
			// create a new cache instance
			c = new ShiroRedisCache<K, V>(cache, keyPrefix);

			// add it to the cache collection
			caches.put(name, c);
		}
		return c;
	}

}
