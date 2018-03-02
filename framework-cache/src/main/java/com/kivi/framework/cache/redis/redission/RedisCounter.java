/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Eric
 *
 */
public class RedisCounter {
	private static final Logger log = LoggerFactory.getLogger(RedisCounter.class);
	private RedissonClient redisson;
	
	public RedisCounter(){
		
	}
	
	public long getValue( String key ) {
		RAtomicLong raLong = redisson.getAtomicLong(key) ;
		
		long result =raLong.get();
		//log.debug("RedisCounter：key="+key+"，value="+ result);
		
		
		return result;
	}
	
	public long getSetValue( String key,long val ) {
		RAtomicLong raLong = redisson.getAtomicLong(key) ;
		
		long result =raLong.getAndSet(val) ;
		//log.debug("RedisCounter：key="+key+"，original value="+result+"，new value="+val );
		return result;
	}
	
	public long setValue( String key,long val ) {
		RAtomicLong raLong = redisson.getAtomicLong(key) ;
		raLong.set(val);
		//log.debug("RedisCounter：key="+key+"，new value="+val );
		return val;
	}
	
	public long incr(String key){
		RAtomicLong raLong = redisson.getAtomicLong(key) ;
		
		long result = raLong.incrementAndGet() ;
		//log.debug("RedisCounter：key="+key+"，new value="+result );
		return result;
	}
	
	public long decr(String key){
		RAtomicLong raLong = redisson.getAtomicLong(key) ;
		long result = raLong.decrementAndGet();
		//log.debug("RedisCounter：key="+key+"，new value="+result );
		return result;
	}
	
	
	public long incrBy(String key, long val){
		RAtomicLong raLong = redisson.getAtomicLong(key) ;
		long result = raLong.addAndGet(val) ;
		//log.debug("RedisCounter：key="+key+"，new value="+result );
		return result;
	}
	
	public long decrBy(String key, long val){
		RAtomicLong raLong = redisson.getAtomicLong(key) ;
		long result = raLong.addAndGet( 0-val) ;
		//log.debug("RedisCounter：key="+key+"，new value="+result );
		return result;
	}
	
	public BigDecimal incrDecimal(String key){
		RAtomicDouble raDecimal = redisson.getAtomicDouble(key) ;
		
		double result = raDecimal.incrementAndGet();
		//log.debug("RedisCounter：key="+key+"，new value="+result );
		return BigDecimal.valueOf(result) ;
	}
	
	public BigDecimal decrDecimal(String key){
		RAtomicDouble raDecimal = redisson.getAtomicDouble(key) ;
		
		double result = raDecimal.decrementAndGet();
		//log.debug("RedisCounter：key="+key+"，new value="+result );
		return BigDecimal.valueOf(result) ;
	}
	
	public BigDecimal incrDecimal(String key, BigDecimal val){
		RAtomicDouble raDecimal = redisson.getAtomicDouble(key) ;
		
		double result = raDecimal.addAndGet( val.doubleValue()) ;
		//log.debug("RedisCounter：key="+key+"，new value="+result );
		return BigDecimal.valueOf(result) ;
	}
	
	public BigDecimal decrDecimal(String key, BigDecimal val){
		RAtomicDouble raDecimal = redisson.getAtomicDouble(key) ;
		
		double result = raDecimal.addAndGet( val.negate().doubleValue()) ;
		//log.debug("RedisCounter：key="+key+"，new value="+result );
		return BigDecimal.valueOf(result) ;
	}

	public RedissonClient getRedisson() {
		return redisson;
	}

	public void setRedisson(RedissonClient redisson) {
		this.redisson = redisson;
	}

	
	
}
