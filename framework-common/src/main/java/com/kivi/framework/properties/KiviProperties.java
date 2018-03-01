package com.kivi.framework.properties;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.kivi.framework.constant.enums.shiro.ShiroAuthType;
import com.kivi.framework.constant.enums.shiro.TokenAuthType;
import com.kivi.framework.util.kit.StrKit;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties( prefix = "framework" )
public class KiviProperties {
    private Common  common;
    private Cache   cache;
    private Dubbo   dubbo;
    private Swagger swagger;
    private Shiro   shiro;

    public KiviProperties() {
        this.common = new Common();
        this.cache = new Cache();
        this.dubbo = new Dubbo();
        this.swagger = new Swagger();
        this.shiro = new Shiro();
    }

    @Getter
    @Setter
    public static class Common {
        private String errorMappingFilePattern = "classpath*:error-mapping-*.properties";
        private String componentScan           = "com.jyt";
        private String txPointcutExpression;
        private String txAdviceRequired        = "insert*,update*,delete*,save*,modify*,add*";
        private String txAdviceSupports        = "find*,get*,query*,list*,select*";
        private String txAdviceNotSupported    = "log*";
    }

    @Getter
    @Setter
    public static class Cache {
        private int expiration = 3600;
    }

    @Getter
    @Setter
    public static class Swagger {
        private Boolean enabled              = true;
        private String  title;
        private String  description;
        private String  version              = "1.0.0";
        private String  termsOfServiceUrl;
        private Boolean authorizationEnabled = true;

        public String getTitleUTF8() {
            String utf8 = title;

            try {
                utf8 = new String(title.getBytes("iso-8859-1"), "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return utf8;
        }

        public String getDescriptionUTF8() {
            String utf8 = description;

            try {
                utf8 = new String(description.getBytes("iso-8859-1"), "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return utf8;
        }
    }

    @Getter
    @Setter
    public static class Shiro {
        private Boolean       enabled                   = true;
        private ShiroAuthType type;
        private Long          sessionTimeout            = 3600000L;
        private Long          sessionValidationInterval = 900000L;
        private Long          rememberMeCookieTtl       = 604800L;
        private Token         token;
        private Filters       filters;
        private Forward       forward;

        @Getter
        @Setter
        public static class Token {
            private TokenAuthType authType = TokenAuthType.JWT;
            private Long          ttl      = 1800L;
            private Jwt           jwt;

            @Getter
            @Setter
            public static class Jwt {
                private String                             issuer     = "jytpay";
                private String                             secretSeed = "jytpay.jwt";
                private io.jsonwebtoken.SignatureAlgorithm signAlg    = SignatureAlgorithm.HS256;
            }
        }

        @Getter
        @Setter
        public static class Filters {
            private String anon   = "/index,/kaptcha,/**/login,/**/reg,/**/forget,/**/passReset,/favicon.ico,/api/auth";
            // 退出url过滤器
            private String logout = "/**/logout";
            // 需要管理员权限访问的url过滤器
            private String admin  = "/console/**";
            // 需要用户权限访问的url过滤器
            private String user   = "/member/**";
            // 需要token认证的访问url过滤器
            private String token  = "/api/**";

            public Map<String, String> getAnonMap( String value ) {
                return getFilterMap(value, anon);
            }

            public Map<String, String> getLogoutMap( String value ) {
                return getFilterMap(value, logout);
            }

            public Map<String, String> getAdminMap( String value ) {
                return getFilterMap(value, admin);
            }

            public Map<String, String> getUserMap( String value ) {
                return getFilterMap(value, user);
            }

            public Map<String, String> getTokenMap( String value ) {
                return getFilterMap(value, token);
            }

            private Map<String, String> getFilterMap( String value, String filter ) {
                Map<String, String> map = new LinkedHashMap<String, String>();

                String[] filters = StrKit.split(filter, ",");
                for (String url : filters)
                    map.put(url, value);

                return map;
            }

        }

        @Getter
        @Setter
        public static class Forward {
            // 认证未通过重定向url
            private String unauthorized = "/html/403.html";
            // 管理员默认登陆url
            private String adminLogin   = "/console/login";
            // 管理员默认登录成功url
            private String adminSuccess = "/console/index";
            // 用户默认登陆url
            private String userLogin    = "/member/login";
            // 用户默认登录成功url
            private String userSuccess  = "/member/index";
        }
    }

    @Setter
    @Getter
    public static class Dubbo {
        private Application application;
        private Registry    registry;
        private Protocol    protocol;

        @Getter
        @Setter
        public static class Application {
            private String name;
        }

        @Getter
        @Setter
        public static class Registry {
            private String address;
            private String protocol;
        }

        @Getter
        @Setter
        public static class Protocol {
            private String  name;
            private Integer port;
            private String  threadpool;
            private Integer threads;
        }
    }

}
