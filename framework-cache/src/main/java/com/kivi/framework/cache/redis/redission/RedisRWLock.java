/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Redisson distributed reentrant ReadWriteLock object for Java implements 
 * java.util.concurrent.locks.ReadWriteLock interface and supports TTL. 
 * Many ReadLock owners and only one WriteLock owner are allowed.
 * 公共锁建议在：baseUtil的中redis-component.xml声明spring bean
 * 私有锁建议在：各自应用的spring-redis-redisson.xml中声明spring bean
 * @author Eric
 *
 */
public class RedisRWLock {
	private static final Logger log = LoggerFactory.getLogger(RedisRWLock.class);
	private RedissonClient redisson;
	
	private ConcurrentMap<String,RReadWriteLock> rwlocks = new  ConcurrentHashMap<>(); 
	
	
	public RedisRWLock(){
	}
	
	
	private RReadWriteLock getRWLock( String name)  {
		
		RReadWriteLock rwlock = null ;
		if( rwlocks.containsKey(name)){
			rwlock = rwlocks.get(name) ;
		}
		else{
			rwlock = redisson.getReadWriteLock(name);
			rwlocks.put(name, rwlock) ;
		}
		
		return rwlock;
	}
	public RedissonClient getRedisson() {
		return redisson;
	}
	public void setRedisson(RedissonClient redisson) {
		this.redisson = redisson;
	}
	
	
	/**
	 * Acquires the read lock. 
	 * If the lock is not available then the current thread becomes disabled for thread scheduling 
	 * purposes and lies dormant until the lock has been acquired. 
	 */
	public void readLock(String name ){
		getRWLock(name).readLock().lock();
	}
	
	/**
	 * Acquires the read lock. 
	 * If the lock is not available then the current thread becomes disabled for thread scheduling 
	 * purposes and lies dormant until the lock has been acquired. If the lock is acquired, 
	 * it is held until unlock is invoked, or until leaseTime milliseconds have passed 
	 * since the lock was granted - whichever comes first.
	 * 
	 * @param leaseTime   the maximum time to hold the lock after granting it, 
	 *                    before automatically releasing it if it hasn't already been released by invoking unlock. 
	 *                    If leaseTime is -1, hold the lock until explicitly unlocked.
	 * @param unit        the time unit of the leaseTime argument
	 */
	public void readLock(String name , long leaseTime, TimeUnit unit){
		getRWLock(name).readLock().lock(leaseTime, unit);
		
	}
	
	/**
	 * Acquires the lock only if it is free at the time of invocation. 
	 * 
	 * Acquires the lock if it is available and returns immediately with the value true. 
	 * If the lock is not available then this method will return immediately with the value false. 
	 * A typical usage idiom for this method would be: 

     * RedisRWLock lock = ...;
     * if (lock.tryReadLock()) {
     *     try {
     *         // manipulate protected state
     *     } finally {
     *         lock.readUnlock();
     *     }
     * } else {
     *     // perform alternative actions
     * }
     * 
	 * This usage ensures that the lock is unlocked if it was acquired, 
	 * and doesn't try to unlock if the lock was not acquired.
	 * @return true if the lock was acquired and false otherwise
	 */
	public boolean tryReadLock(String name ){
		return getRWLock(name).readLock().tryLock();
	}
	
	/**
	 * 
	 * @param time			the maximum time to wait for the lock
	 * @param unit			the time unit of the time argument
	 * @return	true if the lock was acquired and false if the waiting time elapsed before the lock was acquired
	 * @throws InterruptedException
	 */
	public boolean tryReadLock(String name ,long time, TimeUnit unit) throws InterruptedException{
		return getRWLock(name).readLock().tryLock(time, unit) ;
	}
	
	/**
	 * 
	 * @param waitTime		 the maximum time to aquire the lock
	 * @param leaseTime		 lease time
	 * @param unit			 time unit
	 * @return
	 * @throws InterruptedException
	 */
	public boolean tryReadLock( String name ,long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException{
		return getRWLock(name).readLock().tryLock(waitTime, leaseTime, unit) ;
	}
	
	/**
	 * Release the read lock
	 */
	public void readUnlock(String name ){
		getRWLock(name).readLock().unlock();
	}
	
	/**
	 * Acquires the write lock. 
	 * If the lock is not available then the current thread becomes disabled for thread scheduling 
	 * purposes and lies dormant until the lock has been acquired. 
	 */
	public void writeLock(String name ){
		getRWLock(name).writeLock().lock();
	}
	
	/**
	 * Acquires the write lock. 
	 * If the lock is not available then the current thread becomes disabled for thread scheduling 
	 * purposes and lies dormant until the lock has been acquired. If the lock is acquired, 
	 * it is held until unlock is invoked, or until leaseTime milliseconds have passed 
	 * since the lock was granted - whichever comes first.
	 * 
	 * @param leaseTime   the maximum time to hold the lock after granting it, 
	 *                    before automatically releasing it if it hasn't already been released by invoking unlock. 
	 *                    If leaseTime is -1, hold the lock until explicitly unlocked.
	 * @param unit        the time unit of the leaseTime argument
	 */
	public void writeLock(String name ,long leaseTime, TimeUnit unit){
		getRWLock(name).writeLock().lock(leaseTime,unit);
	}
	
	/**
	 * Acquires the lock only if it is free at the time of invocation. 
	 * 
	 * Acquires the lock if it is available and returns immediately with the value true. 
	 * If the lock is not available then this method will return immediately with the value false. 
	 * A typical usage idiom for this method would be: 

     * RedisRWLock lock = ...;
     * if (lock.tryWriteLock()) {
     *     try {
     *         // manipulate protected state
     *     } finally {
     *         lock.writeUnlock();
     *     }
     * } else {
     *     // perform alternative actions
     * }
     * 
	 * This usage ensures that the lock is unlocked if it was acquired, 
	 * and doesn't try to unlock if the lock was not acquired.
	 * @return true if the lock was acquired and false otherwise
	 */
	public boolean tryWriteLock(String name ){
		return getRWLock(name).writeLock().tryLock();
	}
	
	/**
	 * 
	 * @param time			the maximum time to wait for the lock
	 * @param unit			the time unit of the time argument
	 * @return	true if the lock was acquired and false if the waiting time elapsed before the lock was acquired
	 * @throws InterruptedException
	 */
	public boolean tryWriteLock(String name ,long time, TimeUnit unit) throws InterruptedException{
		return getRWLock(name).writeLock().tryLock(time, unit) ;
	}
	
	/**
	 * 
	 * @param waitTime		 the maximum time to aquire the lock
	 * @param leaseTime		 lease time
	 * @param unit			 time unit
	 * @return
	 * @throws InterruptedException
	 */
	public boolean tryWriteLock(String name , long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException{
		return getRWLock(name).writeLock().tryLock(waitTime, leaseTime, unit) ;
	}
	
	/**
	 * Release the write lock
	 */
	public void writeUnlock( String name ){
		getRWLock(name).writeLock().unlock();
	}
}
