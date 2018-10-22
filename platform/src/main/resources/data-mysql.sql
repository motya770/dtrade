-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: dtrade1
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
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'\0','\0','','','3e7f4ed2-e5a3-4940-8ac5-faf309988d86','motya770@gmail.com','de88e3e4ab202d87754078cbb2df6063','+123456789a','ROLE_ADMIN',0),(2,'\0','\0','','','509958e2-b265-45e9-bf91-36ad2bce2024','test@test.com','de88e3e4ab202d87754078cbb2df6063','+19923993993','ROLE_ACCOUNT',0),(11,'\0','\0','','','29301bcd-5a05-4585-b837-7caea68072fd','t1@t1.com','25d55ad283aa400af464c76d713c07ad','+232342344','ROLE_ACCOUNT',0),(12,'\0','\0','','','1abab051-0130-4570-ad92-bbdc9d6568d3','t1@213.com','25d55ad283aa400af464c76d713c07ad','+1232131221332','ROLE_ACCOUNT',0),(13,'\0','\0','','','92ae23d3-db4c-492c-9552-fe3a5d47571d','t1@1232.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(14,'\0','\0','','','5bc1ee4d-bdc7-4ae0-95d9-ad9da866d34d','motya7701@gmail.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(15,'\0','\0','','','a9ca1920-69aa-4f95-a5bd-a2efe2e36f46','m1t@t.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(16,'\0','\0','','','5a38f39d-1e53-4b06-9135-bf835e0d5a21','m1@qwe23.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(17,'\0','\0','','','f5a08168-a182-46ea-b213-4a81de298e61','m1@t.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(18,'\0','\0','','','6646d634-f37d-4bf4-aed7-dfe4ee50d8ab','test1@test1.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(19,'\0','\0','','','726f39de-453c-493b-bd8b-963be3a4c551','test2@test2.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(20,'\0','\0','','','58814bd9-ff3a-4b89-830f-2741b1d05bb2','t2@t.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(21,'\0','\0','','','ebda56d7-4e61-4a32-90e7-f3b1fcfd5bf4','t2@t4.com','25d55ad283aa400af464c76d713c07ad',NULL,'ROLE_ACCOUNT',0),(34,'\0','\0','','','498189f5-5559-4bf1-939d-2e1cd59e1042','testAccountDiana0@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(35,'\0','\0','','','13c75b45-37a1-486f-b3ca-bff32cab8e7b','testAccountDiana1@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(36,'\0','\0','','','49c87366-5b8a-495d-8867-939b2458c73c','testAccountBTCUSD0@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(37,'\0','\0','','','ab9c0952-b045-44ea-a8c1-caca236890c5','testAccountBTCUSD1@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(38,'\0','\0','','','bd76d7f1-a9ea-4a8d-8c67-3617583ff664','testAccountETHUSD0@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(39,'\0','\0','','','b88dd053-46ac-485e-886e-72d4ee09ae99','testAccountETHUSD1@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(40,'\0','\0','','','b728f784-ac00-4498-8a74-3d7bc388c062','testAccountLTCUSD0@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(41,'\0','\0','','','bc3901fb-86c3-409d-ac64-2e3b26440c6a','testAccountLTCUSD1@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(42,'\0','\0','','','c2549920-0330-4fb3-bba2-8ecac7faa775','testAccountETHBTC0@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(43,'\0','\0','','','8f3ce51e-4db8-4a29-91f3-44d056c0b17a','testAccountETHBTC1@gmail.com','6dbd0fe19c9a301c4708287780df41a2',NULL,'ROLE_ACCOUNT',1),(46,'\0','\0','','','7f3e960e-f2cd-4a38-a6c5-51f7a46200f0','testAccountDiana10@gmail.com','{bcrypt}$2a$10$vHl2n3BAnsJGTIIzJSWURud52dB8Y6hAt0mRxB7J2fv0KVqORdSiO',NULL,'ROLE_ACCOUNT',1),(47,'\0','\0','','','eaa4021c-c8c6-4ba0-948f-a4e989eca8e6','testAccountDiana11@gmail.com','{bcrypt}$2a$10$3pNREXdDBEE94AvDrDk/IuuSasd7RM/izK4CZsKsl9W8xqW4igzVS',NULL,'ROLE_ACCOUNT',1),(48,'\0','\0','','','3fb8e6c7-3fd6-47df-bacd-e9533f0d24f9','test2@test.com','{bcrypt}$2a$10$bFzWw7FWT82YeSkJxPPmwONIhXuWTI9wtB/HaWmAwRsNkbOWcc4Pe',NULL,'ROLE_ACCOUNT',0),(49,'\0','\0','','','a76e9f4a-d76c-4caf-8694-b91e6b9454d3','test3@test.com','{bcrypt}$2a$10$XDXgV9F7ALIvLUvrWEQaWeU999HHk2R1dZqpLjXGGskAOdn7RJPDW',NULL,'ROLE_ACCOUNT',0),(50,'\0','\0','','','9b8e44b4-9655-4395-ae15-ef9982c09c98','test4@test.com','{bcrypt}$2a$10$gRkIGSVn3Hc9so9y/EkhueTC4TQbNMFIR8f1yQGrHggHLn04pJYPu',NULL,'ROLE_ACCOUNT',0),(51,'\0','\0','','','dc149a0c-aa35-4166-b9b4-4514660565ad','testAccountApple0@gmail.com','{bcrypt}$2a$10$vgseOI58DN7CS7vmITZ5weu2j2p1gMF7tKJ4RQWnFOVfR/qKEitia',NULL,'ROLE_ACCOUNT',1),(52,'\0','\0','','','e5a2ad1d-7309-40e8-9978-0687e49018d6','testAccountApple1@gmail.com','{bcrypt}$2a$10$ZLzORfuUWjiKx5ZlPc8PAelWpgZha1L.oidHIPx0OwMl0YVxYbpq.',NULL,'ROLE_ACCOUNT',1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `balance`
--

LOCK TABLES `balance` WRITE;
/*!40000 ALTER TABLE `balance` DISABLE KEYS */;
INSERT INTO `balance` VALUES (40398,99400.00000000,0.00000000,0.00000000,1,'USD',1,NULL),(40415,9995867.02503170,0.00000000,5065.30840270,35,'USD',1,NULL),(42242,10004133.44334239,0.00000000,-4133.44334239,34,'USD',1,NULL),(42244,9994873.89515155,0.00000000,800.26839636,34,'BTC',1,NULL),(42245,10000000.00000000,0.00000000,0.00000000,34,'ETH',1,NULL),(42246,10000000.00000000,0.00000000,0.00000000,34,'LTC',0,NULL),(42247,9997190.35842633,0.00000000,-701.16875602,36,'USD',1,NULL),(42248,10000000.40008102,0.00000000,0.00000000,36,'BTC',1,NULL),(42249,10000000.00000000,0.00000000,0.00000000,36,'ETH',1,NULL),(42250,10000000.00000000,0.00000000,0.00000000,36,'LTC',0,NULL),(42251,11683379.37614265,0.00000000,0.00000000,39,'USD',1,NULL),(42252,10000000.00000000,0.00000000,0.00000000,39,'BTC',1,NULL),(42253,9973033.19831925,0.00000000,25103.23198496,39,'ETH',1,NULL),(42254,10000000.00000000,0.00000000,0.00000000,39,'LTC',0,NULL),(42255,9659812.05118395,0.00000000,199448.34380044,40,'USD',1,NULL),(42256,10000000.00000000,0.00000000,0.00000000,40,'BTC',1,NULL),(42257,10000000.00000000,0.00000000,0.00000000,40,'ETH',1,NULL),(42258,10027236.02453634,0.00000000,-17983.01775227,40,'LTC',0,NULL),(42259,10000000.00000000,0.00000000,0.00000000,42,'USD',1,NULL),(42260,9973885.26521601,0.00000000,2760.00770687,42,'BTC',1,NULL),(42261,10026934.18290102,0.00000000,-17996.09707692,42,'ETH',1,NULL),(42262,10000000.00000000,0.00000000,0.00000000,42,'LTC',0,NULL),(42263,10005206.83906268,0.00000000,0.00000000,35,'BTC',1,NULL),(42264,10000000.00000000,0.00000000,0.00000000,35,'ETH',1,NULL),(42265,10000000.00000000,0.00000000,0.00000000,35,'LTC',0,NULL),(42266,10341852.26193136,0.00000000,0.00000000,41,'USD',1,NULL),(42267,10000000.00000000,0.00000000,0.00000000,41,'BTC',1,NULL),(42268,10000000.00000000,0.00000000,0.00000000,41,'ETH',1,NULL),(42269,9972793.77517533,0.00000000,24485.43596678,41,'LTC',0,NULL),(42270,10000000.00000000,0.00000000,0.00000000,43,'USD',1,NULL),(42271,10026117.99825043,0.00000000,0.00000000,43,'BTC',1,NULL),(42272,9973094.74558125,0.00000000,24819.96730285,43,'ETH',1,NULL),(42273,10000000.00000000,0.00000000,0.00000000,43,'LTC',0,NULL),(42274,9997780.25345693,0.00000000,59481.18519706,37,'BTC',1,NULL),(42277,8330541.84348903,0.00000000,1115188.30657764,38,'USD',1,NULL),(42278,10000000.00000000,0.00000000,0.00000000,38,'BTC',1,NULL),(42279,10026956.00501547,0.00000000,-17760.51532704,38,'ETH',1,NULL),(42280,10000000.00000000,0.00000000,0.00000000,38,'LTC',0,NULL),(42281,21931524.31679912,0.00000000,0.00000000,37,'USD',1,NULL),(42282,10000000.00000000,0.00000000,0.00000000,37,'ETH',1,NULL),(42283,10000000.00000000,0.00000000,0.00000000,37,'LTC',0,NULL),(42284,100000.00000000,0.00000000,0.00000000,1,'BTC',1,NULL),(42542,100001.50000000,0.00000000,0.00000000,1,'ETH',1,NULL),(42543,100000.00000000,0.00000000,0.00000000,1,'LTC',0,NULL),(42544,0.00000000,0.00000000,0.00000000,49,'USD',0,NULL),(42545,0.00000000,0.00000000,0.00000000,49,'BTC',0,NULL),(42546,0.00000000,0.00000000,0.00000000,49,'ETH',0,NULL),(42547,103568.65505519,0.00000000,0.00000000,50,'USD',1,NULL),(42548,99999.60008075,0.00000000,0.60008075,50,'BTC',1,NULL),(42549,99998.00000000,0.00000000,0.00000000,50,'ETH',1,NULL),(43044,100000.00000000,0.00000000,0.00000000,37,'BHC',0,NULL),(43045,100000.00000000,0.00000000,0.00000000,37,'ETC',0,NULL),(43047,100000.00000000,0.00000000,0.00000000,38,'BHC',0,NULL),(43048,100000.00000000,0.00000000,0.00000000,38,'ETC',0,NULL),(43049,100000.00000000,0.00000000,0.00000000,40,'BHC',0,NULL),(43050,100000.00000000,0.00000000,0.00000000,40,'ETC',0,NULL),(43051,100000.00000000,0.00000000,0.00000000,43,'BHC',0,NULL),(43052,100000.00000000,0.00000000,0.00000000,43,'ETC',0,NULL),(43053,100000.00000000,0.00000000,0.00000000,39,'BHC',0,NULL),(43054,100000.00000000,0.00000000,0.00000000,39,'ETC',0,NULL),(43055,100000.00000000,0.00000000,0.00000000,42,'BHC',0,NULL),(43056,100000.00000000,0.00000000,0.00000000,42,'ETC',0,NULL),(43059,100000.00000000,0.00000000,0.00000000,41,'BHC',0,NULL),(43060,100000.00000000,0.00000000,0.00000000,41,'ETC',0,NULL),(45019,100000.00000000,0.00000000,0.00000000,36,'BHC',0,NULL),(45020,100000.00000000,0.00000000,0.00000000,36,'ETC',0,NULL),(45022,0.00000000,0.00000000,0.00000000,51,'USD',1,NULL),(45023,0.00000000,0.00000000,0.00000000,51,'BTC',1,NULL),(45024,0.00000000,0.00000000,0.00000000,51,'ETH',1,NULL),(45025,0.00000000,0.00000000,0.00000000,52,'USD',1,NULL),(45026,0.00000000,0.00000000,0.00000000,52,'BTC',1,NULL),(45027,0.00000000,0.00000000,0.00000000,52,'ETH',1,NULL);
/*!40000 ALTER TABLE `balance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,1,'wines ','Wine','\0'),(2,0,'diamonds','Diamond','\0'),(3,2,'coins ','Coins','');
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `diamond`
--

LOCK TABLES `diamond` WRITE;
/*!40000 ALTER TABLE `diamond` DISABLE KEYS */;
INSERT INTO `diamond` VALUES (1,1.20,'VVS1','D','POOR','HIDDEN','CUSHION','Diana1',80001.00,NULL,1000001.00,1,'\0','USD',NULL,NULL,'BTC',NULL,NULL,1.00000000),(2,1.00,'VS2','D','FAIR','ENLISTED','HEART','BTC/USD',7500.00,NULL,100000000.00,1,'','BTC',7093.23616030,7072.02010000,'USD',1535535269189,NULL,0.00151110),(8,1.00,'VS2','D','FAIR','ENLISTED','HEART','ETH/USD',500.00,NULL,1000000.00,1,'','ETH',297.28732439,296.39813000,'USD',1535499166129,NULL,1.00000000),(9,1.00,'VS2','D','FAIR','ENLISTED','HEART','LTC/USD',300.00,0,1000000000.00,1,'','LTC',62.60143658,62.41419400,'USD',1535535222991,NULL,0.40000000),(30,1.00,'VS2','D','FAIR','ENLISTED','HEART','ETH/BTC',0.06,NULL,10000000.00,1,'','ETH',0.04148963,0.04136553,'BTC',1535535268265,NULL,0.00200000);
/*!40000 ALTER TABLE `diamond` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-29 16:00:09