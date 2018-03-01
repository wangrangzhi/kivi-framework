# 工程framework-common

## 1、概述

​	本项目是kivi框架的公共基础项目，在本项目中对公共的常量、枚举、DTO、Component、Annotation、常用工具类等进行了定义和封装，对于本框架需要用到的配置文件属性进行了规范描述。提供了错误码的定义、加载、使用和更新的组建。

## 2、主要功能列表

1. 框架注解

   @EnableKiviFramework：封装了属性文件和Component的扫描路径。

2. Component组件

   - ApplicationKit：主要用于获取SpringBoot应用的相关信息。
   - ErrorKit：系统错误码加载和获取。
   - SpringContextHolder：Spring bean获取帮组组件。

3. 自定义属性文件

   KiviProperties：框架自定义配置文件属性的定义

4. 公共常量

   - 常量、错误码、枚举、DTO等

5. 常用工具

   - Excel操作工具类
   - Des加密/解密
   - Bean工具类
   - 类工具类
   - 集合相关工具类，包括数组
   - 时间工具类
   - 十六进制类
   - Object通用的函数
   - 分页工具类
   - 资源文件相关的操作类
   - 字符串工具类