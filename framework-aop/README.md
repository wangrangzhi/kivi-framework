# 工程framework-aop

## 1、概述

​	framework-aop是kivi框架的Spring AOP功能集成模块项目，在pom文中包含了Spring Boot使用AOP的相关依赖jar包。
本项目实现了多数据原和访问日志两个AOP。

## 2、主要功能列表

1. 注解

   @DataSource：多数据源标识
   @BizLog：业务日志

2.  Aspect

   - MultiDataSourceAspect： 多数据源切换的Aspect
   - LogAspect：业务日志Aspect