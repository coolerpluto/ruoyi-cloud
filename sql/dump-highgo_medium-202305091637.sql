-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 192.168.110.201    Database: highgo_medium
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
-- Table structure for table `t_file_down_his`
--

DROP TABLE IF EXISTS `t_file_down_his`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_file_down_his` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `opportunity_num` varchar(24) NOT NULL COMMENT '关联的商机编号',
  `file_id` bigint(20) NOT NULL COMMENT '文件表对应的ID',
  `file_type` char(8) NOT NULL DEFAULT 'NORMAL' COMMENT '文件类型(MD5,SIGN,MEDIUM,LICENSE,NORMAL)',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件下载历史记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_file_down_his`
--

LOCK TABLES `t_file_down_his` WRITE;
/*!40000 ALTER TABLE `t_file_down_his` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_file_down_his` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_file_info`
--

DROP TABLE IF EXISTS `t_file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_file_info` (
  `id` bigint(20) NOT NULL COMMENT '记录ID',
  `file_name` varchar(64) NOT NULL COMMENT '文件名字',
  `file_path` varchar(128) NOT NULL DEFAULT '' COMMENT '文件在服务器的路径',
  `file_type` char(6) NOT NULL DEFAULT '' COMMENT '文件类型(文件后缀)',
  `file_server` varchar(20) NOT NULL DEFAULT '' COMMENT '文件服务器',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1删除）',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_file_info`
--

LOCK TABLES `t_file_info` WRITE;
/*!40000 ALTER TABLE `t_file_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_file_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_license_info`
--

DROP TABLE IF EXISTS `t_license_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_license_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `serial` varchar(30) NOT NULL COMMENT 'license序列编号',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `opportunity_num` varchar(20) NOT NULL COMMENT '商机编号',
  `auth_type` varchar(10) DEFAULT 'esl' COMMENT '授权方式:esl,asfu,fulluse',
  `customer_name` varchar(10) DEFAULT '' COMMENT '使用客户的名称',
  `purpose` varchar(10) DEFAULT 'Test' COMMENT '用途:Adapter适配,Test测试,Delivery交付',
  `expire_time` datetime DEFAULT NULL COMMENT '授权到期时间',
  `prod_type` char(10) DEFAULT 'db' COMMENT '用途:db 数据库,proxy,db_ha',
  `db_version` varchar(20) DEFAULT NULL COMMENT '数据库版本',
  `server_url` varchar(150) DEFAULT NULL COMMENT '下载文件的url',
  `other_param` text DEFAULT '' COMMENT '其他的参数',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1删除）',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `lic_file_id` varchar(22) DEFAULT NULL COMMENT '文件记录对应的ID',
  PRIMARY KEY (`id`),
  KEY `t_license_info_customer_name_IDX` (`customer_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权License文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_license_info`
--

LOCK TABLES `t_license_info` WRITE;
/*!40000 ALTER TABLE `t_license_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_license_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_medium_info`
--

DROP TABLE IF EXISTS `t_medium_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_medium_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `medium_name` varchar(100) DEFAULT '未设置' COMMENT '介质文件名称',
  `medium_type` varchar(20) NOT NULL COMMENT '介质文件版本类型',
  `db_version` varchar(20) NOT NULL COMMENT '介质适用数据库版本',
  `cpu_type` varchar(20) NOT NULL COMMENT '介质适用CPU',
  `package_type` varchar(20) NOT NULL COMMENT '介质打包类型',
  `medium_file_id` bigint(20) DEFAULT NULL COMMENT '介质文件 ID',
  `medium_md5_file_id` bigint(20) DEFAULT NULL COMMENT '校验介质文件 ID',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1删除）',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='介质信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_medium_info`
--

LOCK TABLES `t_medium_info` WRITE;
/*!40000 ALTER TABLE `t_medium_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_medium_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_medium_security_info`
--

DROP TABLE IF EXISTS `t_medium_security_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_medium_security_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `medium_id` bigint(20) NOT NULL COMMENT '介质文件id',
  `security_name` varchar(100) DEFAULT NULL COMMENT '附件的名字',
  `file_type` varchar(8) NOT NULL DEFAULT 'NORMAL' COMMENT '文件类型(MD5,SIGN,NORMAL)',
  `file_id` bigint(20) NOT NULL COMMENT '文件 ID',
  `md5_file_id` bigint(20) NOT NULL COMMENT '校验文件 ID',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1删除）',
  `create_id` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(125) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `t_medium_security_info_FK` (`file_id`),
  KEY `t_medium_security_info_FK_1` (`md5_file_id`),
  KEY `t_medium_security_info_FK_2` (`medium_id`),
  CONSTRAINT `t_medium_security_info_FK` FOREIGN KEY (`file_id`) REFERENCES `t_file_info` (`id`),
  CONSTRAINT `t_medium_security_info_FK_1` FOREIGN KEY (`md5_file_id`) REFERENCES `t_file_info` (`id`),
  CONSTRAINT `t_medium_security_info_FK_2` FOREIGN KEY (`medium_id`) REFERENCES `t_medium_info` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='介质文件相关文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_medium_security_info`
--

LOCK TABLES `t_medium_security_info` WRITE;
/*!40000 ALTER TABLE `t_medium_security_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_medium_security_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'highgo_medium'
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
