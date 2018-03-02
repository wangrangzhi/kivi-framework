package com.kivi.framework.cache.redis.redission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class RSLock implements InitializingBean{
	private static final Logger log = LoggerFactory.getLogger(RSLock.class);
	private static final String RSL="RSLock_RedisSemaphore" ;
	
	private RedisSemaphore redisSemaphore;
	private RedisList redisList;
	
	
	private void setPermits( String name){
		if(!redisList.contains(RSL, name)){
			redisSemaphore.trySetPermits(name, 1) ;
			redisList.add(RSL, name);
		}
	}
	
	public void setRedisList(RedisList redisList) {
		this.redisList = redisList;
	}
	
	public void setRedisSemaphore(RedisSemaphore redisSemaphore) {
		this.redisSemaphore = redisSemaphore;
	}
	
	public void lock( String name){
		try {
			setPermits(name) ;
			redisSemaphore.acquire(name);
		} catch (InterruptedException e) {
			log.error("中断异常", e);
		}
	}

	public void unlock( String name){
		redisSemaphore.release(name);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		redisList.size(RSL);
	}
}
