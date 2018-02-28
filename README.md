# kivi-framework
Kivi-framework是基于Spring Boot封装的一个Java开发框架，本框架既包括了：pom文件、配置文件、Freemarker模板、AOP、Mvc、Swagger2、RESTful API、事务配置等Spring Boot的基本功能，同时也包括了：Druid、MyBatis、Dubbo、Redis、Ehcache、RabbitMq、Shiro、Spring-Batch等常用组件的集成。</br>
工程结构：</br>
├── kivi-admin-server&nbsp;&nbsp;&nbsp;&nbsp;               Actuator端点的监控Server</br>
├── kivi-framework-actuator&nbsp;&nbsp;&nbsp;&nbsp;         Actuator功能封装模块</br>
├── kivi-framework-aop&nbsp;&nbsp;&nbsp;&nbsp;              AOP功能封装模块</br>
├── kivi-framework-batch&nbsp;&nbsp;&nbsp;&nbsp;            Spring-Batch功能封装模块</br>
├── kivi-framework-cache&nbsp;&nbsp;&nbsp;&nbsp;            缓存功能封装模块，集成Redis和Ehcache</br>
├── kivi-framework-common&nbsp;&nbsp;&nbsp;&nbsp;           工具类以及自定义配置文件属性</br>
├── kivi-framework-db&nbsp;&nbsp;&nbsp;&nbsp;               数据库功能封装模块，采用Druid和MyBatis</br>
├── kivi-framework-disruptor&nbsp;&nbsp;&nbsp;&nbsp;        Disruptor并发框架封装模块</br>
├── kivi-framework-dubbo&nbsp;&nbsp;&nbsp;&nbsp;            Dubbo功能封装模块</br>
├── kivi-framework-mq&nbsp;&nbsp;&nbsp;&nbsp;               MQ功能封装模块，支持RabbitMQ和RocketMq(即将实现)</br>
├── kivi-framework-web&nbsp;&nbsp;&nbsp;&nbsp;              Web基础功能封装模块，包括：MVC、Swagger2、RESTful</br>
├── kivi-framework-web-base&nbsp;&nbsp;&nbsp;&nbsp;         Web管理后台基本功能封装模块，包括：shiro、验证码、用户、角色、菜单等基本功能。</br>
├── kivi-framework-webjar&nbsp;&nbsp;&nbsp;&nbsp;           Web静态资源模块，包括：js、css、Freemarker模板等</br>
└── pom.xml&nbsp;&nbsp;&nbsp;&nbsp;                         Kivi-framework的parent pom文件</br>
