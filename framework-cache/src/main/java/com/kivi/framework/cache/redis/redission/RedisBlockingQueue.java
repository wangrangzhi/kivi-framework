/**
 * 
 */
package com.kivi.framework.cache.redis.redission;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式队列实现类，使用方法：
 * 公共队列建议在：baseUtil的中redis-component.xml声明spring bean
 * 私有队列建议在：各自应用的spring-redis-redisson.xml中声明spring bean
 * @author Eric
 *
 */
public class RedisBlockingQueue {
	private static final Logger log = LoggerFactory.getLogger(RedisBlockingQueue.class);
	
	private static final String RMQ="RMQ_" ;
	
	private RedissonClient redisson;
	private Codec codec ;
	
	private ConcurrentHashMap<String,RBlockingQueue<Object>> blockingQueues = new  ConcurrentHashMap<>(); 
	
	public RedisBlockingQueue(){
	}
	
	
	/**
	 * 入队列
	 * @param value
	 */
	public void push( String name, Object value){
		
		if(value == null){
			log.warn("尝试将null值放入队列{},不入队列，直接返回。", name);
			return;
			//throw new AppException( "E9999991", "数据缓存异常：不能将null值放入队列" );
		}
		
		
		if(!(value instanceof Serializable)){
			log.error("queue-push value must implements Serializable,now queue is unuse please check immediately!!!!!!!!!!!");
			return;
			//throw new AppException( "E9999991", "数据缓存异常，缓存对象没有序列化" );
		}
		
		
		long beginInMillions = 0 ;
		try {
			if( log.isTraceEnabled()){
				beginInMillions = System.currentTimeMillis();
				log.trace("入队列：{}{}，value：{}", RMQ, name,value);
			}
			RBlockingQueue<Object> blockingQueue = this.getBlockingQueue(name) ;
			blockingQueue.offer(value) ;
		} catch (Exception e) {
			log.error("数据入redis队列失败",e);
			//throw new AppException( "E9999991", "数据缓存异常" );
		}
		if( log.isTraceEnabled()){
			long endInMillions = System.currentTimeMillis();
			log.trace("向[{}]队列push数据用时(毫秒)：{}", name, (endInMillions-beginInMillions));
		}
	}
	
	/**
	 * 出队列
	 * @return
	 */
	public Object pop(String name){
		
		Object resultObject = null;
		long beginInMillions = 0;
		if( log.isTraceEnabled()){
			beginInMillions = System.currentTimeMillis();
		}
		
		try {
			RBlockingQueue<Object> blockingQueue = this.getBlockingQueue(name) ;
			resultObject = blockingQueue.poll();
		} catch (Exception e) {
			log.error("数据出队列失败",e);
		}
		
		if( log.isTraceEnabled()){
			long endInMillions = System.currentTimeMillis();
			log.trace("向[{}]队列push数据用时(毫秒)：{}", name, (endInMillions-beginInMillions));
		}
		
		return resultObject;
	}
	
	/**
	 * 阻塞模式出队列
	 * @param timeout  毫秒
	 * @return
	 */
	public Object pop( String name, int timeout, TimeUnit unit ){
		
		Object resultObject = null;
		long beginInMillions = 0;
		if( log.isTraceEnabled()){
			beginInMillions = System.currentTimeMillis();
		}
		
		try {
			RBlockingQueue<Object> blockingQueue = this.getBlockingQueue(name) ;
			resultObject = blockingQueue.poll(timeout, unit) ;
			
		} catch (Exception e) {
			log.error("数据出队列失败",e);
		}
		
		if( log.isTraceEnabled()){
			long endInMillions = System.currentTimeMillis();
			log.trace("向[{}]队列push数据用时(毫秒)：{}", name, (endInMillions-beginInMillions));
		}
		
		return resultObject;
		
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
	 * @return
	 */
	public Object peek(String name){
		RBlockingQueue<Object> blockingQueue = this.getBlockingQueue(name) ;
		return blockingQueue.peek() ;
	}

	public RedissonClient getRedisson() {
		return redisson;
	}

	public void setRedisson(RedissonClient redisson) {
		this.redisson = redisson;
	}

	public Codec getCodec() {
		return codec;
	}

	public void setCodec(Codec codec) {
		this.codec = codec;
	}

	private RBlockingQueue<Object> getBlockingQueue(String name) {
		RBlockingQueue<Object> result = null ;
			if( blockingQueues.containsKey(name))
				result = blockingQueues.get(name) ;
			else{
				result = this.redisson.getBlockingQueue(name);
				
				blockingQueues.put(name, result) ;
			}
		return result;
	}

	
}
