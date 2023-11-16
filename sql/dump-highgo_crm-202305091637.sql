-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 192.168.110.186    Database: highgo_crm
-- ------------------------------------------------------
-- Server version	5.5.5-10.6.7-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gen_table`
--

DROP TABLE IF EXISTS `gen_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table`
--

LOCK TABLES `gen_table` WRITE;
/*!40000 ALTER TABLE `gen_table` DISABLE KEYS */;
INSERT INTO `gen_table` VALUES (2,'t_license_apply','license申请表',NULL,NULL,'LicenseApply','crud','com.highgo.crm','crm','licenseApply','license申请','eszhang','0','/','{\"parentMenuId\":2000}','admin','2023-04-26 03:40:58','','2023-04-26 03:44:09',NULL);
/*!40000 ALTER TABLE `gen_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gen_table_column`
--

DROP TABLE IF EXISTS `gen_table_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COMMENT='代码生成业务表字段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gen_table_column`
--

LOCK TABLES `gen_table_column` WRITE;
/*!40000 ALTER TABLE `gen_table_column` DISABLE KEYS */;
INSERT INTO `gen_table_column` VALUES (17,'2','id','主键','bigint(20)','Long','id','1','1',NULL,'1',NULL,NULL,NULL,'EQ','input','',1,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(18,'2','code','商机Code','varchar(25)','String','code','0','0','1','1','1','1','1','EQ','input','',2,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(19,'2','name','商机名称','varchar(100)','String','name','0','0','1','1','1','1','1','LIKE','input','',3,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(20,'2','cust_code','客户Code','bigint(20)','Long','custCode','0','0','0','1','1','1','0','LIKE','input','',4,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(21,'2','cust_name','客户名称','bigint(20)','Long','custName','0','0','1','1','1','1','1','LIKE','input','',5,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(22,'2','use_time','用户需求时间','date','Date','useTime','0','0',NULL,'1','1','1','0','EQ','datetime','',6,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(23,'2','purposes','用途','char(2)','String','purposes','0','0','1','1','1','1','1','EQ','select','crm_lic_purposes_type',7,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(24,'2','dept_id','商机所在部门','bigint(20)','Long','deptId','0','0',NULL,'1','1','1','1','EQ','input','',8,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(25,'2','owner_id','商机负责人员','bigint(20)','Long','ownerId','0','0',NULL,'1','1','1','1','EQ','input','',9,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(26,'2','shared_id','商机共享到人','varchar(100)','String','sharedId','0','0',NULL,'1','1','1','0','EQ','input','',10,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(27,'2','shared_dept_id','商机共享到部门','varchar(100)','String','sharedDeptId','0','0',NULL,'1','1','1','0','EQ','input','',11,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(28,'2','create_by','商机创建者','varchar(25)','String','createBy','0','0',NULL,'1',NULL,NULL,NULL,'EQ','input','',12,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(29,'2','create_time','商机创建时间','datetime','Date','createTime','0','0',NULL,'1',NULL,NULL,'1','BETWEEN','datetime','',13,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(30,'2','update_by','商机更新者','varchar(25)','String','updateBy','0','0',NULL,'1','1',NULL,NULL,'EQ','input','',14,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(31,'2','update_time','商机更新时间','datetime','Date','updateTime','0','0',NULL,'1','1',NULL,NULL,'EQ','datetime','',15,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09'),(32,'2','remark',NULL,'varchar(100)','String','remark','0','0',NULL,'1','1','1',NULL,'EQ','input','',16,'admin','2023-04-26 03:40:58','','2023-04-26 03:44:09');
/*!40000 ALTER TABLE `gen_table_column` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_application`
--

DROP TABLE IF EXISTS `t_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `name` varchar(50) NOT NULL COMMENT '应用名称',
  `version` varchar(50) DEFAULT NULL COMMENT '应用当前版本',
  `category` varchar(50) DEFAULT NULL COMMENT '应归属门类',
  `category_l1` varchar(10) DEFAULT NULL COMMENT '一级门类',
  `category_l2` varchar(10) DEFAULT NULL COMMENT '二级门类',
  `category_l3` varchar(10) DEFAULT NULL COMMENT '三级门类',
  `category_l4` varchar(10) DEFAULT NULL COMMENT '四级门类',
  `isv` bigint(20) DEFAULT NULL COMMENT '软件开发商id',
  `isv_name` varchar(100) DEFAULT NULL COMMENT '软件开发商名称',
  `isv_contact_id` bigint(20) DEFAULT NULL COMMENT 'isv联系人',
  `base_environ` varchar(55) DEFAULT NULL COMMENT '基础环境',
  `opeart_sys` varchar(15) DEFAULT NULL COMMENT '操作系统',
  `middle_soft` varchar(55) DEFAULT NULL COMMENT '中间件',
  `data_base` varchar(55) NOT NULL COMMENT '当前数据库',
  `db_selection_model` varchar(55) DEFAULT NULL COMMENT 'DB选型',
  `deploy_type` varchar(55) DEFAULT NULL COMMENT '部署类型',
  `deploy_level` varchar(55) DEFAULT NULL COMMENT 'DB部署级别',
  `sys_build_money` varchar(55) DEFAULT NULL COMMENT '建设资金来源',
  `source_id` varchar(20) DEFAULT NULL COMMENT '数据归属',
  `source_type` varchar(15) DEFAULT NULL COMMENT '数据归类',
  `dev_status` varchar(5) DEFAULT NULL COMMENT '应用开发进度',
  `db_adapt_status` varchar(5) DEFAULT NULL COMMENT 'HGDB适配进度',
  `remark` varchar(150) DEFAULT NULL COMMENT '描述',
  `owner_id` bigint(20) DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `create_by` varchar(60) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_by` varchar(60) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `t_application_name_IDX` (`name`) USING BTREE,
  KEY `t_application_source_type_IDX` (`source_type`,`source_id`) USING BTREE,
  KEY `t_application_isv_IDX` (`isv`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COMMENT='应用软件信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_application`
--

LOCK TABLES `t_application` WRITE;
/*!40000 ALTER TABLE `t_application` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_company`
--

DROP TABLE IF EXISTS `t_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `code` varchar(50) DEFAULT NULL COMMENT '记录编码',
  `company_name` varchar(50) CHARACTER SET utf8mb3 NOT NULL COMMENT '公司名称',
  `properties` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '公司性质',
  `industry` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '行业归属',
  `industry_category` varchar(50) DEFAULT NULL COMMENT '行业大类',
  `industry_subcategory` varchar(50) DEFAULT NULL COMMENT '行业小类',
  `legal` varchar(15) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '公司法人',
  `capital_reg` varchar(15) DEFAULT NULL COMMENT '注册资金',
  `capital_pay_type` varchar(15) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '缴纳方式',
  `source_id` varchar(20) DEFAULT NULL COMMENT '数据归属',
  `source_type` varchar(15) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '数据归类',
  `website` varchar(150) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '公司官网',
  `addr` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '公司地址',
  `addr_detail` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `zip_code` varchar(10) DEFAULT NULL COMMENT '邮编号',
  `business_scope` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '经营范围',
  `remark` varchar(200) CHARACTER SET utf8mb3 DEFAULT '' COMMENT '公司描述',
  `owner_id` bigint(20) DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  `create_id` bigint(20) DEFAULT NULL,
  `create_by` varchar(60) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_id` bigint(20) DEFAULT NULL,
  `update_by` varchar(60) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `t_company_un` (`code`),
  KEY `t_company_company_name_IDX` (`company_name`) USING BTREE,
  KEY `t_company_industry_IDX` (`industry`) USING BTREE,
  KEY `t_company_code_IDX` (`code`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='公司信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_company`
--

LOCK TABLES `t_company` WRITE;
/*!40000 ALTER TABLE `t_company` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_contact`
--

DROP TABLE IF EXISTS `t_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `name` varchar(50) CHARACTER SET utf8mb3 NOT NULL COMMENT '联系人名称',
  `sex` char(1) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '性别',
  `company` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '所属公司',
  `company_code` varchar(50) DEFAULT NULL COMMENT '所属公司Code',
  `role_in_oppo` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '在商机内角色',
  `role_in_company` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '公司内角色',
  `post_in_company` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '公司内职务',
  `dept_in_company` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '公司内部门',
  `pri_phone` varchar(11) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '手机(主)',
  `sec_phone` varchar(11) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '手机(副)',
  `pri_email` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '邮件(主)',
  `sec_email` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '邮件(副)',
  `addr` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '联系人地址',
  `category` varchar(20) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '联系人类型',
  `remark` varchar(200) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '描述',
  `source_type` varchar(15) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '数据归类',
  `source_id` varchar(20) DEFAULT NULL COMMENT '数据归属',
  `owner_id` bigint(20) DEFAULT NULL COMMENT '记录负责人',
  `owner_dept_id` bigint(20) DEFAULT NULL COMMENT '记录负责人部门',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建人Id',
  `create_by` varchar(60) DEFAULT NULL COMMENT '创建人名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL,
  `update_by` varchar(60) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `t_contact_name_IDX` (`name`) USING BTREE,
  KEY `t_contact_company_IDX` (`company`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='联系人表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_contact`
--

LOCK TABLES `t_contact` WRITE;
/*!40000 ALTER TABLE `t_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_license_apply`
--

DROP TABLE IF EXISTS `t_license_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_license_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(25) NOT NULL COMMENT '商机Code',
  `name` varchar(100) NOT NULL COMMENT '商机名称',
  `cust_code` varchar(50) DEFAULT NULL COMMENT '客户Code',
  `cust_name` varchar(100) DEFAULT NULL COMMENT '客户名称',
  `use_time` date DEFAULT NULL COMMENT '用户需求时间',
  `purposes` char(2) NOT NULL DEFAULT '' COMMENT '用途',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '商机所在部门',
  `owner_id` bigint(20) DEFAULT NULL COMMENT '商机负责人员',
  `shared_id` varchar(100) DEFAULT NULL COMMENT '商机共享到人',
  `shared_dept_id` varchar(100) DEFAULT NULL COMMENT '商机共享到部门',
  `create_by` varchar(25) DEFAULT NULL COMMENT '商机创建者',
  `create_time` datetime DEFAULT NULL COMMENT '商机创建时间',
  `update_by` varchar(25) DEFAULT NULL COMMENT '商机更新者',
  `update_time` datetime DEFAULT NULL COMMENT '商机更新时间',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_license_applay_un` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='license申请表 主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_license_apply`
--

LOCK TABLES `t_license_apply` WRITE;
/*!40000 ALTER TABLE `t_license_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_license_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_mail_config`
--

DROP TABLE IF EXISTS `t_mail_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_mail_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `server` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '邮件发送服务器',
  `port` int(5) DEFAULT NULL COMMENT '服务器端口',
  `secretKey` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT '邮箱账户',
  `accessKey` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '账户密码',
  `nick_name` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `status` char(2) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否启用',
  `create_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='系统发邮件账户配置,多个生效时取最新的';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_mail_config`
--

LOCK TABLES `t_mail_config` WRITE;
/*!40000 ALTER TABLE `t_mail_config` DISABLE KEYS */;
INSERT INTO `t_mail_config` VALUES (1,'192.168.110.254',465,'crm@highgo.com','highgo@1234','crm_Admin','0','zhangershuai','2023-04-23 23:40:54',NULL,NULL,'http://mail.highgo.com:9900/');
/*!40000 ALTER TABLE `t_mail_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity`
--

DROP TABLE IF EXISTS `t_opportunity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(25) NOT NULL COMMENT '商机Code',
  `name` varchar(100) NOT NULL COMMENT '商机名称',
  `cust_id` bigint(20) NOT NULL COMMENT '客户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '商机所在部门',
  `owner_id` bigint(20) DEFAULT NULL COMMENT '商机维护人员',
  `shared_id` varchar(100) DEFAULT NULL COMMENT '商机共享到人-列表',
  `shared_dept_id` varchar(100) DEFAULT NULL COMMENT '商机共享到部门-列表',
  `create_by` varchar(25) DEFAULT NULL COMMENT '商机创建者',
  `create_time` datetime DEFAULT NULL COMMENT '商机创建时间',
  `update_by` varchar(25) DEFAULT NULL COMMENT '商机更新者',
  `update_time` datetime DEFAULT NULL COMMENT '商机更新时间',
  `remark` varchar(100) DEFAULT NULL,
  `current_stage` char(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_opportunity_un` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机表-主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity`
--

LOCK TABLES `t_opportunity` WRITE;
/*!40000 ALTER TABLE `t_opportunity` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER opp_move_his
before delete 
ON t_opportunity FOR EACH row
insert into t_opportunity_his select * from t_opportunity where id = old.id */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `t_opportunity_advances`
--

DROP TABLE IF EXISTS `t_opportunity_advances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_advances` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opportunity_code` varchar(25) NOT NULL COMMENT '商机code',
  `stage` char(2) DEFAULT NULL COMMENT '当前阶段',
  `advances` varchar(200) DEFAULT NULL COMMENT '进展内容',
  `advances_date` date DEFAULT NULL COMMENT '进展时间',
  `creator` varchar(25) DEFAULT NULL COMMENT '提报人',
  `create_time` datetime DEFAULT NULL COMMENT '提报时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机进展表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_advances`
--

LOCK TABLES `t_opportunity_advances` WRITE;
/*!40000 ALTER TABLE `t_opportunity_advances` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_advances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_competitor`
--

DROP TABLE IF EXISTS `t_opportunity_competitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_competitor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID[自增]',
  `opportunity_code` varchar(25) CHARACTER SET utf8mb3 NOT NULL COMMENT '关联商机code',
  `competitor_id` bigint(20) DEFAULT NULL COMMENT '竞争对手ID',
  `competitor_name` varchar(100) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '竞争对手名称',
  `product` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '参与项目的产品名称',
  `unit_price` decimal(10,0) DEFAULT NULL COMMENT '产品单价',
  `advantage` varchar(50) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '对方在项目中最强势优势',
  `has_same_advantage` char(2) DEFAULT NULL COMMENT '公司是否具备相同优势',
  `memo` varchar(500) CHARACTER SET utf8mb3 DEFAULT NULL COMMENT '备注',
  `tender_price` decimal(10,0) DEFAULT NULL COMMENT '投标价格',
  `tender_total_price` decimal(10,0) DEFAULT NULL COMMENT '投标总价',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='竞争对手信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_competitor`
--

LOCK TABLES `t_opportunity_competitor` WRITE;
/*!40000 ALTER TABLE `t_opportunity_competitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_competitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_contact_info`
--

DROP TABLE IF EXISTS `t_opportunity_contact_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_contact_info` (
  `key_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opportunity_code` varchar(100) DEFAULT NULL COMMENT '商机编码',
  `contact_id` bigint(20) NOT NULL COMMENT '关联联系人表ID',
  `exposure` varchar(50) DEFAULT NULL COMMENT '对接情况',
  `relationship` varchar(20) DEFAULT NULL COMMENT '关系程度',
  `intention` varchar(50) DEFAULT NULL COMMENT '意向厂商',
  `side_visited` char(2) DEFAULT NULL COMMENT '是否会面对手',
  PRIMARY KEY (`key_id`),
  KEY `t_opportunity_contact_info_contact_id_IDX` (`contact_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机关键联系人信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_contact_info`
--

LOCK TABLES `t_opportunity_contact_info` WRITE;
/*!40000 ALTER TABLE `t_opportunity_contact_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_contact_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_cost`
--

DROP TABLE IF EXISTS `t_opportunity_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_cost` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opportunity_code` varchar(25) CHARACTER SET utf8mb3 NOT NULL COMMENT '商机code',
  `cost` decimal(10,0) NOT NULL COMMENT '花费金额',
  `input_user_id` bigint(20) DEFAULT NULL COMMENT '进入人员ID',
  `input_user_name` varchar(100) DEFAULT NULL COMMENT '进入人员名称',
  `use_time` datetime NOT NULL COMMENT '使用时间',
  `reason` varchar(200) CHARACTER SET utf8mb3 NOT NULL COMMENT '使用原因',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '填报人员Id',
  `create_by` varchar(20) DEFAULT NULL COMMENT '填报人名',
  `create_time` datetime DEFAULT NULL COMMENT '填报时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '操作人名',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='商机对应花费表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_cost`
--

LOCK TABLES `t_opportunity_cost` WRITE;
/*!40000 ALTER TABLE `t_opportunity_cost` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_his`
--

DROP TABLE IF EXISTS `t_opportunity_his`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_his` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `code` varchar(25) NOT NULL COMMENT '商机Code',
  `name` varchar(100) NOT NULL COMMENT '商机名称',
  `cust_id` bigint(20) NOT NULL COMMENT '客户ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '商机所在部门',
  `owner_id` bigint(20) DEFAULT NULL COMMENT '商机维护人员',
  `shared_id` varchar(100) DEFAULT NULL COMMENT '商机共享到人-列表',
  `shared_dept_id` varchar(100) DEFAULT NULL COMMENT '商机共享到部门-列表',
  `create_by` varchar(25) DEFAULT NULL COMMENT '商机创建者',
  `create_time` datetime DEFAULT NULL COMMENT '商机创建时间',
  `update_by` varchar(25) DEFAULT NULL COMMENT '商机更新者',
  `update_time` datetime DEFAULT NULL COMMENT '商机更新时间',
  `remark` varchar(100) DEFAULT NULL,
  `current_stage` char(2) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机表-主表-历史表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_his`
--

LOCK TABLES `t_opportunity_his` WRITE;
/*!40000 ALTER TABLE `t_opportunity_his` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_his` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_policy`
--

DROP TABLE IF EXISTS `t_opportunity_policy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_policy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opportunity_code` varchar(25) NOT NULL COMMENT '商机的Code',
  `policy_id` bigint(20) DEFAULT NULL COMMENT '文件的ID',
  `policy_name` varchar(50) DEFAULT NULL COMMENT '文件的名字',
  `release_date` date DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_opportunity_policy_un` (`opportunity_code`,`policy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机对应的支持政策文件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_policy`
--

LOCK TABLES `t_opportunity_policy` WRITE;
/*!40000 ALTER TABLE `t_opportunity_policy` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_policy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_property`
--

DROP TABLE IF EXISTS `t_opportunity_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_property` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `property_label` varchar(50) DEFAULT NULL COMMENT '字段名称',
  `property_key` varchar(50) NOT NULL COMMENT '字段名',
  `property_val` varchar(200) DEFAULT NULL COMMENT '字段值',
  `opportunity_code` varchar(25) NOT NULL COMMENT '商机code',
  `model` varchar(25) DEFAULT NULL COMMENT '模块归属',
  `status` char(2) DEFAULT NULL COMMENT '是否启用',
  `data_type` varchar(10) DEFAULT 'text' COMMENT '字段数据类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_opportunity_property_un` (`opportunity_code`,`property_key`,`model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机属性纵表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_property`
--

LOCK TABLES `t_opportunity_property` WRITE;
/*!40000 ALTER TABLE `t_opportunity_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_quotation`
--

DROP TABLE IF EXISTS `t_opportunity_quotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_quotation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opportunity_code` varchar(100) NOT NULL COMMENT '商机code',
  `quotation_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '报价信息json' CHECK (json_valid(`quotation_json`)),
  `opportunity_stage` varchar(3) NOT NULL COMMENT '商机所在阶段',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建报价单人',
  `create_time` datetime DEFAULT NULL COMMENT '创建报价时间',
  `update_by` varchar(100) DEFAULT NULL COMMENT '修改报价人',
  `update_time` datetime DEFAULT NULL COMMENT '修改报价时间',
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_opportunity_quotation_opportunity_code_IDX` (`opportunity_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机产品报价';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_quotation`
--

LOCK TABLES `t_opportunity_quotation` WRITE;
/*!40000 ALTER TABLE `t_opportunity_quotation` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_quotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_software_operation`
--

DROP TABLE IF EXISTS `t_opportunity_software_operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_software_operation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opportunity_code` varchar(25) NOT NULL COMMENT '商机Code',
  `application_id` bigint(20) DEFAULT NULL COMMENT '应用软件Id',
  `application_name` varchar(50) DEFAULT NULL COMMENT '应用软件名字',
  `db_base` varchar(10) DEFAULT NULL COMMENT '软件在用的数据库',
  `isv_need_adapted` char(1) DEFAULT NULL COMMENT '是否需要适配',
  `target_adapted_progress` char(3) DEFAULT NULL COMMENT '招标前目标进度',
  `current_adapted_progress` char(3) DEFAULT NULL COMMENT '当前适配进度',
  `operational_id` bigint(20) DEFAULT NULL COMMENT '运作主体Id',
  `operational_name` varchar(50) DEFAULT NULL COMMENT '运作主体名字',
  `operational_tender` char(3) DEFAULT NULL COMMENT '运作主体参与投标标识',
  `operational_support_db` varchar(150) DEFAULT NULL COMMENT '运作主体支持的数据库',
  `create_by` varchar(25) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(25) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机应用软件运营信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_software_operation`
--

LOCK TABLES `t_opportunity_software_operation` WRITE;
/*!40000 ALTER TABLE `t_opportunity_software_operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_software_operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_stage_change_his`
--

DROP TABLE IF EXISTS `t_opportunity_stage_change_his`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_stage_change_his` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opportunity_code` varchar(25) NOT NULL COMMENT '商机code',
  `source_stage` char(2) NOT NULL COMMENT '老阶段',
  `target_stage` char(2) NOT NULL COMMENT '新阶段',
  `change_time` datetime DEFAULT NULL COMMENT '转变时间',
  `operator_id` bigint(20) NOT NULL COMMENT '操作员ID',
  `operator_name` varchar(50) DEFAULT NULL COMMENT '操作员名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机阶段变更历史';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_stage_change_his`
--

LOCK TABLES `t_opportunity_stage_change_his` WRITE;
/*!40000 ALTER TABLE `t_opportunity_stage_change_his` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_stage_change_his` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_stage_transfer_config`
--

DROP TABLE IF EXISTS `t_opportunity_stage_transfer_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_stage_transfer_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source_stage` char(2) DEFAULT NULL COMMENT '源阶段',
  `target_stage` char(2) DEFAULT NULL COMMENT '目标阶段',
  `status` char(1) DEFAULT NULL COMMENT '是否启用',
  `conditions` varchar(100) DEFAULT NULL COMMENT '显示条件',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_opportunity_stage_transfer_config_un` (`source_stage`,`target_stage`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COMMENT='商机阶段流转配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_stage_transfer_config`
--

LOCK TABLES `t_opportunity_stage_transfer_config` WRITE;
/*!40000 ALTER TABLE `t_opportunity_stage_transfer_config` DISABLE KEYS */;
INSERT INTO `t_opportunity_stage_transfer_config` VALUES (1,'1','2','Y',NULL),(2,'1','6','Y',NULL),(3,'2','3','Y',NULL),(4,'2','6','Y',NULL),(5,'3','4','Y',NULL),(6,'3','6','Y',NULL),(7,'4','5','Y',NULL),(8,'4','6','Y',NULL),(9,'5','6','Y',NULL),(10,'5','9','Y',NULL),(11,'4','7','Y',NULL),(12,'4','8','Y',NULL),(13,'1','11','Y',NULL),(14,'2','11','Y',NULL),(15,'3','11','Y',NULL),(16,'4','11','Y',NULL),(17,'5','11','Y',NULL),(18,'6','3','Y',NULL),(19,'7','3','Y',NULL),(20,'8','3','Y',NULL),(21,'1','7','Y',NULL),(22,'1','8','Y',NULL),(23,'2','7','Y',NULL),(24,'2','8','Y',NULL),(25,'3','7','Y',NULL),(26,'3','8','Y',NULL),(27,'5','7','Y',NULL),(28,'5','8','Y',NULL);
/*!40000 ALTER TABLE `t_opportunity_stage_transfer_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_opportunity_support`
--

DROP TABLE IF EXISTS `t_opportunity_support`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_opportunity_support` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `opportunity_code` varchar(100) NOT NULL COMMENT '商机code',
  `opportunity_stage` varchar(3) NOT NULL COMMENT '商机所在阶段',
  `support_id` bigint(20) DEFAULT NULL COMMENT '支持人ID',
  `support_name` varchar(25) DEFAULT NULL COMMENT '支持人名称',
  `support_val` varchar(200) DEFAULT NULL COMMENT '支持内容',
  `create_by` varchar(25) DEFAULT NULL COMMENT '填报人',
  `create_time` datetime DEFAULT NULL COMMENT '填报时间',
  `update_by` varchar(25) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `t_opportunity_support_opportunity_code_IDX` (`opportunity_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商机支持人员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_opportunity_support`
--

LOCK TABLES `t_opportunity_support` WRITE;
/*!40000 ALTER TABLE `t_opportunity_support` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_opportunity_support` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_policy_file`
--

DROP TABLE IF EXISTS `t_policy_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_policy_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `file_num` varchar(50) NOT NULL COMMENT '发文字号',
  `file_path` varchar(100) DEFAULT NULL COMMENT '文件路径',
  `release_date` date NOT NULL COMMENT '发布时间',
  `release_office` varchar(100) NOT NULL COMMENT '发文机构',
  `release_year` int(11) NOT NULL COMMENT '发布年分',
  `province` varchar(10) NOT NULL COMMENT '适用省份',
  `city` varchar(10) DEFAULT NULL COMMENT '适用市',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注说明',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `dept_id` varchar(20) DEFAULT NULL COMMENT '当前拥有者部门',
  `owner_id` varchar(20) DEFAULT NULL COMMENT '当前拥有者',
  `status` char(2) DEFAULT NULL COMMENT '文件可用状态',
  PRIMARY KEY (`id`),
  KEY `t_policy_file_name_IDX` (`name`) USING BTREE,
  KEY `t_policy_file_release_date_IDX` (`release_date`) USING BTREE,
  KEY `t_policy_file_release_office_IDX` (`release_office`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='政策文件上传记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_policy_file`
--

LOCK TABLES `t_policy_file` WRITE;
/*!40000 ALTER TABLE `t_policy_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_policy_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_share_config`
--

DROP TABLE IF EXISTS `t_share_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_share_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `data_model` varchar(10) NOT NULL,
  `source_ids` varchar(100) NOT NULL,
  `source_type` char(2) NOT NULL,
  `target_ids` varchar(100) NOT NULL,
  `target_type` char(2) NOT NULL,
  `permission` char(2) NOT NULL,
  `exp_time` datetime DEFAULT NULL,
  `create_by` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(100) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据被动共享规则';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_share_config`
--

LOCK TABLES `t_share_config` WRITE;
/*!40000 ALTER TABLE `t_share_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_share_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_transfer_log`
--

DROP TABLE IF EXISTS `t_transfer_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_transfer_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `model` varchar(20) NOT NULL COMMENT '数据模块',
  `user_from` varchar(100) NOT NULL COMMENT '来源',
  `user_to` varchar(100) NOT NULL COMMENT '目标',
  `record_ids` varchar(1010) NOT NULL COMMENT '操作的记录的',
  `quantity` int(10) DEFAULT NULL COMMENT '单次转移数量',
  `reason` varchar(100) DEFAULT NULL COMMENT '转移原因',
  `action_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='信息转接记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_transfer_log`
--

LOCK TABLES `t_transfer_log` WRITE;
/*!40000 ALTER TABLE `t_transfer_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_transfer_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'highgo_crm'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-09 16:37:02
