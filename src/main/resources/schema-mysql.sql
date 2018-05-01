CREATE DATABASE  IF NOT EXISTS `dtrade` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dtrade`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: dtrade
-- ------------------------------------------------------
-- Server version	5.7.17

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `balance` decimal(19,2) DEFAULT NULL,
  `blocked` bit(1) NOT NULL,
  `canceled` bit(1) NOT NULL,
  `confirmed` bit(1) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `guid` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `balance_activity`
--

DROP TABLE IF EXISTS `balance_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `balance_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) NOT NULL,
  `balance_activity_type` varchar(255) NOT NULL,
  `create_date` bigint(20) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `buy_order_id` bigint(20) DEFAULT NULL,
  `sell_order_id` bigint(20) DEFAULT NULL,
  `balance_snapshot` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKninbfrjd3pg34ngly4rk7ey70` (`account_id`),
  KEY `FKf0ro1fn6ykbujtws4e2hdefnc` (`buy_order_id`),
  KEY `FKxe9bbef408kr3pe3o594hjv8` (`sell_order_id`),
  CONSTRAINT `FKf0ro1fn6ykbujtws4e2hdefnc` FOREIGN KEY (`buy_order_id`) REFERENCES `trade_order` (`id`),
  CONSTRAINT `FKninbfrjd3pg34ngly4rk7ey70` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKxe9bbef408kr3pe3o594hjv8` FOREIGN KEY (`sell_order_id`) REFERENCES `trade_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=230510 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category_tick`
--

DROP TABLE IF EXISTS `category_tick`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_tick` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avarage` decimal(19,2) NOT NULL,
  `score` int(11) NOT NULL,
  `time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `diamond`
--

DROP TABLE IF EXISTS `diamond`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diamond` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `carats` decimal(19,2) DEFAULT NULL,
  `clarity` decimal(19,2) DEFAULT NULL,
  `diamond_status` varchar(255) NOT NULL,
  `diamond_type` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) NOT NULL,
  `score` decimal(19,2) DEFAULT NULL,
  `account_id` bigint(20) NOT NULL,
  `total_stock_amount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKah40au58bq4q3woch5v1cxgb2` (`account_id`),
  CONSTRAINT `FKah40au58bq4q3woch5v1cxgb2` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `diamond_activity`
--

DROP TABLE IF EXISTS `diamond_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `diamond_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` bigint(20) NOT NULL,
  `diamond_activity_type` varchar(255) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `buyer_id` bigint(20) DEFAULT NULL,
  `diamond_id` bigint(20) NOT NULL,
  `seller_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjivbc6dfdhb3eqt7f6671u74x` (`buyer_id`),
  KEY `FK8w926t0c1c2c10xy1n5nkh6er` (`diamond_id`),
  KEY `FKshbo6jajllh8j90ybf8oohodq` (`seller_id`),
  CONSTRAINT `FK8w926t0c1c2c10xy1n5nkh6er` FOREIGN KEY (`diamond_id`) REFERENCES `diamond` (`id`),
  CONSTRAINT `FKjivbc6dfdhb3eqt7f6671u74x` FOREIGN KEY (`buyer_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKshbo6jajllh8j90ybf8oohodq` FOREIGN KEY (`seller_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `offering`
--

DROP TABLE IF EXISTS `offering`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offering` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cancel_previous` tinyint(1) DEFAULT '1',
  `create_date` bigint(20) NOT NULL,
  `offering_status` varchar(255) NOT NULL,
  `offering_type` varchar(255) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `buyer_id` bigint(20) NOT NULL,
  `diamond_id` bigint(20) NOT NULL,
  `seller_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9o94gcjd3es4psxjhrd5ocndf` (`buyer_id`),
  KEY `FK8riwvic1q5dec1sltfh5yuu0j` (`diamond_id`),
  KEY `FKcuvbr8iop4px9raim09tptxj0` (`seller_id`),
  CONSTRAINT `FK8riwvic1q5dec1sltfh5yuu0j` FOREIGN KEY (`diamond_id`) REFERENCES `diamond` (`id`),
  CONSTRAINT `FK9o94gcjd3es4psxjhrd5ocndf` FOREIGN KEY (`buyer_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FKcuvbr8iop4px9raim09tptxj0` FOREIGN KEY (`seller_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quote`
--

DROP TABLE IF EXISTS `quote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quote` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ask` decimal(12,5) DEFAULT NULL,
  `bid` decimal(12,5) DEFAULT NULL,
  `price` decimal(12,5) DEFAULT NULL,
  `quote_type` varchar(255) NOT NULL,
  `time` bigint(20) DEFAULT NULL,
  `diamond_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8qmb0c7lquyrdrda5o914h2np` (`diamond_id`),
  KEY `quote_time_index` (`time`) USING BTREE,
  CONSTRAINT `FK8qmb0c7lquyrdrda5o914h2np` FOREIGN KEY (`diamond_id`) REFERENCES `diamond` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4430736 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `diamond_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq8g2v10you4x7q8onab8iy3ye` (`account_id`),
  KEY `FKax1c78hakdsu6u4tocbydw862` (`diamond_id`),
  CONSTRAINT `FKax1c78hakdsu6u4tocbydw862` FOREIGN KEY (`diamond_id`) REFERENCES `diamond` (`id`),
  CONSTRAINT `FKq8g2v10you4x7q8onab8iy3ye` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stock_activity`
--

DROP TABLE IF EXISTS `stock_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) NOT NULL,
  `create_date` bigint(20) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `buy_order_id` bigint(20) NOT NULL,
  `diamond_id` bigint(20) DEFAULT NULL,
  `sell_order_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKirecygoh0igoi12t3fd01d1j` (`buy_order_id`),
  KEY `FKdo8ixp9utc95q1i1vqa5y59bn` (`diamond_id`),
  KEY `FK9ahv1i6dvcsxs39so0tdcljp2` (`sell_order_id`),
  CONSTRAINT `FK9ahv1i6dvcsxs39so0tdcljp2` FOREIGN KEY (`sell_order_id`) REFERENCES `trade_order` (`id`),
  CONSTRAINT `FKdo8ixp9utc95q1i1vqa5y59bn` FOREIGN KEY (`diamond_id`) REFERENCES `diamond` (`id`),
  CONSTRAINT `FKirecygoh0igoi12t3fd01d1j` FOREIGN KEY (`buy_order_id`) REFERENCES `trade_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115150 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trade_order`
--

DROP TABLE IF EXISTS `trade_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trade_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) NOT NULL,
  `creation_date` bigint(20) NOT NULL,
  `execution_date` bigint(20) DEFAULT NULL,
  `price` decimal(19,2) NOT NULL,
  `trade_order_type` varchar(255) NOT NULL,
  `trader_order_status` varchar(255) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  `diamond_id` bigint(20) NOT NULL,
  `initial_amount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoo7xiemrnnd0hqg4jal2ohluj` (`account_id`),
  KEY `FKmst6y94vgg26prceqhxfx1dwq` (`diamond_id`),
  KEY `trade_order_execution_date_index` (`execution_date`) USING BTREE,
  KEY `trade_order_creation_date_index` (`creation_date`) USING BTREE,
  CONSTRAINT `FKmst6y94vgg26prceqhxfx1dwq` FOREIGN KEY (`diamond_id`) REFERENCES `diamond` (`id`),
  CONSTRAINT `FKoo7xiemrnnd0hqg4jal2ohluj` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=231392 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

create index trade_order_trade_order_status on trade_order (trader_order_status) using HASH;
-- Dump completed on 2018-04-12 22:53:48
