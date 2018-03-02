/**
 * 
 */
package com.kivi.framework.cache.redis.redission;


import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.redisson.api.RFuture;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式Set实现类，
 * 公共Set建议在：baseUtil的中redis-component.xml声明spring bean
 * 私有Set建议在：各自应用的spring-redis-redisson.xml中声明spring bean
 * @author Eric
 *
 */
public class RedisSet {
	private static final Logger log = LoggerFactory.getLogger(RedisSet.class);
	
	private static final String RMQ="RSET_" ;
	
	private RedissonClient redisson;
	private Codec codec ;
	
	private static ConcurrentMap<String,RSet<String>> rRests = new ConcurrentHashMap<String, RSet<String>>() ; 
	
	public RedisSet(){
		
	}
	
	
	private RSet<String> getRSet( String name)  {
		RSet<String> rRest = null ;
		String rsetName = RMQ + name;
		if( rRests.containsKey(rsetName))
			rRest = rRests.get(rsetName);
		else{
			rRest = redisson.getSet(rsetName, codec);
			rRests.put(rsetName, rRest);
		}
		
		return rRest;
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
	 * 向RSet中增加一个数据
	 * @param value
	 */
	public void add( String name, String value ){
		RSet<String> rset = this.getRSet(name);
		
		rset.add(value) ;
	}
	
	/**
	 * 批量向RSet中增加数据
	 * @param name
	 * @param value
	 */
	public void addAll( String name, List<String> values ){
		long start = 0,end = 0 ;
		log.trace("开始向RSet[{}]中批量添加记录，RSet中当前数量{},新增数据数量:{}", name,this.getRSet(name).size(), values.size() );
		if(log.isTraceEnabled()){
			start = System.currentTimeMillis();
		}
		
		this.getRSet(name).addAll(values);
		
		
		if(log.isTraceEnabled()){
			end = System.currentTimeMillis();
			log.trace("结束向RSet[{}]中批量添加记录,当前数量{}，耗时：{}ms", name, this.getRSet(name).size(), (end-start) );
		}
	}
	
	/**
	 * 异步批量向RSet中增加数据
	 * @param name
	 * @param value
	 */
	public RFuture<Boolean> addAllAsync( String name, List<String> values ){
		long start = 0,end = 0 ;
		log.trace("开始向RSet[{}]中批量添加记录，RSet中当前数量{},新增数据数量:{}", name,this.getRSet(name).size(), values.size() );
		if(log.isTraceEnabled()){
			start = System.currentTimeMillis();
		}
		
		RFuture<Boolean> future = this.getRSet(name).addAllAsync(values);
		
		
		if(log.isTraceEnabled()){
			end = System.currentTimeMillis();
			log.trace("结束向RSet[{}]中批量添加记录,当前数量{}，耗时：{}ms", name, this.getRSet(name).size(), (end-start) );
		}
		
		return future;
	}
	
	
	
	/**
     * 以first为基准，比较两个set集合中数据，
     * 
     * @param first 	基准Set
     * @param second 	比较set	
     * @return values
     */
	public Set<String> diff( String first, String second){
		long start = 0,end = 0 ;
		log.trace("开始比较RSet[{}]和RSet[{}]", first, second );
		if(log.isTraceEnabled()){
			start = System.currentTimeMillis();
		}
		
		RSet<String> baseRset = this.getRSet(first);
		
		RSet<String> compareRset = this.getRSet(second);
		
		
		Set<String> result =  baseRset.readDiff( compareRset.getName() ) ;
		
		if(log.isTraceEnabled()){
			end = System.currentTimeMillis();
			log.trace("结束RSet[{}]和RSet[{}]的比较，不一致数据数量：{},耗时：{}ms", first, second, result.size(), (end-start) );
		}
		
		return result;
	}


	
	/**
	 * 从RSet中删除指定元素
	 * @param name
	 * @param value
	 */
	public void remove( String name ,String value){
		getRSet(name).remove(value) ;
	}
	
	/**
	 * Returns the number of elements in this set (its cardinality). If this set contains more than Integer.
	 * MAX_VALUE elements, returns Integer.MAX_VALUE.
	 * @param name
	 * @return
	 */
	public int size(String name){
		return getRSet(name).size();
	}
	
	/**
	 * 清空RSet
	 * @param name
	 */
	public void clear( String name){
		getRSet(name).clear();
	}
}
