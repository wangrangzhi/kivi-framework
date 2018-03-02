package com.kivi.framework.cache.redis.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kivi.framework.constant.Constant;
import com.kivi.framework.util.kit.StrKit;

/**
 * Redis fastjson Serializer
 * 
 * @author Eric
 *
 * @param <T>
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    private static final Logger log = LoggerFactory.getLogger(FastJson2JsonRedisSerializer.class);

    private Class<T>            clazz;

    public FastJson2JsonRedisSerializer( Class<T> clazz ) {
        super();
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize( T t ) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        byte[] bytes = JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(Constant.DEFAULT_CHARSET);

        if (log.isTraceEnabled())
            log.trace("json:{}", StrKit.str(bytes, Constant.DEFAULT_CHARSET.name()));

        return bytes;
    }

    @Override
    public T deserialize( byte[] bytes ) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, Constant.DEFAULT_CHARSET);

        if (log.isTraceEnabled())
            log.trace("json:{}", str);

        T obj = JSON.parseObject(str, clazz);

        return obj;
    }

}
