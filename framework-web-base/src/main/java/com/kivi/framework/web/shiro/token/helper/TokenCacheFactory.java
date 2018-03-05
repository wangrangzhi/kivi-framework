package com.kivi.framework.web.shiro.token.helper;

import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.kivi.framework.cache.CacheKit;
import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.constant.GlobalErrorConst;
import com.kivi.framework.exception.AppException;

@Component
@ConditionalOnProperty( prefix = "framework.shiro", name = "enabled", havingValue = "true", matchIfMissing = false )
@DependsOn( "springContextHolder" )
public class TokenCacheFactory {

    public static TokenCacheFactory me() {
        return SpringContextHolder.getBean(TokenCacheFactory.class);
    }

    @SuppressWarnings( "unchecked" )
    public ITokenCache tokenCache() {
        ITokenCache tokenCache = null;

        if (CacheType.EHCACHE.compareTo(CacheKit.me().getCacheType()) == 0) {
            tokenCache = new TokenEhCache(CacheKit.me().getEhCacheManager().getCacheManager());
        }
        else if (CacheType.REDIS.compareTo(CacheKit.me().getCacheType()) == 0) {

            tokenCache = new TokenRedis(CacheKit.me().getRedisCacheManager().getRedisOperations());
        }
        else {
            throw new AppException(GlobalErrorConst.E_NOT_IMPLEMENT);
        }

        return tokenCache;
    }

}
