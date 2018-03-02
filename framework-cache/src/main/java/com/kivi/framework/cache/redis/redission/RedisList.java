/**
 * 
 */
package com.kivi.framework.cache.redis.redission;


import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 分布式MAP实现类，
 * 公共MAP建议在：baseUtil的中redis-component.xml声明spring bean
 * 私有MAP建议在：各自应用的spring-redis-redisson.xml中声明spring bean
 * @author Eric
 *
 */
public class RedisList {
	private static final Logger log = LoggerFactory.getLogger(RedisList.class);
	
	private static final String RMQ="RLIST_" ;
	
	private RedissonClient redisson;
	private Codec codec ;
	
	private ConcurrentMap<String,RList<Object>> rLists = new  ConcurrentHashMap<>(); 
	
	public RedisList(){
		
	}
	
	
	private synchronized RList<Object> getRList( String name)  {
		RList<Object> rList = redisson.getList(RMQ + name, codec);
		
		return rList;
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


	/**
	 * Appends the specified element to the end of this list (optional operation). 
	 * Lists that support this operation may place limitations on what elements may be added to this list. 
	 * In particular, some lists will refuse to add null elements, and others will impose restrictions 
	 * on the type of elements that may be added. List classes should clearly specify in their documentation
	 *  any restrictions on what elements may be added.
	 * @param key
	 * @param value
	 * @return	true (as specified by Collection.add)
	 */
	public boolean add( String name , Object value ){
		if(value == null){
			log.warn("尝试将null值放入MAP"+name+"。不入队列，直接返回。");
			return false;
		}
		
		
		if(!(value instanceof Serializable)){
			log.error("queue-push value must implements Serializable,now queue is unuse please check immediately!!!!!!!!!!!");
			return false;
		}
		return getRList(name).add(value) ;
	}
	
	
	/**
	 * Returns the element at the specified position in this list.
	 * @param index
	 * @param key
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public Object get( String name ,int index ){
		return  getRList(name).get(index);
	}
	
	/**
	 * Remove the element at the specified position in this list.
	 * @param index
	 * @param key
	 * @return 
	 */
	public void remove( String name ,int index){
		 getRList(name).fastRemove(index); 
	}
	
	/**
	 * Returns the number of elements in this list. If this list contains more than Integer.MAX_VALUE elements, 
	 * returns Integer.MAX_VALUE.
	 * @param name
	 * @return
	 */
	public int size(String name){
		return getRList(name).size();
	}
	
	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * @param name
	 * @return
	 */
	public Iterator<Object> iterator(String name){
		return getRList(name).iterator();
	}
	
	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element). 
	 * The returned array will be "safe" in that no references to it are maintained by this list.
	 * (In other words, this method must allocate a new array even if this list is backed by an array). 
	 * The caller is thus free to modify the returned array. 
	 * This method acts as bridge between array-based and collection-based APIs.
	 * @param name
	 * @return
	 */
	public Object[] toArray(String name){
		return getRList(name).toArray() ;
	}
	
	
	/**
	 * Returns true if this list contains the specified element. 
	 * More formally, returns true if and only if this list contains at least 
	 * one element e such that (o==null ? e==null : o.equals(e)).
	 * @param name
	 * @param value
	 * @return
	 */
	public boolean contains(String name, Object value ){
		return getRList(name).contains(value);
	}
}
