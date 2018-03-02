package com.kivi.framework.cache.configuration;

import java.io.IOException;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.kivi.framework.cache.redis.redission.RSLock;
import com.kivi.framework.cache.redis.redission.RedisBlockingQueue;
import com.kivi.framework.cache.redis.redission.RedisCountDownLatch;
import com.kivi.framework.cache.redis.redission.RedisFairLock;
import com.kivi.framework.cache.redis.redission.RedisList;
import com.kivi.framework.cache.redis.redission.RedisLock;
import com.kivi.framework.cache.redis.redission.RedisMap;
import com.kivi.framework.cache.redis.redission.RedisRWLock;
import com.kivi.framework.cache.redis.redission.RedisSemaphore;
import com.kivi.framework.cache.redis.redission.RedisSet;
import com.kivi.framework.constant.KiviFramework;

@Configuration
@ConditionalOnProperty(prefix = "framework.redisson", name = {
		"enabled" }, havingValue = "true", matchIfMissing = false)
public class RedissonConfiguration {

	@Bean(destroyMethod = "shutdown")
	RedissonClient redisson(@Value(KiviFramework.REDISSON_CONFIG) Resource configFile) throws IOException {
		Config config = Config.fromJSON(configFile.getInputStream());
		return Redisson.create(config);
	}

	@Bean
	RedisBlockingQueue getRedisBlockingQueue(RedissonClient redissonClient) {
		RedisBlockingQueue redisBlockingQueue = new RedisBlockingQueue();
		redisBlockingQueue.setRedisson(redissonClient);
		return redisBlockingQueue;
	}

	@Bean
	RedisMap getRedisMap(RedissonClient redissonClient) {
		RedisMap redisMap = new RedisMap();
		redisMap.setRedisson(redissonClient);
		return redisMap;
	}

	@Bean
	RedisList getRedisList(RedissonClient redissonClient) {
		RedisList redisList = new RedisList();
		redisList.setRedisson(redissonClient);
		return redisList;
	}

	@Bean
	RedisLock getRedisLock(RedissonClient redissonClient) {
		RedisLock redisLock = new RedisLock();
		redisLock.setRedisson(redissonClient);
		return redisLock;
	}

	@Bean
	RedisFairLock getRedisFairLock(RedissonClient redissonClient) {
		RedisFairLock redisLock = new RedisFairLock();
		redisLock.setRedisson(redissonClient);
		return redisLock;
	}

	@Bean
	RedisRWLock getRedisRWLock(RedissonClient redissonClient) {
		RedisRWLock redisObj = new RedisRWLock();
		redisObj.setRedisson(redissonClient);
		return redisObj;
	}

	@Bean
	RedisCountDownLatch getRedisCountDownLatch(RedissonClient redissonClient) {
		RedisCountDownLatch redisObj = new RedisCountDownLatch();
		redisObj.setRedisson(redissonClient);
		return redisObj;
	}

	@Bean
	RedisSemaphore getRedisSemaphore(RedissonClient redissonClient) {
		RedisSemaphore redisSemaphore = new RedisSemaphore();
		redisSemaphore.setRedisson(redissonClient);
		return redisSemaphore;
	}

	@Bean
	RedisSet getRedisSet(RedissonClient redissonClient) {
		RedisSet redisObj = new RedisSet();
		redisObj.setRedisson(redissonClient);
		return redisObj;
	}

	@Bean
	RSLock getRSLock(RedisSemaphore redisSemaphore, RedisList redisList) {
		RSLock redisObj = new RSLock();
		redisObj.setRedisSemaphore(redisSemaphore);
		redisObj.setRedisList(redisList);
		return redisObj;
	}

}
