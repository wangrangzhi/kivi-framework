package com.kivi.framework.cache.redis.factory;

import java.util.Collection;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

public class KtfRedisCacheManager extends RedisCacheManager {

	@SuppressWarnings("rawtypes")
	public KtfRedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames,
			boolean cacheNullValues) {
		super(redisOperations, cacheNames, cacheNullValues);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("rawtypes")
	public KtfRedisCacheManager(RedisOperations redisOperations, Collection<String> cacheNames) {
		super(redisOperations, cacheNames);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("rawtypes")
	public KtfRedisCacheManager(RedisOperations redisOperations) {
		super(redisOperations);
		// TODO Auto-generated constructor stub

	}

	@Override
	public RedisCache createCache(String cacheName) {
		// TODO Auto-generated method stub
		return super.createCache(cacheName);

	}

	@SuppressWarnings("rawtypes")
	public final RedisOperations getRedisOperations() {
		return super.getRedisOperations();
	}
}
