package com.kivi.framework.cache.factory;

import java.util.Set;

/**
 * 通用缓存接口
 */
public interface ICache {

	void put(String cacheName, Object key, Object value);

	<T> T get(String cacheName, Object key);

	Set<String> getKeys(String cacheName);

	void remove(String cacheName, Object key);

	void removeAll(String cacheName);

	<T> T get(String cacheName, Object key, ILoader iLoader);

	<T> T get(String cacheName, Object key, Class<? extends ILoader> iLoaderClass);

}
