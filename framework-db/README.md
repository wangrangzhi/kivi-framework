# 工程framework-db

## 1、概述

​	framework-db是kivi框架的的数据库集成模块项目，在pom文中包含了Spring Boot使用数据库的相关依赖jar包。
本项目集成了druid、mybatis、tk.mybatis、pagehelper等组建，并封装了mapper扫描、事务配置、动态数据源、框架错误码实现类以及mybatis-generator文件的编写的使用。

## 2、主要功能列表

1. Mapper扫描注解

   @KtfMapperScan：封装了mapper的扫描路径。

2. 接口

   - IDao：Dao操作接口。
   - IDaoEx：继承于IDao接口，扩展了功能
   - MyMapper<T>：所有mapper接口的继承接口，tk.mybatis工具用过这个接口实现对Mapper的管理。

3. Configuration配置

   TransactionConfiguration：声明式事务定义及配置

4. 公共Bean

   - PageInfoKtf<T>：分页查询的分页信息Bean