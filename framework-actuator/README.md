# 工程framework-actuator

## 1、概述

​	framework-actuator是kivi框架的Spring Actuator功能集成模块项目，在pom文中包含了Spring Boot使用actuator的相关依赖jar包。
本项目实现了一个框架自定义的交易度量，扩展了标准的actuator。

## 2、主要功能列表

1. 注解

   @TranMetric：标记需要收集访问数据的方法

2.  Aspect

   - TranMetricAspect：交易度量拦截AOP