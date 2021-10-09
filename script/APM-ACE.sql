-- MySQL dump 10.13  Distrib 5.5.60, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: cat
-- ------------------------------------------------------
-- Server version	5.5.60-0+deb8u1-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `domain` varchar(128) NOT NULL COMMENT '告警项目',
  `alert_time` datetime NOT NULL COMMENT '告警时间',
  `category` varchar(64) NOT NULL COMMENT '告警分类:network/business/system/exception -alert',
  `type` varchar(64) NOT NULL COMMENT '告警类型:error/warning',
  `content` longtext NOT NULL COMMENT '告警内容',
  `metric` varchar(128) NOT NULL COMMENT '告警指标',
  `creation_date` datetime NOT NULL COMMENT '数据插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2845720 DEFAULT CHARSET=utf8 COMMENT='存储告警信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alert_summary`
--

DROP TABLE IF EXISTS `alert_summary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `domain` varchar(128) NOT NULL COMMENT '告警项目',
  `alert_time` datetime NOT NULL COMMENT '告警时间',
  `content` longtext NOT NULL COMMENT '统一告警内容',
  `creation_date` datetime NOT NULL COMMENT '数据插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统一告警信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alteration`
--

DROP TABLE IF EXISTS `alteration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alteration` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `type` varchar(64) NOT NULL COMMENT '分类',
  `title` varchar(128) NOT NULL COMMENT '变更标题',
  `domain` varchar(128) NOT NULL COMMENT '变更项目',
  `hostname` varchar(128) NOT NULL COMMENT '变更机器名',
  `ip` varchar(128) DEFAULT NULL COMMENT '变更机器IP',
  `date` datetime NOT NULL COMMENT '变更时间',
  `user` varchar(45) NOT NULL COMMENT '变更用户',
  `alt_group` varchar(45) DEFAULT NULL COMMENT '变更组别',
  `content` longtext NOT NULL COMMENT '变更内容',
  `url` varchar(200) DEFAULT NULL COMMENT '变更链接',
  `status` tinyint(4) DEFAULT '0' COMMENT '变更状态',
  `creation_date` datetime NOT NULL COMMENT '数据库创建时间',
  PRIMARY KEY (`id`),
  KEY `ind_date_domain_host` (`date`,`domain`,`hostname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='变更表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_command_data_1`
--

DROP TABLE IF EXISTS `app_command_data_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_command_data_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `period` date NOT NULL COMMENT '时间',
  `minute_order` smallint(6) NOT NULL COMMENT '分钟',
  `city` smallint(6) NOT NULL COMMENT '城市',
  `operator` tinyint(4) NOT NULL COMMENT '运营商',
  `network` tinyint(4) NOT NULL COMMENT '网络类型',
  `app_version` int(11) NOT NULL COMMENT '版本',
  `connect_type` tinyint(4) NOT NULL COMMENT '访问类型，是否长连接',
  `code` smallint(6) NOT NULL COMMENT '返回码',
  `platform` tinyint(4) NOT NULL COMMENT '平台',
  `access_number` bigint(20) NOT NULL COMMENT '访问量',
  `response_sum_time` bigint(20) NOT NULL COMMENT '响应时间大小',
  `request_package` bigint(20) NOT NULL COMMENT '请求包大小',
  `response_package` bigint(20) NOT NULL COMMENT '响应包大小',
  `status` smallint(6) NOT NULL COMMENT '数据状态',
  `creation_date` datetime NOT NULL COMMENT '数据插入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_condition` (`period`,`minute_order`,`city`,`operator`,`network`,`app_version`,`connect_type`,`code`,`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app基本数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `app_speed_data_1`
--

DROP TABLE IF EXISTS `app_speed_data_1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_speed_data_1` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `period` date NOT NULL COMMENT '时间',
  `minute_order` smallint(6) NOT NULL COMMENT '分钟',
  `city` smallint(6) NOT NULL COMMENT '城市',
  `operator` tinyint(4) NOT NULL COMMENT '运营商',
  `network` tinyint(4) NOT NULL COMMENT '网络类型',
  `app_version` int(11) NOT NULL COMMENT '版本',
  `platform` tinyint(4) NOT NULL COMMENT '平台',
  `access_number` bigint(20) NOT NULL COMMENT '访问量',
  `slow_access_number` bigint(20) NOT NULL COMMENT '慢用户访问量',
  `response_sum_time` bigint(20) NOT NULL COMMENT '响应时间大小',
  `slow_response_sum_time` bigint(20) NOT NULL COMMENT '慢用户响应时间大小',
  `status` smallint(6) NOT NULL COMMENT '数据状态',
  `creation_date` datetime NOT NULL COMMENT '数据插入时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IX_condition` (`period`,`minute_order`,`city`,`operator`,`network`,`app_version`,`platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app测速数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `baseline`
--

DROP TABLE IF EXISTS `baseline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `baseline` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `report_name` varchar(100) DEFAULT NULL,
  `index_key` varchar(100) DEFAULT NULL,
  `report_period` datetime NOT NULL DEFAULT '1990-10-31 11:00:00',
  `data` blob,
  `creation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`,`report_period`),
  KEY `period_name_key` (`report_period`,`report_name`,`index_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1376785 DEFAULT CHARSET=utf8
/*!50100 PARTITION BY RANGE (month(report_period))
(PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
 PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `businessReport`
--

DROP TABLE IF EXISTS `businessReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `businessReport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '报表类型 报表数据格式, 1/Binary, 2/xml , 3/json',
  `name` varchar(20) NOT NULL COMMENT '报表名称',
  `ip` varchar(50) NOT NULL COMMENT '报表来自于哪台机器',
  `productLine` varchar(50) NOT NULL COMMENT '指标来源于哪个产品组',
  `period` datetime NOT NULL COMMENT '报表时间段',
  `content` longblob COMMENT '用于存放报表的具体内容',
  `creation_date` datetime NOT NULL COMMENT '报表创建时间',
  PRIMARY KEY (`id`,`period`),
  KEY `IX_Period_productLine_name` (`period`,`productLine`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=215103 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='用于存放业务监控实时报表信息，处理之后的结果'
/*!50100 PARTITION BY RANGE (month(period))
(PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
 PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '配置名称',
  `content` longtext COMMENT '配置的具体内容',
  `creation_date` datetime NOT NULL COMMENT '配置创建时间',
  `modify_date` datetime NOT NULL COMMENT '配置修改时间',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='用于存储系统的全局配置信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `config_modification`
--

DROP TABLE IF EXISTS `config_modification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config_modification` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `user_name` varchar(64) NOT NULL COMMENT '用户名',
  `account_name` varchar(64) NOT NULL COMMENT '账户名',
  `action_name` varchar(64) NOT NULL COMMENT 'action名',
  `argument` longtext COMMENT '参数内容',
  `date` datetime NOT NULL COMMENT '修改时间',
  `creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2680 DEFAULT CHARSET=utf8 COMMENT='配置修改记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `daily_report_content`
--

DROP TABLE IF EXISTS `daily_report_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_report_content` (
  `report_id` int(11) NOT NULL COMMENT '报表ID',
  `content` longblob NOT NULL COMMENT '二进制报表内容',
  `creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`report_id`,`creation_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='天报表二进制内容'
/*!50100 PARTITION BY RANGE (month(creation_date))
(PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
 PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dailygraph`
--

DROP TABLE IF EXISTS `dailygraph`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dailygraph` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '报表名称',
  `ip` varchar(50) DEFAULT NULL COMMENT '报表来自于哪台cat-client机器ip, 空串表示合并同domain所有ip',
  `domain` varchar(50) NOT NULL COMMENT '报表处理的Domain信息',
  `period` datetime NOT NULL COMMENT '报表时间段',
  `type` tinyint(4) NOT NULL COMMENT '报表数据格式, 1/xml, 2/json, 3/csv, 默认3',
  `detail_content` mediumtext NOT NULL COMMENT '详细绘图内容',
  `summary_content` mediumtext NOT NULL COMMENT '概要绘图内容',
  `creation_date` datetime NOT NULL COMMENT '报表创建时间',
  PRIMARY KEY (`id`,`period`),
  UNIQUE KEY `dailygraph_period_ip_domain_name` (`period`,`ip`,`domain`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2108752 DEFAULT CHARSET=utf8 COMMENT='用于月报的画图曲线'
/*!50100 PARTITION BY RANGE (month(period))
(PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
 PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dailyreport`
--

DROP TABLE IF EXISTS `dailyreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dailyreport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '报表名称, transaction, problem...',
  `ip` varchar(50) NOT NULL COMMENT '报表来自于哪台cat-consumer机器',
  `domain` varchar(50) NOT NULL COMMENT '报表处理的Domain信息',
  `period` datetime NOT NULL COMMENT '报表时间段',
  `type` tinyint(4) NOT NULL COMMENT '报表数据格式, 1/xml, 2/json, 默认1',
  `creation_date` datetime NOT NULL COMMENT '报表创建时间',
  PRIMARY KEY (`id`,`period`),
  UNIQUE KEY `period` (`period`,`domain`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=257633 DEFAULT CHARSET=utf8 COMMENT='天报表'
/*!50100 PARTITION BY RANGE (month(period))
(PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
 PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `graph`
--

DROP TABLE IF EXISTS `graph`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `graph` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '报表名称',
  `ip` varchar(50) DEFAULT NULL COMMENT '报表来自于哪台cat-client机器ip, NULL表示合并同domain所有ip',
  `domain` varchar(50) NOT NULL COMMENT '报表处理的Domain信息',
  `period` datetime NOT NULL COMMENT '报表时间段',
  `type` tinyint(4) NOT NULL COMMENT '报表数据格式, 1/xml, 2/json, 3/csv, 默认3',
  `detail_content` mediumtext NOT NULL COMMENT '详细绘图内容',
  `summary_content` mediumtext NOT NULL COMMENT '概要绘图内容',
  `creation_date` datetime NOT NULL COMMENT '报表创建时间',
  PRIMARY KEY (`id`,`period`),
  UNIQUE KEY `graph_period_ip_domain_name` (`period`,`ip`,`domain`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=20418862 DEFAULT CHARSET=utf8 COMMENT='小时图表曲线'
/*!50100 PARTITION BY RANGE (month(period))
(PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
 PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hostinfo`
--

DROP TABLE IF EXISTS `hostinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hostinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) NOT NULL COMMENT '部署机器IP',
  `domain` varchar(200) NOT NULL COMMENT '部署机器对应的项目名',
  `hostname` varchar(200) DEFAULT NULL COMMENT '机器域名',
  `creation_date` datetime NOT NULL,
  `last_modified_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ip_index` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=42298 DEFAULT CHARSET=utf8 COMMENT='IP和项目名的对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `monthly_report_content`
--

DROP TABLE IF EXISTS `monthly_report_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthly_report_content` (
  `report_id` int(11) NOT NULL COMMENT '报表ID',
  `content` longblob NOT NULL COMMENT '二进制报表内容',
  `creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`report_id`,`creation_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='月报表二进制内容'
/*!50100 PARTITION BY RANGE (month(creation_date))
(PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
 PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `monthreport`
--

DROP TABLE IF EXISTS `monthreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `monthreport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '报表名称, transaction, problem...',
  `ip` varchar(50) NOT NULL COMMENT '报表来自于哪台cat-consumer机器',
  `domain` varchar(50) NOT NULL COMMENT '报表处理的Domain信息',
  `period` datetime NOT NULL COMMENT '报表时间段',
  `type` tinyint(4) NOT NULL COMMENT '报表数据格式, 1/xml, 2/json, 默认1',
  `creation_date` datetime NOT NULL COMMENT '报表创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `period` (`period`,`domain`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=197323 DEFAULT CHARSET=utf8 COMMENT='月报表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `user` varchar(128) NOT NULL COMMENT '用户名',
  `module` varchar(128) NOT NULL COMMENT '模块',
  `operation` varchar(128) NOT NULL COMMENT '操作',
  `time` datetime NOT NULL COMMENT '修改时间',
  `content` longtext NOT NULL COMMENT '修改内容',
  `creation_date` datetime NOT NULL COMMENT '数据插入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户操作日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `overload`
--

DROP TABLE IF EXISTS `overload`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `overload` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `report_id` int(11) NOT NULL COMMENT '报告id',
  `report_type` tinyint(4) NOT NULL COMMENT '报告类型 1:hourly 2:daily 3:weekly 4:monthly',
  `report_size` double NOT NULL COMMENT '报告大小 单位MB',
  `period` datetime NOT NULL COMMENT '报表时间',
  `creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `period` (`period`)
) ENGINE=InnoDB AUTO_INCREMENT=77250 DEFAULT CHARSET=utf8 COMMENT='过大容量表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(200) NOT NULL COMMENT '项目名称',
  `cmdb_domain` varchar(200) DEFAULT NULL COMMENT 'cmdb项目名称',
  `level` int(5) DEFAULT NULL COMMENT '项目级别',
  `bu` varchar(50) DEFAULT NULL COMMENT 'CMDB事业部',
  `cmdb_productline` varchar(50) DEFAULT NULL COMMENT 'CMDB产品线',
  `owner` varchar(300) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL COMMENT '项目组邮件',
  `phone` varchar(200) DEFAULT NULL COMMENT '联系电话',
  `creation_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `domain` (`domain`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COMMENT='项目基本信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '报表类型, 1/xml, 9/binary 默认1',
  `name` varchar(20) NOT NULL COMMENT '报表名称',
  `ip` varchar(50) DEFAULT NULL COMMENT '报表来自于哪台机器',
  `domain` varchar(50) NOT NULL COMMENT '报表项目',
  `period` datetime NOT NULL COMMENT '报表时间段',
  `creation_date` datetime NOT NULL COMMENT '报表创建时间',
  PRIMARY KEY (`id`),
  KEY `IX_Domain_Name_Period` (`domain`,`name`,`period`),
  KEY `IX_Name_Period` (`name`,`period`),
  KEY `IX_Period` (`period`)
) ENGINE=InnoDB AUTO_INCREMENT=7343320 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='用于存放实时报表信息，处理之后的结果';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `report_content`
--

DROP TABLE IF EXISTS `report_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report_content` (
  `report_id` int(11) NOT NULL COMMENT '报表ID',
  `content` longblob NOT NULL COMMENT '二进制报表内容',
  `creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`report_id`,`creation_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='小时报表二进制内容'
/*!50100 PARTITION BY RANGE (month(creation_date))
(PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
 PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
 PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
 PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
 PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
 PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
 PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
 PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
 PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
 PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
 PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
 PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
 PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `producer` varchar(20) NOT NULL COMMENT '任务创建者ip',
  `consumer` varchar(20) DEFAULT NULL COMMENT '任务执行者ip',
  `failure_count` tinyint(4) NOT NULL COMMENT '任务失败次数',
  `report_name` varchar(20) NOT NULL COMMENT '报表名称, transaction, problem...',
  `report_domain` varchar(50) NOT NULL COMMENT '报表处理的Domain信息',
  `report_period` datetime NOT NULL COMMENT '报表时间',
  `status` tinyint(4) NOT NULL COMMENT '执行状态: 1/todo, 2/doing, 3/done 4/failed',
  `task_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0表示小时任务，1表示天任务',
  `creation_date` datetime NOT NULL COMMENT '任务创建时间',
  `start_date` datetime DEFAULT NULL COMMENT '开始时间, 这次执行开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间, 这次执行结束时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `task_period_domain_name_type` (`report_period`,`report_domain`,`report_name`,`task_type`)
) ENGINE=InnoDB AUTO_INCREMENT=15537787 DEFAULT CHARSET=utf8 COMMENT='后台任务';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `topologyGraph`
--

DROP TABLE IF EXISTS `topologyGraph`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topologyGraph` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) NOT NULL COMMENT '报表来自于哪台cat-client机器ip',
  `period` datetime NOT NULL COMMENT '报表时间段,精确到分钟',
  `type` tinyint(4) NOT NULL COMMENT '报表数据格式, 1/xml, 2/json, 3/binary',
  `content` longblob COMMENT '用于存放报表的具体内容',
  `creation_date` datetime NOT NULL COMMENT '报表创建时间',
  PRIMARY KEY (`id`),
  KEY `period` (`period`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于存储历史的拓扑图曲线';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_define_rule`
--

DROP TABLE IF EXISTS `user_define_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_define_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `content` text NOT NULL COMMENT '用户定义规则',
  `creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户定义规则表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `weekly_report_content`
--

DROP TABLE IF EXISTS `weekly_report_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weekly_report_content` (
  `report_id` int(11) NOT NULL COMMENT '报表ID',
  `content` longblob NOT NULL COMMENT '二进制报表内容',
  `creation_date` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED COMMENT='周报表二进制内容';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `logview_long_period_content`
--
DROP TABLE IF EXISTS `logview_long_period_content`;
CREATE TABLE `logview_long_period_content`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT,
    `domain`     varchar(256)    NOT NULL DEFAULT '',
    `message_id` varchar(256)             DEFAULT NULL,
    `content`    longblob        NOT NULL,
    `ctime`      datetime        NOT NULL,
    `mtime`      datetime                 DEFAULT NULL,
    PRIMARY KEY (`id`, `ctime`),
    UNIQUE KEY `unq_msg_ctime` (`message_id`, `ctime`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5879
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
    /*!50100 PARTITION BY RANGE (month(`ctime`))
    (PARTITION p1 VALUES LESS THAN (2) ENGINE = InnoDB,
    PARTITION p2 VALUES LESS THAN (3) ENGINE = InnoDB,
    PARTITION p3 VALUES LESS THAN (4) ENGINE = InnoDB,
    PARTITION p4 VALUES LESS THAN (5) ENGINE = InnoDB,
    PARTITION p5 VALUES LESS THAN (6) ENGINE = InnoDB,
    PARTITION p6 VALUES LESS THAN (7) ENGINE = InnoDB,
    PARTITION p7 VALUES LESS THAN (8) ENGINE = InnoDB,
    PARTITION p8 VALUES LESS THAN (9) ENGINE = InnoDB,
    PARTITION p9 VALUES LESS THAN (10) ENGINE = InnoDB,
    PARTITION p10 VALUES LESS THAN (11) ENGINE = InnoDB,
    PARTITION p11 VALUES LESS THAN (12) ENGINE = InnoDB,
    PARTITION p12 VALUES LESS THAN (13) ENGINE = InnoDB,
    PARTITION p_other VALUES LESS THAN MAXVALUE ENGINE = InnoDB) */;

--
-- Table structure for table `weeklyreport`
--

DROP TABLE IF EXISTS `weeklyreport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weeklyreport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '报表名称, transaction, problem...',
  `ip` varchar(50) NOT NULL COMMENT '报表来自于哪台cat-consumer机器',
  `domain` varchar(50) NOT NULL COMMENT '报表处理的Domain信息',
  `period` datetime NOT NULL COMMENT '报表时间段',
  `type` tinyint(4) NOT NULL COMMENT '报表数据格式, 1/xml, 2/json, 默认1',
  `creation_date` datetime NOT NULL COMMENT '报表创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `period` (`period`,`domain`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=223477 DEFAULT CHARSET=utf8 COMMENT='周报表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-29 19:40:28