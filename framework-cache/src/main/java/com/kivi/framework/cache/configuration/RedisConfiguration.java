package com.kivi.framework.cache.configuration;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kivi.framework.cache.redis.factory.KtfRedisCacheManager;
import com.kivi.framework.cache.redis.serializer.FastJson2JsonRedisSerializer;
import com.kivi.framework.cache.redis.serializer.Fst2JsonRedisSerializer;
import com.kivi.framework.properties.KtfProperties;

@Configuration
@Import( CacheProperties.class )
@ConditionalOnProperty( name = { "spring.cache.type" }, havingValue = "redis", matchIfMissing = false )
@EnableCaching( proxyTargetClass = true, mode = AdviceMode.PROXY )
public class RedisConfiguration extends CachingConfigurerSupport {
    private static final Logger log = LoggerFactory.getLogger(RedisConfiguration.class);

    @Bean
    public CacheManager cacheManager( RedisTemplate<?, ?> redisTemplate, KtfProperties ktfProperties ) {
        KtfRedisCacheManager manager = new KtfRedisCacheManager(redisTemplate);
        // Sets the default expire time (in seconds)
        manager.setDefaultExpiration(ktfProperties.getCache().getExpiration());
        return manager;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate( RedisConnectionFactory connectionFactory ) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(connectionFactory);
        setFastJson2JsonRedisSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations( RedisTemplate<String, Object> redisTemplate ) {
        return redisTemplate.opsForHash();
    }

    /**
     * 实例化 ValueOperations 对象,可以使用 String 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations( RedisTemplate<String, Object> redisTemplate ) {
        return redisTemplate.opsForValue();
    }

    /**
     * 实例化 ListOperations 对象,可以使用 List 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations( RedisTemplate<String, Object> redisTemplate ) {
        return redisTemplate.opsForList();
    }

    /**
     * 实例化 SetOperations 对象,可以使用 Set 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations( RedisTemplate<String, Object> redisTemplate ) {
        return redisTemplate.opsForSet();
    }

    /**
     * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
     *
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations( RedisTemplate<String, Object> redisTemplate ) {
        return redisTemplate.opsForZSet();
    }

    /**
     * 设置序列化方法:Jackson
     */
    @SuppressWarnings( "unused" )
    private void setJackson2JsonRedisSerializer( RedisTemplate<?, ?> template ) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(template.getStringSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
    }

    /**
     * 设置序列化方法:FastJson
     */

    private void setFastJson2JsonRedisSerializer( RedisTemplate<?, ?> template ) {
        FastJson2JsonRedisSerializer<Object> fastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer<>(
                Object.class);
        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(fastJson2JsonRedisSerializer);

        template.setDefaultSerializer(fastJson2JsonRedisSerializer);
        template.setHashKeySerializer(template.getStringSerializer());
        template.setHashValueSerializer(fastJson2JsonRedisSerializer);
    }

    /**
     * 设置序列化方法:Fst，不适用于使用DevTools自动重启开启的情况
     */
    @SuppressWarnings( "unused" )
    private void setFst2JsonRedisSerializer( RedisTemplate<?, ?> template ) {
        Fst2JsonRedisSerializer<Object> fst2JsonRedisSerializer = new Fst2JsonRedisSerializer<>(Object.class);

        template.setKeySerializer(template.getStringSerializer());
        template.setValueSerializer(fst2JsonRedisSerializer);
        template.setDefaultSerializer(fst2JsonRedisSerializer);
        template.setHashKeySerializer(template.getStringSerializer());
        template.setHashValueSerializer(fst2JsonRedisSerializer);
    }

    /**
     * 自定义key. 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        log.trace("RedisConfiguration.keyGenerator()");

        return new KeyGenerator() {

            @Override
            public Object generate( Object o, Method method, Object... objects ) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                log.trace("keyGenerator=" + sb.toString());
                return sb.toString();
            }
        };

    }

}
