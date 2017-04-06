/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.6.24 : Database - sec_retrieval
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`sec_retrieval` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `sec_retrieval`;

/*Table structure for table `sec_acl` */

DROP TABLE IF EXISTS `sec_acl`;

CREATE TABLE `sec_acl` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单ID',
  `code` varchar(100) DEFAULT NULL COMMENT '编码',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `url_pattern` varchar(100) DEFAULT NULL COMMENT '路径',
  `order_num` int(11) DEFAULT '1' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=283 DEFAULT CHARSET=utf8;

/*Data for the table `sec_acl` */

/*Table structure for table `sec_menu` */

DROP TABLE IF EXISTS `sec_menu`;

CREATE TABLE `sec_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级id',
  `name` varchar(100) NOT NULL COMMENT '菜单名称',
  `location` varchar(200) DEFAULT NULL COMMENT '菜单路径',
  `position` varchar(20) DEFAULT NULL COMMENT '菜单位置',
  `target` varchar(100) DEFAULT NULL COMMENT '菜单目标',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `order_num` varchar(20) DEFAULT '1' COMMENT '菜单排序',
  `display` tinyint(1) DEFAULT NULL COMMENT '是否显示',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=416 DEFAULT CHARSET=utf8;

/*Data for the table `sec_menu` */

insert  into `sec_menu`(`id`,`parent_id`,`name`,`location`,`position`,`target`,`icon`,`order_num`,`display`,`create_time`,`modify_time`) values (6,NULL,'系统设置','#','0',NULL,'icon-cog','6',1,NULL,'2015-04-09 13:52:50'),(7,6,'用户管理','/user/list.do','0',NULL,'','1',1,NULL,'2014-11-07 17:39:33'),(8,6,'角色管理','/role/list.do','0',NULL,'','2',1,'2014-11-05 19:46:17',NULL),(9,6,'菜单管理','/menu/list.do','0',NULL,'','3',1,'2014-11-05 19:46:17',NULL),(10,6,'日志管理','/syslog/list.do','0',NULL,'','4',1,'2015-07-08 23:00:45',NULL);

/*Table structure for table `sec_role` */

DROP TABLE IF EXISTS `sec_role`;

CREATE TABLE `sec_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `code` varchar(100) NOT NULL COMMENT '角色代码，以ROLE_开头',
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;

/*Data for the table `sec_role` */

insert  into `sec_role`(`id`,`code`,`name`,`enabled`,`remark`,`create_time`,`modify_time`) values (1,'ROLE_ADMIN','管理员',1,NULL,NULL,'2016-04-15 14:32:21');

/*Table structure for table `sec_role_acl` */

DROP TABLE IF EXISTS `sec_role_acl`;

CREATE TABLE `sec_role_acl` (
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `acl_id` int(11) DEFAULT NULL COMMENT 'acl id',
  KEY `fk_rc_role_id` (`role_id`),
  KEY `fk_rc_acl_id` (`acl_id`),
  CONSTRAINT `fk_rc_acl_id` FOREIGN KEY (`acl_id`) REFERENCES `sec_acl` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_rc_role_id` FOREIGN KEY (`role_id`) REFERENCES `sec_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sec_role_acl` */

/*Table structure for table `sec_role_menu` */

DROP TABLE IF EXISTS `sec_role_menu`;

CREATE TABLE `sec_role_menu` (
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  KEY `fk_rm_role_id` (`role_id`),
  KEY `fk_rm_menu_id` (`menu_id`),
  CONSTRAINT `fk_rm_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `sec_menu` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_rm_role_id` FOREIGN KEY (`role_id`) REFERENCES `sec_role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sec_role_menu` */

insert  into `sec_role_menu`(`role_id`,`menu_id`) values (1,6),(1,7),(1,8),(1,9),(1,10);

/*Table structure for table `sec_user` */

DROP TABLE IF EXISTS `sec_user`;

CREATE TABLE `sec_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  `account_non_expired` tinyint(1) DEFAULT NULL COMMENT '用户是否过期',
  `account_non_locked` tinyint(1) DEFAULT NULL COMMENT '用户是否锁定',
  `credentials_non_expired` tinyint(1) DEFAULT NULL COMMENT '证书是否过期',
  `realname` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `cert_id` varchar(100) DEFAULT NULL COMMENT '证书id',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `phone` varchar(100) DEFAULT NULL COMMENT '电话号码',
  `qq` varchar(100) DEFAULT NULL COMMENT 'qq',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `aduitPass` varchar(100) DEFAULT NULL COMMENT '审批密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8;

/*Data for the table `sec_user` */

insert  into `sec_user`(`id`,`username`,`password`,`enabled`,`account_non_expired`,`account_non_locked`,`credentials_non_expired`,`realname`,`address`,`cert_id`,`email`,`mobile`,`phone`,`qq`,`create_time`,`modify_time`,`last_login_time`,`aduitPass`) values (1,'admin','e422ccc0cb8741554bad9f967614c760c6bdc694e740315f111fc47ad839447fd122158785bb72f3',1,1,1,1,'admin','密码是6个0',NULL,'zhouying@xyb100.com','13590234711',NULL,NULL,'2014-11-05 20:03:08','2016-04-17 20:16:39','2016-04-17 20:16:39',NULL),(171,'lixiang','2259dc6d27e98c18897c4d765e88b6760e977efd97bfdda069262d5b476651cf80cb8f6003c89619',1,1,1,1,'李翔',NULL,NULL,'','',NULL,NULL,'2015-07-07 10:19:48','2016-04-17 22:44:38','2016-04-17 22:44:38',NULL);

/*Table structure for table `sec_user_channel` */

DROP TABLE IF EXISTS `sec_user_channel`;

CREATE TABLE `sec_user_channel` (
  `userId` int(11) NOT NULL DEFAULT '0',
  `channelId` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`,`channelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sec_user_channel` */

/*Table structure for table `sec_user_role` */

DROP TABLE IF EXISTS `sec_user_role`;

CREATE TABLE `sec_user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  KEY `fk_ur_user_id` (`user_id`),
  KEY `fk_ur_role_id` (`role_id`),
  CONSTRAINT `fk_ur_role_id` FOREIGN KEY (`role_id`) REFERENCES `sec_role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ur_user_id` FOREIGN KEY (`user_id`) REFERENCES `sec_user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sec_user_role` */

insert  into `sec_user_role`(`user_id`,`role_id`) values (171,1),(1,1);

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `operate_type` int(2) DEFAULT '0',
  `operator` int(20) DEFAULT NULL,
  `operate_content` varchar(150) DEFAULT NULL,
  `operate_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `sys_log` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
