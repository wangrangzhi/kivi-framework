/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50536
Source Host           : localhost:3306
Source Database       : ktf

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2018-03-05 17:39:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ktf_dept
-- ----------------------------
DROP TABLE IF EXISTS `ktf_dept`;
CREATE TABLE `ktf_dept` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(11) unsigned DEFAULT NULL COMMENT '父部门id',
  `pids` varchar(255) DEFAULT NULL COMMENT '父级ids',
  `simplename` varchar(45) DEFAULT NULL COMMENT '简称',
  `fullname` varchar(255) DEFAULT NULL COMMENT '全称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '版本（乐观锁保留字段）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of ktf_dept
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_dict
-- ----------------------------
DROP TABLE IF EXISTS `ktf_dict`;
CREATE TABLE `ktf_dict` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '排序',
  `pid` bigint(11) unsigned DEFAULT NULL COMMENT '父级字典',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of ktf_dict
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_error_code
-- ----------------------------
DROP TABLE IF EXISTS `ktf_error_code`;
CREATE TABLE `ktf_error_code` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `err_code` varchar(8) NOT NULL COMMENT '错误代码',
  `err_alias` varchar(32) DEFAULT NULL COMMENT '错误代码英文别名',
  `err_desc` varchar(64) NOT NULL COMMENT '错误代码中文说明',
  `err_tip` varchar(64) DEFAULT NULL COMMENT '错误代码友好提示',
  `err_group` varchar(2) NOT NULL COMMENT '错误代码分组',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_error_code` (`err_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ktf_error_code
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ktf_login_log`;
CREATE TABLE `ktf_login_log` (
  `ID` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `LOG_NAME` varchar(64) NOT NULL COMMENT '日志名称',
  `USER_ID` varchar(64) NOT NULL COMMENT '登录用户ID',
  `SUCCEED` varchar(8) NOT NULL COMMENT '是否执行成功',
  `MESSAGE` varchar(255) DEFAULT NULL COMMENT '描述消息',
  `IP` varchar(64) DEFAULT NULL COMMENT '登录ip',
  `REGION_INFO` varchar(128) DEFAULT NULL COMMENT '登录地区信息，数据格式：country|city',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_UPDATE` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录日志';

-- ----------------------------
-- Records of ktf_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_notice
-- ----------------------------
DROP TABLE IF EXISTS `ktf_notice`;
CREATE TABLE `ktf_notice` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `content` text COMMENT '内容',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  `creater` int(11) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知表';

-- ----------------------------
-- Records of ktf_notice
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `ktf_operation_log`;
CREATE TABLE `ktf_operation_log` (
  `ID` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `LOG_TYPE` varchar(16) DEFAULT NULL COMMENT '日志类型',
  `LOG_NAME` varchar(32) DEFAULT NULL COMMENT '日志名称',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户id',
  `CLASS_NAME` varchar(64) DEFAULT NULL COMMENT '类名称',
  `METHOD` varchar(64) DEFAULT NULL COMMENT '方法名称',
  `SUCCEED` varchar(8) DEFAULT NULL COMMENT '是否成功',
  `MESSAGE` varchar(255) DEFAULT NULL COMMENT '备注',
  `IP` varchar(64) DEFAULT NULL COMMENT '请求IP',
  `gmt_CREATE` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_UPDATE` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of ktf_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_resource
-- ----------------------------
DROP TABLE IF EXISTS `ktf_resource`;
CREATE TABLE `ktf_resource` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `is_menu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `is_open` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=198 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of ktf_resource
-- ----------------------------
INSERT INTO `ktf_resource` VALUES ('168', 'system', '0', '[0],', '系统管理', 'fa-user', '', '3', '1', '1', null, '1', '1', null, null);
INSERT INTO `ktf_resource` VALUES ('169', 'mgr', 'system', '[0],[system],', '用户管理', '', '/mgr', '1', '2', '1', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('170', 'mgr_add', 'mgr', '[0],[system],[mgr],', '添加用户', null, '/mgr/add', '1', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('171', 'mgr_edit', 'mgr', '[0],[system],[mgr],', '修改用户', null, '/mgr/edit', '2', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('172', 'mgr_delete', 'mgr', '[0],[system],[mgr],', '删除用户', null, '/mgr/delete', '3', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('173', 'mgr_reset', 'mgr', '[0],[system],[mgr],', '重置密码', null, '/mgr/reset', '4', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('174', 'mgr_freeze', 'mgr', '[0],[system],[mgr],', '冻结用户', null, '/mgr/freeze', '5', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('175', 'mgr_unfreeze', 'mgr', '[0],[system],[mgr],', '解除冻结用户', null, '/mgr/unfreeze', '6', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('176', 'mgr_setRole', 'mgr', '[0],[system],[mgr],', '分配角色', null, '/mgr/setRole', '7', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('177', 'role', 'system', '[0],[system],', '角色管理', null, '/role', '2', '2', '1', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('178', 'role_add', 'role', '[0],[system],[role],', '添加角色', null, '/role/add', '1', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('179', 'role_edit', 'role', '[0],[system],[role],', '修改角色', null, '/role/edit', '2', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('180', 'role_remove', 'role', '[0],[system],[role],', '删除角色', null, '/role/remove', '3', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('181', 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', null, '/role/setAuthority', '4', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('182', 'menu', 'system', '[0],[system],', '菜单管理', null, '/menu', '4', '2', '1', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('183', 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', null, '/menu/add', '1', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('184', 'menu_edit', 'menu', '[0],[system],[menu],', '修改菜单', null, '/menu/edit', '2', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('185', 'menu_remove', 'menu', '[0],[system],[menu],', '删除菜单', null, '/menu/remove', '3', '3', '0', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('186', 'log', 'system', '[0],[system],', '业务日志', null, '/log', '6', '2', '1', null, '1', '0', null, null);
INSERT INTO `ktf_resource` VALUES ('187', 'druid', 'system', '[0],[system],', '监控管理', null, '/druid', '7', '2', '1', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('188', 'dept', 'system', '[0],[system],', '部门管理', null, '/dept', '3', '2', '1', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('189', 'dict', 'system', '[0],[system],', '字典管理', null, '/dict', '4', '2', '1', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('190', 'loginLog', 'system', '[0],[system],', '登录日志', null, '/loginLog', '6', '2', '1', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('191', 'log_clean', 'log', '[0],[system],[log],', '清空日志', null, '/log/delLog', '3', '3', '0', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('192', 'dept_add', 'dept', '[0],[system],[dept],', '添加部门', null, '/dept/add', '1', '3', '0', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('193', 'dept_update', 'dept', '[0],[system],[dept],', '修改部门', null, '/dept/update', '1', '3', '0', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('194', 'dept_delete', 'dept', '[0],[system],[dept],', '删除部门', null, '/dept/delete', '1', '3', '0', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('195', 'dict_add', 'dict', '[0],[system],[dict],', '添加字典', null, '/dict/add', '1', '3', '0', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('196', 'dict_update', 'dict', '[0],[system],[dict],', '修改字典', null, '/dict/update', '1', '3', '0', null, '1', null, null, null);
INSERT INTO `ktf_resource` VALUES ('197', 'dict_delete', 'dict', '[0],[system],[dict],', '删除字典', null, '/dict/delete', '1', '3', '0', null, '1', null, null, null);

-- ----------------------------
-- Table structure for ktf_role
-- ----------------------------
DROP TABLE IF EXISTS `ktf_role`;
CREATE TABLE `ktf_role` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(11) DEFAULT NULL COMMENT '序号',
  `pid` int(11) DEFAULT NULL COMMENT '父角色id',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门名称',
  `tips` varchar(255) DEFAULT NULL COMMENT '提示',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段(暂时没用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of ktf_role
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `ktf_role_resource`;
CREATE TABLE `ktf_role_resource` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(11) unsigned DEFAULT NULL COMMENT '角色id',
  `resource_id` bigint(11) unsigned DEFAULT NULL COMMENT '菜单id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of ktf_role_resource
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_seq_mutex
-- ----------------------------
DROP TABLE IF EXISTS `ktf_seq_mutex`;
CREATE TABLE `ktf_seq_mutex` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(32) NOT NULL COMMENT '业务名称',
  `count` bigint(20) NOT NULL COMMENT 'ID值',
  `max_count` bigint(20) DEFAULT NULL COMMENT '最大值',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='ID生成表';

-- ----------------------------
-- Records of ktf_seq_mutex
-- ----------------------------
INSERT INTO `ktf_seq_mutex` VALUES ('1', 'SEQ_KTF_COMMON', '1', '1000000', null);

-- ----------------------------
-- Table structure for ktf_user
-- ----------------------------
DROP TABLE IF EXISTS `ktf_user`;
CREATE TABLE `ktf_user` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `account` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(45) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',
  `name` varchar(45) DEFAULT NULL COMMENT '名字',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sex` int(11) DEFAULT NULL COMMENT '性别（1：男 2：女）',
  `email` varchar(45) DEFAULT NULL COMMENT '电子邮件',
  `phone` varchar(45) DEFAULT NULL COMMENT '电话',
  `role_id` varchar(255) DEFAULT NULL COMMENT '角色id',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
  `status` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '保留字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of ktf_user
-- ----------------------------

-- ----------------------------
-- Table structure for ktf_user_role
-- ----------------------------
DROP TABLE IF EXISTS `ktf_user_role`;
CREATE TABLE `ktf_user_role` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(11) unsigned DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(11) unsigned DEFAULT NULL COMMENT '角色id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

-- ----------------------------
-- Records of ktf_user_role
-- ----------------------------

-- ----------------------------
-- Function structure for seq
-- ----------------------------
DROP FUNCTION IF EXISTS `seq`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `seq`(`seq_name` varchar(64)) RETURNS int(11)
BEGIN
  UPDATE ktf_seq_mutex SET count=last_insert_id(mod((count+1),max_count)) WHERE name=seq_name; 
  RETURN last_insert_id();
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for seq_combo
-- ----------------------------
DROP FUNCTION IF EXISTS `seq_combo`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `seq_combo`(`seq_name` varchar(64),`seq_len` int,`seq_prefix` varchar(4)) RETURNS varchar(64) CHARSET utf8
BEGIN
	UPDATE ktf_seq_mutex SET count=last_insert_id(mod((count+1),max_count)) WHERE name=seq_name;  
  RETURN CONCAT(seq_prefix,date_format(current_timestamp,'%Y%m%d%H%i%s'),LPAD(last_insert_id(),seq_len,'0'));
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for seq_str
-- ----------------------------
DROP FUNCTION IF EXISTS `seq_str`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `seq_str`(`seq_name` varchar(64),`seq_len` int) RETURNS varchar(64) CHARSET utf8
BEGIN
  UPDATE ktf_seq_mutex SET count=last_insert_id(mod((count+1),max_count)) WHERE name=seq_name;  
  RETURN LPAD(last_insert_id(),seq_len,'0');
END
;;
DELIMITER ;


-- ----------------------------
-- Table structure for ktf_name_service
-- ----------------------------
DROP TABLE IF EXISTS `ktf_name_service`;
CREATE TABLE `ktf_name_service` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sid` varchar(64) DEFAULT NULL COMMENT 'service唯一标签',
  `name` varchar(255) DEFAULT NULL COMMENT 'service名字',
  `host` varchar(32) DEFAULT NULL COMMENT '主机IP',
  `port` int(255) unsigned DEFAULT NULL COMMENT '端口',
  `uri` varchar(255) DEFAULT NULL COMMENT '服务地址',
  `biz_type` varchar(32) DEFAULT NULL COMMENT '业务类型',
  `status` varchar(2) DEFAULT NULL COMMENT '状态，00：离线 01：在线',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_update` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
