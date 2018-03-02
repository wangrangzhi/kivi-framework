/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Redisson distributed Semaphore object for Java similar to
 * java.util.concurrent.Semaphore object.
 * 公共信号量建议在：baseUtil的中redis-component.xml声明spring bean
 * 私有信号量建议在：各自应用的spring-redis-redisson.xml中声明spring bean
 * 
 * @author Eric
 *
 */
public class RedisSemaphore {
	private static final Logger log = LoggerFactory.getLogger(RedisRWLock.class);
	private RedissonClient redisson;

	private static final String PREFIX = "RSEM_";

	public RedisSemaphore() {
	}

	private RSemaphore getRSemaphore(String name) {

		RSemaphore rSemaphore = redisson.getSemaphore(PREFIX + name);
		return rSemaphore;
	}

	public RedissonClient getRedisson() {
		return redisson;
	}

	public void setRedisson(RedissonClient redisson) {
		this.redisson = redisson;
	}

	/**
	 * Sets number of permits.
	 * 
	 * @param permits
	 * @return true if permits has been set successfully, otherwise false.
	 */
	public boolean trySetPermits(String name, int permits) {
		return getRSemaphore(name).trySetPermits(permits);
	}

	/**
	 * Acquires a permit from this semaphore, blocking until one is available,
	 * or the thread is interrupted.
	 * 
	 * Acquires a permit, if one is available and returns immediately, reducing
	 * the number of available permits by one. If no permit is available then
	 * the current thread becomes disabled for thread scheduling purposes and
	 * lies dormant until one of two things happens:
	 * 
	 * Some other thread invokes the release method for this semaphore and the
	 * current thread is next to be assigned a permit; or Some other thread
	 * interrupts the current thread.
	 * 
	 * @throws InterruptedException
	 */
	public void acquire(String name) throws InterruptedException {
		getRSemaphore(name).acquire();

	}

	/**
	 * Acquires the given number of permits from this semaphore, blocking until
	 * all are available, or the thread is {@linkplain Thread#interrupt
	 * interrupted}.
	 *
	 * <p>
	 * Acquires the given number of permits, if they are available, and returns
	 * immediately, reducing the number of available permits by the given
	 * amount.
	 *
	 * <p>
	 * If insufficient permits are available then the current thread becomes
	 * disabled for thread scheduling purposes and lies dormant until one of two
	 * things happens:
	 * <ul>
	 * <li>Some other thread invokes one of the {@link #release() release}
	 * methods for this semaphore, the current thread is next to be assigned
	 * permits and the number of available permits satisfies this request; or
	 * <li>Some other thread {@linkplain Thread#interrupt interrupts} the
	 * current thread.
	 * </ul>
	 * 
	 * @throws InterruptedException
	 */
	public void acquire(String name, int permits) throws InterruptedException {
		getRSemaphore(name).acquire(permits);
	}

	/**
	 * Acquires a permit only if one is available at the time of invocation.
	 * Acquires a permit, if one is available and returns immediately, with the
	 * value true, reducing the number of available permits by one. If no permit
	 * is available then this method will return immediately with the value
	 * false.
	 * 
	 * @return true if a permit was acquired and false otherwise
	 */
	public boolean tryAcquire(String name) {
		return getRSemaphore(name).tryAcquire();
	}

	public boolean tryAcquire(String name, int permits) {
		return getRSemaphore(name).tryAcquire(permits);
	}

	/**
	 * Acquires a permit from this semaphore, if one becomes available within
	 * the given waiting time and the current thread has not been interrupted.
	 * Acquires a permit, if one is available and returns immediately, with the
	 * value true, reducing the number of available permits by one. If no permit
	 * is available then the current thread becomes disabled for thread
	 * scheduling purposes and lies dormant until one of three things happens:
	 * 
	 * Some other thread invokes the release method for this semaphore and the
	 * current thread is next to be assigned a permit; or Some other thread
	 * interrupts the current thread; or The specified waiting time elapses.
	 * 
	 * If a permit is acquired then the value true is returned. If the specified
	 * waiting time elapses then the value false is returned. If the time is
	 * less than or equal to zero, the method will not wait at all.
	 * 
	 * @param waitTime
	 *            the maximum time to wait for a permit
	 * @param unit
	 *            the time unit of the timeout argument
	 * @return
	 * @throws InterruptedException
	 */
	public boolean tryAcquire(String name, long waitTime, TimeUnit unit) throws InterruptedException {
		return getRSemaphore(name).tryAcquire(waitTime, unit);
	}

	/**
	 * Releases a permit, returning it to the semaphore. Releases a permit,
	 * increasing the number of available permits by one. If any threads of
	 * Redisson client are trying to acquire a permit, then one is selected and
	 * given the permit that was just released.
	 * 
	 * There is no requirement that a thread that releases a permit must have
	 * acquired that permit by calling acquire. Correct usage of a semaphore is
	 * established by programming convention in the application.
	 */
	public void release(String name) {
		getRSemaphore(name).release();
	}

	public void release(String name, int permits) {
		getRSemaphore(name).release(permits);
	}
}
