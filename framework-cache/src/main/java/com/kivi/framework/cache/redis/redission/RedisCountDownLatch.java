/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Redisson distributed CountDownLatch object for Java has structure similar to 
 * java.util.concurrent.CountDownLatch object.
 * 公共计数器建议在：baseUtil的中redis-component.xml声明spring bean
 * 私有计数器建议在：各自应用的spring-redis-redisson.xml中声明spring bean
 * @author Eric
 *
 */
public class RedisCountDownLatch {
	private static final Logger log = LoggerFactory.getLogger(RedisCountDownLatch.class);
	
	private RedissonClient redisson;
	
	private ConcurrentMap<String,RCountDownLatch> rCountDownLatches = new  ConcurrentHashMap<>(); 
	
	public RedisCountDownLatch(){
	}
	
	
	
	public RedissonClient getRedisson() {
		return redisson;
	}
	public void setRedisson(RedissonClient redisson) {
		this.redisson = redisson;
	}
	
	
	private RCountDownLatch getLatch( String name ){
		RCountDownLatch result = null ;
		if(rCountDownLatches.containsKey(name))
			result = rCountDownLatches.get(name);
		else{
			result = this.redisson.getCountDownLatch(name) ;
			rCountDownLatches.put(name, result) ;
		}
		
		return result ;
	}

	/**
	 * Sets new count value only if previous count already has reached zero or is not set at all.
	 * @param count  number of times countDown must be invoked before threads can pass through await
	 * @return
	 */
	public boolean trySetCount(String name, long count){
		return getLatch(name).trySetCount(count) ;
	}
	
	/**
	 * Decrements the count of the latch, releasing all waiting threads if the count reaches zero. 
	 * 
	 * If the current count is greater than zero then it is decremented. If the new count is zero 
	 * then all waiting threads are re-enabled for thread scheduling purposes. 
	 * 
	 * If the current count equals zero then nothing happens.
	 */
	public void countDown(String name){
		getLatch(name).countDown(); 
	}
	
	/**
	 * Causes the current thread to wait until the latch has counted down to zero, unless the thread is interrupted. 
	 * If the current count is zero then this method returns immediately. 
	 * 
	 * If the current count is greater than zero then the current thread becomes disabled for thread scheduling 
	 * purposes and lies dormant until one of two things happen: 
	 * 
	 * The count reaches zero due to invocations of the countDown method; or 
	 * Some other thread interrupts the current thread. 
	 * 
	 * If the current thread: 
	 * has its interrupted status set on entry to this method; or 
	 * is interrupted while waiting, 

	 * @throws InterruptedException
	 */
	public void await(String name) throws InterruptedException{
		getLatch(name).await();
	}
	
	/**
	 * Causes the current thread to wait until the latch has counted down to zero, 
	 * unless the thread is interrupted, or the specified waiting time elapses. 
	 * 
	 * If the current count is zero then this method returns immediately with the value true. 
	 * If the current count is greater than zero then the current thread becomes disabled for thread scheduling 
	 * purposes and lies dormant until one of three things happen: 
	 * 
	 * The count reaches zero due to invocations of the countDown method; or 
	 * Some other thread interrupts the current thread; or 
	 * The specified waiting time elapses. 
	 * 
	 * If the count reaches zero then the method returns with the value true. 
	 * 
	 * If the current thread: 
	 * has its interrupted status set on entry to this method; or 
	 * is interrupted while waiting, 
	 * then InterruptedException is thrown and the current thread's interrupted status is cleared. 
	 * If the specified waiting time elapses then the value false is returned. 
	 * If the time is less than or equal to zero, the method will not wait at all.

	 * @param timeout		the maximum time to wait
	 * @param unit			the time unit of the timeout argument
	 * @return		true if the count reached zero and false if the waiting time elapsed before the count reached zero
	 * @throws InterruptedException
	 */
	public boolean await(String name, long timeout, TimeUnit unit) throws InterruptedException{
		return getLatch(name).await(timeout, unit) ;
	}
}
