# 工程framework-cache

## 1、概述

​	framework-cache是kivi框架的缓存集成模块项目，在pom文中包含了Spring Boot使用缓存的相关依赖jar包。
本项目中对Ehcahce和Redis缓存进行集成，重点关注于Redis缓存的继承，对Spring Cache的默认的Redis序列化方式进行了个性化，
增加了基于FastJson和Fst两种序列化方式，redis存储的Value由默认的二进制byte形式，改为json字符串形式，方便运行时查看缓存内容。同时，还增加了Redisson客户端的集成。

## 2、主要功能列表

1.  Component组件

   - CacheKit：主要用于获取SpringBoot应用配置的缓存类型，已经对应的CacheManager，目前仅支持：EhCacheCacheManager和RedisCacheManager

2. 缓存工厂

   - EhcacheFactory：Ehcache缓存工厂
   - RedisFactory：Redis缓存工厂

3. 缓存配置

   - RedisConfiguration：Spring Boot的Redis缓存集成配置
   - RedissonConfiguration：Spring Boot的Redisson客户端配置

4. Reis缓存序列化/反序列化

   - FastJson2JsonRedisSerializer：基于FastJson的序列化实现，被缓存的对象无需必须继承java.io.Serializable，使Redis缓存存储的的value值为json字符串格式
   - Fst2JsonRedisSerializer：基于FST的序列化实现，被缓存的对象必须继承java.io.Serializable，使Redis缓存存储的的value值为json字符串格式。注意：使用本这种序列化方法时，不能开启DevTools自动重启。
