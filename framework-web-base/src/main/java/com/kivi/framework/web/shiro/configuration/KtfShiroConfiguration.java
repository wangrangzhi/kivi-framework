package com.kivi.framework.web.shiro.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;

import com.kivi.framework.constant.enums.shiro.TokenAuthType;
import com.kivi.framework.properties.KtfProperties;
import com.kivi.framework.web.shiro.cache.ShiroRedisSessionDAO;
import com.kivi.framework.web.shiro.factory.ShiroFactroy;
import com.kivi.framework.web.shiro.filter.AdminFormAuthFilter;
import com.kivi.framework.web.shiro.filter.ClientFormAuthFilter;
import com.kivi.framework.web.shiro.filter.StatelessAuthcFilter;
import com.kivi.framework.web.shiro.modular.CustomModularRealmAuthenticator;
import com.kivi.framework.web.shiro.modular.UrlPermissionResovler;
import com.kivi.framework.web.shiro.realm.StateAuthRealm;
import com.kivi.framework.web.shiro.realm.StatelessRealm;
import com.kivi.framework.web.shiro.token.helper.TokenCacheFactory;
import com.kivi.framework.web.shiro.token.manager.TokenManager;
import com.kivi.framework.web.shiro.token.manager.impl.RaFilterJwtTokenManagerImpl;
import com.kivi.framework.web.shiro.token.manager.impl.SimpleJwtTokenManagerImpl;
import com.kivi.framework.web.shiro.token.manager.impl.UuidTokenManagerImpl;
import com.kivi.framework.web.shiro.util.JwtUtil;

/**
 * shiro配置
 *
 */
@Configuration
@ConditionalOnProperty( prefix = "framework.shiro", name = "enabled", havingValue = "true", matchIfMissing = false )
@DependsOn( "springContextHolder" )
public class KtfShiroConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(KtfShiroConfiguration.class);

    /**
     * token管理类
     * 
     * @param cacheManager
     * @param bootProperties
     * @return
     */
    @Bean
    public TokenManager tokenManager( KtfProperties ktfProperties ) {
        logger.info("创建token管理器");

        TokenAuthType tokenType = ktfProperties.getShiro().getToken().getAuthType();
        logger.info("Token认证方式：{}", tokenType);

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

        return tokenManager;
    }

    /**
     * 无状态域
     * 
     * @param tokenManager
     * @param principalService
     *            登陆账号服务需要实现PrincipalService接口
     * @param authorizationService
     *            授权服务 需要实现authorizationService接口
     * @return
     */
    @Bean( name = "statelessRealm" )
    @Scope( ConfigurableBeanFactory.SCOPE_PROTOTYPE )
    public StatelessRealm statelessRealm( TokenManager tokenManager ) {
        logger.debug("创建无状态域StatelessRealm: statelessRealm");
        StatelessRealm realm = new StatelessRealm();
        realm.setTokenManager(tokenManager);
        return realm;
    }

    /**
     * 管理后端认证reaml
     * 
     * @return
     */
    @Bean( name = "adminShiroRealm" )
    @Scope( ConfigurableBeanFactory.SCOPE_PROTOTYPE )
    public StateAuthRealm adminShiroRealm() {
        logger.debug("创建管理后端状态域StateAuthRealm: adminShiroRealm");
        StateAuthRealm realm = new StateAuthRealm();
        return realm;
    }

    /**
     * 客户端认证reaml
     * 
     * @return
     */
    @Bean( name = "clientShiroRealm" )
    @Scope( ConfigurableBeanFactory.SCOPE_PROTOTYPE )
    public StateAuthRealm clientShiroRealm() {
        logger.debug("创建客户状态域StateAuthRealm: clientShiroRealm");
        StateAuthRealm realm = new StateAuthRealm();
        return realm;
    }

    @Bean
    public EnterpriseCacheSessionDAO sessionDAO( CacheProperties cacheProperties,
            KtfProperties ktfProperties, RedisTemplate<String, Object> redisTemplate ) {
        if (cacheProperties.getType().compareTo(CacheType.EHCACHE) == 0) {
            EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
            sessionDAO.setCacheManager(ShiroFactroy.me().getShiroCacheManager());
            return sessionDAO;
        }
        else if (cacheProperties.getType().compareTo(CacheType.REDIS) == 0) {
            ShiroRedisSessionDAO redisSessionDAO = new ShiroRedisSessionDAO(redisTemplate);
            redisSessionDAO.setExpireTime(ktfProperties.getShiro().getSessionTimeout());
            return redisSessionDAO;
        }
        return null;
    }

    /**
     * @see DefaultWebSessionManager
     * @return
     */
    @Bean( name = "sessionManager" )
    public DefaultWebSessionManager defaultWebSessionManager( CacheProperties cacheProperties,
            KtfProperties ktfProperties,
            RedisTemplate<String, Object> redisTemplate ) {
        logger.debug("创建session管理器");
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        sessionManager.setSessionDAO(sessionDAO(cacheProperties, ktfProperties, redisTemplate));
        sessionManager.setCacheManager(ShiroFactroy.me().getShiroCacheManager());
        sessionManager.setSessionValidationInterval(ktfProperties.getShiro().getSessionValidationInterval());
        sessionManager.setGlobalSessionTimeout(ktfProperties.getShiro().getSessionTimeout());
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("KTF.SHIRO.ID");
        cookie.setHttpOnly(true);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }

    /**
     * 会话管理类 禁用session
     * 
     * @return
     */
    /*
     * @Bean(name = "noSessionManager") public DefaultSessionManager
     * defaultSessionManager() {
     * logger.info("ShiroConfig.getDefaultSessionManager()");
     * DefaultSessionManager manager = new DefaultSessionManager();
     * manager.setSessionValidationSchedulerEnabled(false); return manager; }
     */

    /**
     * 安全管理类
     * 
     * @param statelessRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager( CookieRememberMeManager rememberMeManager,
            DefaultWebSessionManager sessionManager, StatelessRealm statelessRealm ) {
        logger.info("创建 DefaultWebSecurityManager");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();

        Map<String, Object> shiroAuthenticatorRealms = new HashMap<>();

        StateAuthRealm adminShiroRealm = adminShiroRealm();
        StateAuthRealm clientShiroRealm = clientShiroRealm();

        shiroAuthenticatorRealms.put("adminShiroRealm", adminShiroRealm);
        shiroAuthenticatorRealms.put("clientShiroRealm", clientShiroRealm);
        shiroAuthenticatorRealms.put("statelessRealm", statelessRealm);

        Collection<Realm> shiroAuthorizerRealms = new ArrayList<Realm>();
        shiroAuthorizerRealms.add(adminShiroRealm);
        shiroAuthorizerRealms.add(clientShiroRealm);
        shiroAuthorizerRealms.add(statelessRealm);

        CustomModularRealmAuthenticator customModularRealmAuthenticator = new CustomModularRealmAuthenticator();
        customModularRealmAuthenticator.setDefinedRealms(shiroAuthenticatorRealms);
        customModularRealmAuthenticator.setAuthenticationStrategy(authenticationStrategy());
        manager.setAuthenticator(customModularRealmAuthenticator);

        ModularRealmAuthorizer customModularRealmAuthorizer = new ModularRealmAuthorizer();
        customModularRealmAuthorizer.setRealms(shiroAuthorizerRealms);
        customModularRealmAuthorizer.setPermissionResolver(new UrlPermissionResovler());
        manager.setAuthorizer(customModularRealmAuthorizer);

        // 注入缓存管理器;
        manager.setCacheManager(ShiroFactroy.me().getShiroCacheManager());// 这个如果执行多次，也是同样的一个对象;

        manager.setSessionManager(sessionManager);
        manager.setRememberMeManager(rememberMeManager);
        // 设置了SecurityManager采用使用SecurityUtils的静态方法 获取用户等
        SecurityUtils.setSecurityManager(manager);
        return manager;
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager( SimpleCookie rememberMeCookie ) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decodeBase64("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie( KtfProperties ktfProperties ) {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(ktfProperties.getShiro().getRememberMeCookieTtl().intValue());// 7天
        return simpleCookie;
    }

    /**
     * Shiro生命周期处理
     * 
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        logger.info("ShiroConfig.getLifecycleBeanPostProcessor()");
        return new LifecycleBeanPostProcessor();
    }

    @ConditionalOnMissingBean
    @Bean( name = "defaultAdvisorAutoProxyCreator" )
    @DependsOn( "lifecycleBeanPostProcessor" )
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        logger.debug("ShiroConfiguration.getDefaultAdvisorAutoProxyCreator()");
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    /**
     * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
     * 
     * @param securityManager
     * @return
     */
    @Bean( name = "authorizationAttributeSourceAdvisor" )
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager ) {
        logger.debug("ShiroConfiguration.authorizationAttributeSourceAdvisor()");
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 身份验证过滤器
     * 
     * @param manager
     * @param tokenManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean( SecurityManager manager, TokenManager tokenManager,
            KtfProperties ktfProperties ) {
        logger.info("ShiroConfig.getShiroFilterFactoryBean()");
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        Map<String, Filter> filters = new HashMap<String, Filter>();
        // 无需增加 shiro默认会添加该filter
        // filters.put("anon", anonymousFilter());

        // 无状态授权过滤器
        // 特别注意！不能声明为bean 否则shiro无法管理该filter生命周期，该过滤器会执行其他过滤器拦截过的路径
        filters.put("admin", adminFormAuthFilter(ktfProperties));
        filters.put("client", clientFormAuthFilter(ktfProperties));
        filters.put("token", statelessAuthcFilter(tokenManager));
        bean.setFilters(filters);

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.putAll(ktfProperties.getShiro().getFilters().getAnonMap("anon"));
        filterChainDefinitionMap.putAll(ktfProperties.getShiro().getFilters().getLogoutMap("logout"));
        filterChainDefinitionMap.putAll(ktfProperties.getShiro().getFilters().getAdminMap("admin"));
        filterChainDefinitionMap.putAll(ktfProperties.getShiro().getFilters().getUserMap("client"));
        filterChainDefinitionMap.putAll(ktfProperties.getShiro().getFilters().getTokenMap("token"));

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        // anon（匿名） org.apache.shiro.web.filter.authc.AnonymousFilter
        // authc（身份验证）
        // org.apache.shiro.web.filter.authc.FormAuthenticationFilter
        // authcBasic（http基本验证）
        // org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
        // logout（退出） org.apache.shiro.web.filter.authc.LogoutFilter
        // noSessionCreation（不创建session）
        // org.apache.shiro.web.filter.session.NoSessionCreationFilter
        // perms(许可验证)
        // org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
        // port（端口验证） org.apache.shiro.web.filter.authz.PortFilter
        // rest (rest方面)
        // org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
        // roles（权限验证）
        // org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
        // ssl（ssl方面） org.apache.shiro.web.filter.authz.SslFilter
        // member （用户方面） org.apache.shiro.web.filter.authc.UserFilter
        // user 表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe

        // 注意是LinkedHashMap 保证有序
        // 1， 相同url规则，后面定义的会覆盖前面定义的(执行的时候只执行最后一个)。
        // 2， 两个url规则都可以匹配同一个url，只执行第一个
        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        // filterChainDefinitionMap.put("/index", "anon");
        // filterChainDefinitionMap.put("/kaptcha", "anon");
        // filterChainDefinitionMap.put("/**/login", "anon");
        // filterChainDefinitionMap.put("/**/logout", "logout");
        // filterChainDefinitionMap.put("/**/reg", "anon");
        // filterChainDefinitionMap.put("/**/forget", "anon");
        // filterChainDefinitionMap.put("/**/passReset", "anon");
        // filterChainDefinitionMap.put("/favicon.ico", "anon");
        // filterChainDefinitionMap.put("/api/auth", "anon");
        // filterChainDefinitionMap.put("/api/**", "stateless");
        // filterChainDefinitionMap.put("/console/**", "admin");
        // filterChainDefinitionMap.put("/member/**", "client");

        // 未授权界面;
        bean.setUnauthorizedUrl(ktfProperties.getShiro().getForward().getUnauthorized());
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }

    /**
     * 管理端Auth Filter
     * 
     * @return
     */
    public AdminFormAuthFilter adminFormAuthFilter( KtfProperties ktfProperties ) {
        AdminFormAuthFilter filter = new AdminFormAuthFilter();
        filter.setLoginUrl(ktfProperties.getShiro().getForward().getAdminLogin());
        filter.setSuccessUrl(ktfProperties.getShiro().getForward().getAdminSuccess());
        return filter;
    }

    /**
     * 客户端Auth Filter
     * 
     * @return
     */
    public ClientFormAuthFilter clientFormAuthFilter( KtfProperties ktfProperties ) {
        ClientFormAuthFilter filter = new ClientFormAuthFilter();
        filter.setLoginUrl(ktfProperties.getShiro().getForward().getUserLogin());
        filter.setSuccessUrl(ktfProperties.getShiro().getForward().getUserSuccess());
        return filter;
    }

    /**
     * 
     * @Function: ShiroConfig::anonymousFilter
     * @Description: 该过滤器无需增加 shiro默认会添加该filter
     * @return
     */
    public AnonymousFilter anonymousFilter() {
        logger.info("ShiroConfig.anonymousFilter()");
        return new AnonymousFilter();
    }

    /**
     * 
     * @Function: ShiroConfig::statelessAuthcFilter
     * @Description: 无状态授权过滤器 注意不能声明为bean 否则shiro无法管理该filter生命周期，<br>
     *               该过滤器会执行其他过滤器拦截过的路径
     * @param tokenManager
     * @return
     */
    public StatelessAuthcFilter statelessAuthcFilter( TokenManager tokenManager ) {
        logger.info("ShiroConfig.statelessAuthcFilter()");
        StatelessAuthcFilter statelessAuthcFilter = new StatelessAuthcFilter();
        statelessAuthcFilter.setTokenManager(tokenManager);
        return statelessAuthcFilter;
    }

    /**
     * Shiro默认提供了三种 AuthenticationStrategy 实现： AtLeastOneSuccessfulStrategy
     * ：其中一个通过则成功。 FirstSuccessfulStrategy ：其中一个通过则成功，但只返回第一个通过的Realm提供的验证信息。
     * AllSuccessfulStrategy ：凡是配置到应用中的Realm都必须全部通过。 authenticationStrategy
     * 
     * @return
     */
    @Bean( name = "authenticationStrategy" )
    public AuthenticationStrategy authenticationStrategy() {
        logger.debug("ShiroConfiguration.authenticationStrategy()");
        return new FirstSuccessfulStrategy();
    }

}
