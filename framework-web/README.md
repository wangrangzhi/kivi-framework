# 工程framework-web

## 1、概述

​	framework-web是kivi框架的基本功web集成模块项目，在pom文中包含了Spring Boot使用web应用的相关依赖jar包。
本项目使用FastJson改进了Spring默认的HttpMessageConverter，集成了Swagger2组件，并定义了框架Controller基类、全局的控制器、错误页面默认跳转。
同时提供了Http请求操作和Web防火墙工具类。

## 2、主要功能列表

1. 注解

   @Permission：web权限注解

2.  Configuration配置

   - FastJsonHttpMessageConverterConfiguration：FastJson HttpMessageConverter实现
   - KftWebMvcConfigurer：静态资源路径扩展
   - SwaggerConfiguration：Swagger2配置

3. 公共Controller

   - BaseController：框架Controller基类
   - GlobalController：全局控制器
   - DefaultErrorView：错误页面的默认跳转
4. 工具类
   - HttpSessionHolder：非Controller中获取当前session的工具类
   - HttpKit：Http请求工具类
   - WafKit： web防火墙工具类

5. 公共Bean
   - SuccessTip： 返回给前台的成功提示
   - ErrorTip：返回给前台的错误提示
   - Tip：返回给前台的提示