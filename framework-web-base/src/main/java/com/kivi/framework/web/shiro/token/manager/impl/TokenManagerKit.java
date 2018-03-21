package com.kivi.framework.web.shiro.token.manager.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kivi.framework.component.SpringContextHolder;
import com.kivi.framework.constant.enums.shiro.TokenAuthType;
import com.kivi.framework.properties.KtfProperties;
import com.kivi.framework.util.kit.DateTimeKit;
import com.kivi.framework.web.shiro.token.StatelessToken;
import com.kivi.framework.web.shiro.token.helper.TokenCacheFactory;
import com.kivi.framework.web.shiro.token.manager.TokenManager;
import com.kivi.framework.web.shiro.util.JwtUtil;

@Component
public class TokenManagerKit {

    @Autowired
    private KtfProperties ktfProperties;

    private TokenManager  tokenManager;

    @PostConstruct
    private void init() {

        TokenAuthType tokenType = ktfProperties.getShiro().getToken().getAuthType();
        TokenManager tokenManager = null;

        if (TokenAuthType.UUID.compareTo(tokenType) == 0) {
            tokenManager = new UuidTokenManagerImpl();
        }
        else if (TokenAuthType.JWT.compareTo(tokenType) == 0) {
            tokenManager = new SimpleJwtTokenManagerImpl();
            JwtUtil jwtUtil = new JwtUtil(ktfProperties);
            ((SimpleJwtTokenManagerImpl) tokenManager).setJwtUtil(jwtUtil);
            // 用户tokenCache管理
            ((SimpleJwtTokenManagerImpl) tokenManager).setTokenCache(TokenCacheFactory.me().tokenCache());
            // token失效时间
            ((SimpleJwtTokenManagerImpl) tokenManager).setExpirateTime(ktfProperties.getShiro().getToken().getTtl());
        }
        else if (TokenAuthType.JWT_RA.compareTo(tokenType) == 0) {
            tokenManager = new RaFilterJwtTokenManagerImpl();
            JwtUtil jwtUtil = new JwtUtil(ktfProperties);
            ((RaFilterJwtTokenManagerImpl) tokenManager).setJwtUtil(jwtUtil);
            // 用户tokenCache管理
            ((RaFilterJwtTokenManagerImpl) tokenManager).setTokenCache(TokenCacheFactory.me().tokenCache());
            // token失效时间
            ((RaFilterJwtTokenManagerImpl) tokenManager).setExpirateTime(ktfProperties.getShiro().getToken().getTtl());
        }

    }

    public static TokenManagerKit me() {
        return SpringContextHolder.getBean(TokenManagerKit.class);
    }

    public StatelessToken createToken( String userCode ) {
        return this.createToken(userCode, DateTimeKit.nowCompact());
    }

    public StatelessToken createToken( String userCode, String password ) {
        return tokenManager.createToken(userCode, password);
    }

    public boolean checkToken( StatelessToken statelessToken ) {
        return tokenManager.checkToken(statelessToken);
    }

    public boolean check( String authentication ) {
        return tokenManager.check(authentication);
    }

    public StatelessToken getToken( String authentication ) {
        return tokenManager.getToken(authentication);
    }

    public void deleteToken( String userCode ) {
        tokenManager.deleteToken(userCode);
    }

}
