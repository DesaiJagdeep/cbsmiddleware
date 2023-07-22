-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: cbsmiddleware
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `account_holder_master`
--

DROP TABLE IF EXISTS `account_holder_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_holder_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_holder_code` int DEFAULT NULL,
  `account_holder` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_holder_master`
--

LOCK TABLES `account_holder_master` WRITE;
/*!40000 ALTER TABLE `account_holder_master` DISABLE KEYS */;
INSERT INTO `account_holder_master` VALUES (1,1,'SINGLE','admin',NULL,NULL,NULL),(2,2,'JOINT','admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `account_holder_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_type`
--

DROP TABLE IF EXISTS `activity_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(255) DEFAULT NULL,
  `activity_type_code` int DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_type`
--

LOCK TABLES `activity_type` WRITE;
/*!40000 ALTER TABLE `activity_type` DISABLE KEYS */;
INSERT INTO `activity_type` VALUES (1,'HORTICULTURE',1,'admin',NULL,NULL,NULL),(2,'SUGARCANE',1,'admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `activity_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_log`
--

DROP TABLE IF EXISTS `application_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `error_type` varchar(255) DEFAULT NULL,
  `error_code` varchar(255) DEFAULT NULL,
  `error_message` text,
  `column_number` bigint DEFAULT NULL,
  `iss_portal_id` bigint DEFAULT NULL,
  `sevierity` varchar(255) DEFAULT NULL,
  `expected_solution` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `jhi_row_number` bigint DEFAULT NULL,
  `batch_id` varchar(255) DEFAULT NULL,
  `iss_file_parser_id` bigint DEFAULT NULL,
  `error_record_count` bigint DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_application_log__iss_file_parser_id` (`iss_file_parser_id`),
  CONSTRAINT `fk_application_log__iss_file_parser_id` FOREIGN KEY (`iss_file_parser_id`) REFERENCES `iss_file_parser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_log`
--

LOCK TABLES `application_log` WRITE;
/*!40000 ALTER TABLE `application_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `application_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_log_history`
--

DROP TABLE IF EXISTS `application_log_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_log_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `error_type` varchar(255) DEFAULT NULL,
  `error_code` varchar(255) DEFAULT NULL,
  `error_message` varchar(255) DEFAULT NULL,
  `jhi_row_number` bigint DEFAULT NULL,
  `column_number` bigint DEFAULT NULL,
  `sevierity` varchar(255) DEFAULT NULL,
  `expected_solution` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `iss_file_parser_id` bigint DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_application_log_history__iss_file_parser_id` (`iss_file_parser_id`),
  CONSTRAINT `fk_application_log_history__iss_file_parser_id` FOREIGN KEY (`iss_file_parser_id`) REFERENCES `iss_file_parser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_log_history`
--

LOCK TABLES `application_log_history` WRITE;
/*!40000 ALTER TABLE `application_log_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `application_log_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_transaction`
--

DROP TABLE IF EXISTS `application_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `batch_id` varchar(255) DEFAULT NULL,
  `unique_id` varchar(255) DEFAULT NULL,
  `record_status` bigint DEFAULT NULL,
  `application_status` bigint DEFAULT NULL,
  `kcc_status` bigint DEFAULT NULL,
  `recipient_unique_id` varchar(255) DEFAULT NULL,
  `farmer_id` varchar(255) DEFAULT NULL,
  `iss_file_parser_id` bigint DEFAULT NULL,
  `iss_file_portal_id` bigint DEFAULT NULL,
  `application_errors` text,
  `application_number` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_application__iss_file_parser_id` (`iss_file_parser_id`),
  CONSTRAINT `fk_application__iss_file_parser_id` FOREIGN KEY (`iss_file_parser_id`) REFERENCES `iss_file_parser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_transaction`
--

LOCK TABLES `application_transaction` WRITE;
/*!40000 ALTER TABLE `application_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `application_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_branch_master`
--

DROP TABLE IF EXISTS `bank_branch_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_branch_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_code` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `branch_address` varchar(255) DEFAULT NULL,
  `bank_code` varchar(255) DEFAULT NULL,
  `ifsc_code` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_branch_master`
--

LOCK TABLES `bank_branch_master` WRITE;
/*!40000 ALTER TABLE `bank_branch_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_branch_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank_master`
--

DROP TABLE IF EXISTS `bank_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bank_code` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_master`
--

LOCK TABLES `bank_master` WRITE;
/*!40000 ALTER TABLE `bank_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `batch_transaction`
--

DROP TABLE IF EXISTS `batch_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `status` varchar(255) DEFAULT NULL,
  `batch_details` varchar(255) DEFAULT NULL,
  `application_count` bigint DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `batch_id` varchar(255) DEFAULT NULL,
  `batch_ack_id` varchar(255) DEFAULT NULL,
  `batch_errors` text,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_transaction`
--

LOCK TABLES `batch_transaction` WRITE;
/*!40000 ALTER TABLE `batch_transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `batch_transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cast_category_master`
--

DROP TABLE IF EXISTS `cast_category_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cast_category_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cast_category_code` int DEFAULT NULL,
  `cast_category_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cast_category_master`
--

LOCK TABLES `cast_category_master` WRITE;
/*!40000 ALTER TABLE `cast_category_master` DISABLE KEYS */;
INSERT INTO `cast_category_master` VALUES (1,1,'SC','admin',NULL,NULL,NULL),(2,2,'ST','admin',NULL,NULL,NULL),(3,3,'GEN','admin',NULL,NULL,NULL),(4,4,'OBC','admin',NULL,NULL,NULL),(5,4,'MUSLIM','admin',NULL,NULL,NULL),(6,3,'OPEN','admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `cast_category_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_master`
--

DROP TABLE IF EXISTS `category_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cast_name` varchar(255) DEFAULT NULL,
  `cast_code` varchar(255) DEFAULT NULL,
  `cast_category_code` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_master`
--

LOCK TABLES `category_master` WRITE;
/*!40000 ALTER TABLE `category_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop_category_master`
--

DROP TABLE IF EXISTS `crop_category_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_category_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `category_code` varchar(255) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop_category_master`
--

LOCK TABLES `crop_category_master` WRITE;
/*!40000 ALTER TABLE `crop_category_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `crop_category_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop_master`
--

DROP TABLE IF EXISTS `crop_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `crop_code` varchar(255) DEFAULT NULL,
  `crop_name` varchar(255) DEFAULT NULL,
  `category_code` varchar(255) DEFAULT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=620 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop_master`
--

LOCK TABLES `crop_master` WRITE;
/*!40000 ALTER TABLE `crop_master` DISABLE KEYS */;
INSERT INTO `crop_master` VALUES (1,'021000100','Acid Lime','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(2,'010900200','African Sarson','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(3,'021000300','Almond','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(4,'021400400','Aloe Vera','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(5,'022100500','Amaranthus/Grain Amaranthus','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(6,'022100600','Amarphophallus (Surankand/Elephant Foot Yam)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(7,'041319300','Amla','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(8,'020800800','Anthurium','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(9,'021000900','Aonla','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(10,'021001000','Apple','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(11,'021001005','Apple - >5 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(12,'021001002','Apple - 15 To 40 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(13,'021001001','Apple - 5 To 15 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(14,'021001003','Apple - Hills (15 To 40 Years)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(15,'021001004','Apple - Hills (5 To 15 Years)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(16,'021001100','Apricot','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(17,'021801200','Arecanut','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(18,'041318300','Arecanut - Irrigated','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(19,'041323400','Arecanut ((M.Cost)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(20,'041322600','Arecanut (Mixed Crop)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(21,'041323500','Arecanut (New Plantation)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(22,'041321100','Arecanut(I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(23,'041321900','Arecanut(R)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(24,'022101300','Arum','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(25,'022101400','Ash Gourd (Petha)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(26,'022101401','Ash Gourd (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(27,'022101402','Ash Gourd (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(28,'041320200','Ashwagandha','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(29,'021001500','Avacado','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(30,'022101700','Baby Corn','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(31,'021001800','Bail','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(32,'010920900','Bajra - Napier Hybrid','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(33,'010919200','Bajra Napier Grass','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(34,'021002000','Banana','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(35,'041318600','Banana â others','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(36,'021002001','Banana - Plaintain','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(37,'021002002','Banana - Sucker','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(38,'021002004','Banana - Sucker IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(39,'021002005','Banana - Sucker RF','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(40,'021002003','Banana - Tissue Culture','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(41,'021002006','Banana - Tissue Culture IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(42,'021002007','Banana - Tissue Culture RF','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(43,'041318500','Banana Nendran','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(44,'010402100','Barley (Jau)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(45,'011502200','Barnyard Millet (Kundiraivlli/Sawan','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(46,'010902300','Barseem','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(47,'020502400','Bay Leaf','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(48,'022102500','Beans','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(49,'022102501','Beans (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(50,'022102502','Beans (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(51,'022102700','Beet Root (Garden Beet/Stock Beet)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(52,'022102800','Bell Pepper','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(53,'011902900','Bengal Gram (Chana)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(54,'011902901','Bengal Gram (Chana) - IR','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(55,'011902902','Bengal Gram (Chana) - RF','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(56,'021003000','Ber','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(57,'021403100','Betel Vine','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(58,'020603200','Bhang (Hemp, Canabis)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(59,'022103301','Bhindi (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(60,'022103302','Bhindi (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(61,'022103300','Bhindi(Okra/Ladysfinger)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(62,'010903400','Birdwood Grass','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(63,'020503500','Bishops Weed','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(64,'022103600','Bitter Gourd','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(65,'022103601','Bitter Gourd (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(66,'022103602','Bitter Gourd (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(67,'011903700','Black Gram (Urad)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(68,'011903701','Black Gram (Urad) - IR','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(69,'011903702','Black Gram (Urad) - RF','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(70,'022103800','Bottle Gourd','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(71,'022104100','Brinjal','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(72,'022104200','Broad Bean','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(73,'022104300','Broccoli','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(74,'011604400','Brown Sarson','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(75,'022104500','Brussil\'S Sprouts','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(76,'011504600','Buck Wheat (Kaspat)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(77,'010904700','Buffel Grass (Anjan Grass)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(78,'022104800','Bush Squash','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(79,'022104900','Butter Pea (Vegetable)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(80,'022105000','Cabbage','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(81,'022105001','Cabbage (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(82,'022105002','Cabbage (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(83,'021405100','Caleus','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(84,'022105300','Capsicum','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(85,'020505400','Cardamom','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(86,'020805500','Carnation','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(87,'022105600','Carrot','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(88,'022105601','Carrot (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(89,'022105602','Carrot (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(90,'021805700','Cashew','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(91,'041323000','Cashew (I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(92,'011605801','Castor (Rehri, Rendi, Arandi) - RF','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(93,'022106000','Cauliflower','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(94,'041320400','Cauliflower (Hybrid)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(95,'041319700','Causarina','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(96,'020506100','Celery','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(97,'022106200','Chapan Kaddu','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(98,'021006300','Chestnut','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(99,'041319900','Chicory','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(100,'022106400','Chillies','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(101,'022106403','Chillies - Hills','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(102,'022106401','Chillies - IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(103,'022106402','Chillies - RF','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(104,'022106404','Chilly (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(105,'022106405','Chilly (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(106,'020806500','China Astor','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(107,'022106700','Chinese Cabbage','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(108,'020806800','Chrysanthemum','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(109,'020506900','Cinnamon','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(110,'021007000','Citrus','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(111,'021007003','Citrus - >5 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(112,'021007001','Citrus - 10 To 25 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(113,'021007002','Citrus - 5 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(114,'020507100','Clove','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(115,'022107200','Cluster Bean','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(116,'021807300','Cocoa','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(117,'021807400','Coconut','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(118,'041318200','Coconut â Irrigated','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(119,'041318100','Coconut â Rainfed','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(120,'041322900','Coconut (Crop Maint)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(121,'041323800','Coconut (General )','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(122,'041323200','Coconut (General Intensive)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(123,'041320900','Coconut (I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(124,'041323600','Coconut (M.Cost)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(125,'021807500','Coffee','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(126,'041323900','Coffee (Arabica)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(127,'041324400','Coffee Robusta(I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(128,'041322100','Coffee-Kaveri','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(129,'022107700','Colocasia (Arvi, Arbi)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(130,'011507800','Common Millet (Panivaragu/Chena/Proso Millet/Hogm','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(131,'020507900','Coriander','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(132,'020708000','Cotton (Kapas)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(133,'020708001','Cotton (Kapas) - IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(134,'020708002','Cotton (Kapas) - RF','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(135,'020731701','Cotton (Lint) - I','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(136,'020731702','Cotton (Lint) - II','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(137,'020731703','Cotton (Lint) - III','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(138,'011908100','Cowpea','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(139,'022108200','Cowpea (Vegetable)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(140,'020808300','Crossandra','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(141,'022108400','Cucumber','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(142,'022108401','Cucumber (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(143,'022108402','Cucumber (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(144,'020508500','Cumin','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(145,'022108600','Curry Leaf','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(146,'021008700','Custard Apple','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(147,'020808800','Cymbidium','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(148,'021808900','Datepalm','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(149,'041324700','Davana','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(150,'011109000','Dhaincha','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(151,'010909100','Dharaf Grass','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(152,'020509200','Dill Seed','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(153,'010909300','Dinanath Grass','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(154,'022109500','Dolichos Bean','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(155,'041324500','Dragan Fruits','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(156,'022109600','Drum Stick','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(157,'011909900','Faba Bean (Horse Bean/Windsor Bean)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(158,'020510000','Fennel','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(159,'021010200','Fig','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(160,'011510300','Fingermillet (Ragi/Mandika)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(161,'011510301','Fingermillet (Ragi/Mandika) - Hills','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(162,'011510302','Fingermillet (Ragi/Mandika) - IR','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(163,'011510304','Fingermillet (Ragi/Mandika) - RF','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(164,'011510303','Fingermillet (Ragi/Mandika) - Summer IR','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(165,'010910500','Fodder Maize','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(166,'010910600','Fodder Sorghum','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(167,'022110700','French Bean','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(168,'022110701','French Bean - Hills','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(169,'041324300','Gaillardia','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(170,'020511000','Garlic','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(171,'020511001','Garlic (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(172,'020511002','Garlic (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(173,'020811100','Gerbera','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(174,'020511200','Ginger','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(175,'020511201','Ginger - Hills','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(176,'041322000','Ginger (I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(177,'041322300','Ginger (R) ','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(178,'020811300','Gladiolus','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(179,'041319500','Gloriosa Lily (Medicine Crops)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(180,'020631900','GLORIOSA SUPERBA','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(181,'022111500','Gobhi Sarson','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(182,'010911600','Golden Thimothy','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(183,'021011800','Grape','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(184,'041322700','Grape-Anab-E-Shahi','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(185,'041320600','Grape-E-Bangalore Blue','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(186,'041321700','Grapes (Anabi-E-Shai) Dilkush','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(187,'041320700','Grapes (Thomson Seedless)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(188,'041320300','Grape-Seedless','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(189,'041323700','Grape-Seedless (Maintenance)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(190,'041325200','Grapesmaintence','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(191,'022111900','Greater Yam','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(192,'011912000','Green Gram (Moong)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(193,'011912001','Green Gram (Moong) - IR','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(194,'011912002','Green Gram (Moong) - RF','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(195,'011612100','Groundnut (Pea Nut)','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(196,'011612101','Groundnut (Pea Nut) - IR','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(197,'011612102','Groundnut (Pea Nut) - RF','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(198,'011612103','Groundnut (Pea Nut) - Summer','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(199,'011612104','Groundnut (Pea Nut) - Summer IR','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(200,'011612105','Groundnut (Pea Nut) - Summer RF','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(201,'041317900','Grow bag','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(202,'010912200','Guar','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(203,'021012300','Guava','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(204,'010912400','Guinea Grass','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(205,'021012600','Hazlenut','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(206,'020812700','Hibiscus (Gurhal)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(207,'041324600','Hill Squash ( Chayote)','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(208,'041318900','Homestead Farming/Mixed cropping','01','Agri Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(209,'021412800','Honey Plant','02','Horti & Veg Crops','admin','2023-07-22 02:02:36','admin','2023-07-22 02:02:36'),(210,'011913000','Horse Gram (Kulthi/Kultha)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(211,'011913001','Horse Gram (Kulthi/Kultha) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(212,'022113100','Indian Bean (Vegetable)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(213,'021413200','Indian Clover (Senji Sweet Clover)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(214,'022113400','Indian Squash (Tinda/Round Melon)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(215,'021413500','Isabgol','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(216,'011513600','Italian Millet (Thenai/Navane/Foxtail Millet/Kang)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(217,'011513601','Italian Millet (Thenai/Navane/Foxtail Millet/Kang) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(218,'022113700','Ivy Gourd','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(219,'021013800','Jack Fruit','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(220,'021013900','Jamun','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(221,'020814000','Jasmine','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(222,'021814100','Jatropha (Ratanjot)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(223,'011614200','Jojoba','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(224,'020714300','Jute','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(225,'041325000','Kal Meha','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(226,'020814400','Kaner','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(227,'011614500','Karan Rai','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(228,'011914600','Khesari (Chickling Vetch/ Grass Pea)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(229,'021014700','Kinnow','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(230,'021014701','Kinnow - 10-25 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(231,'021014702','Kinnow - 5-10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(232,'021014800','Kiwi Fruit','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(233,'022114900','Knol-Khol','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(234,'011515000','Kodo Millet (Kodara/Varagu)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(235,'020515100','Kokum','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(236,'011115200','Kolanchi(Tephrosia Purpurea)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(237,'022115300','Koronda','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(238,'011515400','Korra','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(239,'022115500','Kundru','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(240,'022115600','Lab Lab','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(241,'021015700','Lahsoda','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(242,'020515800','Large Cardamom','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(243,'022115900','Leafy Vegetable','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(244,'021016000','Lehberry','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(245,'021016100','Lemon','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(246,'011916200','Lentil (Masur)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(247,'022116300','Lesser Yam (Rafula)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(248,'011916400','Lethyrus','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(249,'022116500','Lettuce','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(250,'020816600','Lilliums','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(251,'011616700','Linseed (Alsi)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(252,'011616701','Linseed (Alsi) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(253,'021016800','Litchi','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(254,'021016801','Litchi - 11 To 30 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(255,'021016802','Litchi - 51 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(256,'021031600','Litchi - Hills','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(257,'021031601','Litchi - Hills 11 To 30 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(258,'021031602','Litchi - Hills 5 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(259,'011516900','Little Millet (Samai/Kutki/Kodo-Kutki)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(260,'022117000','Lobia','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(261,'022117100','Long Melon','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(262,'021017200','Loquat','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(263,'010917300','Lucerne (Alfalfa)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(264,'011517400','Maize (Makka)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(265,'011517405','Maize (Makka) - I','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(266,'011517406','Maize (Makka) - II','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(267,'011517407','Maize (Makka) - III','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(268,'011517401','Maize (Makka) - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(269,'011517403','Maize (Makka) - Rabi','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(270,'011517402','Maize (Makka) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(271,'011517404','Maize (Makka) - Summer','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(272,'021017500','Malta','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(273,'021017502','Malta - 05 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(274,'021017501','Malta - 11 To 25 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(275,'021017600','Mango','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(276,'021017608','Mango - >5 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(277,'021017604','Mango - 05 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(278,'021017603','Mango - 10 To 40 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(279,'021017602','Mango - 16 To 50 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(280,'021017601','Mango - 5 To 15 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(281,'021017607','Mango - Hills-16 To 50 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(282,'021017606','Mango - Hills-5 To 15 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(283,'021017605','Mango - More than 40 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(284,'020817700','Marigold','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(285,'010917900','Marvel Grass','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(286,'041319800','Mehandi/Henna tree','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(287,'021018000','Melon','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(288,'021418100','Mentha','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(289,'011118200','Mesta','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(290,'020518300','Methi (Fenugreek)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(291,'021418400','Mint','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(292,'011918500','Mochai (Lab-Lab)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(293,'041320100','Moringa','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(294,'021018600','Mosambi (sweet lime)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(295,'021018602','Mosambi (sweet lime) - 11 To 25 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(296,'021018601','Mosambi (sweet lime) - 5 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(297,'021031800','Mosambi (sweet orange)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(298,'011918700','Moth Bean (Kidney Bean/ Deww Gram)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(299,'021018800','Mulberry','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(300,'041320500','Mulberry Hybrid ','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(301,'041322400','Mulberry Ratoon Crop','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(302,'041323100','Mulberry(I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(303,'041323300','Mulberry(I) Maintenance)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(304,'022118900','Mushroom','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(305,'021019000','Musk Melon','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(306,'011619100','Mustard','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(307,'021419300','Neem','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(308,'011619400','Niger (Ramtil)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(309,'020519500','Nutmeg','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(310,'010419600','Oats','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(311,'021819700','Oil Palm','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(312,'011619800','Olive','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(313,'022119900','Onion','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(314,'022119904','Onion - I','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(315,'022119905','Onion - II','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(316,'022119901','Onion - IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(317,'022119903','Onion - RF','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(318,'022119902','Onion - Summer IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(319,'020620000','Opium Popy','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(320,'021020100','Orange','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(321,'021020102','Orange - 11 To 25 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(322,'021020101','Orange - 5 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(323,'041319200','Ornamental Nursery','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(324,'041319100','Other nursery','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(325,'041318800','Other Vegetables','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(326,'010420204','Paddy - Aahu','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(327,'010420205','Paddy - Aman','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(328,'010420206','Paddy - Aus','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(329,'010420207','Paddy - Autumn','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(330,'010420208','Paddy - Boro','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(331,'010420215','Paddy - Hills','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(332,'010420203','Paddy - I','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(333,'010420212','Paddy - II','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(334,'010420214','Paddy - III','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(335,'010420201','Paddy - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(336,'010420202','Paddy - RF (UnIR)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(337,'010420209','Paddy - Sali','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(338,'010420210','Paddy - Summer','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(339,'010420213','Paddy - Summer IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(340,'010420200','Paddy (Dhan)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(341,'010420211','Paddy High Yielding Variety','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(342,'041318000','Paddy(Local)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(343,'021820300','Palmyra','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(344,'041319400','Pandal Vegetable (Panthal)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(345,'021020400','Papaya','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(346,'021020500','Passion Fruit','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(347,'011920600','Pea','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(348,'021020700','Peach','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(349,'021020706','Peach - >5 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(350,'021020702','Peach - 10 To 20 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(351,'021020701','Peach - 10 To 25 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(352,'021020703','Peach - 5 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(353,'021020704','Peach - Hills 10 To 20 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(354,'021020705','Peach - Hills 5 To 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(355,'021020800','Pear','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(356,'011501900','Pearl Millet (Bajra)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(357,'011501901','Pearl Millet (Bajra) - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(358,'011501902','Pearl Millet (Bajra) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(359,'022121000','Peas (Field Peas/ Garden Peas/Matar)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(360,'022121001','Peas (Field Peas/ Garden Peas/Matar) - Vegetables Hills','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(361,'021021100','Pecan Nut','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(362,'020521200','Pepper','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(363,'041320800','Pepper  (Rf)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(364,'041321200','Pepper (Mixed Crop)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(365,'041324800','Pepper-Maintenance','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(366,'021421300','Periwinkle','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(367,'010921400','Persian Clover','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(368,'021021500','Persimmon','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(369,'011921700','Pigeon Pea (Red Gram/Arhar/Tur)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(370,'011921701','Pigeon Pea (Red Gram/Arhar/Tur) - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(371,'011921702','Pigeon Pea (Red Gram/Arhar/Tur) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(372,'011121800','Pillipesara','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(373,'021021900','Pineapple','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(374,'021022000','Plum','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(375,'021022004','Plum - >5 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(376,'021022001','Plum - 10 to 20 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(377,'021022002','Plum - 10 to 25 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(378,'021022003','Plum - 5 to 10 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(379,'022122100','Pointed Gourd','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(380,'021022200','Pomegranate','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(381,'041321800','Pomegranate (I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(382,'041324000','Pomegranate (R) ','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(383,'041321000','Pomegranate Export Quality','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(384,'022122400','Potato','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(385,'022122403','Potato - Hills','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(386,'022122404','Potato - II','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(387,'022122401','Potato - IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(388,'022122402','Potato - RF','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(389,'022122405','Potato (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(390,'022122406','Potato (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(391,'011922600','Pulses','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(392,'022122701','Pumkin (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(393,'022122702','Pumkin (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(394,'022122700','Pumpkin','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(395,'022122900','Radish','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(396,'010923100','Rajka Bajri','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(397,'011923200','Rajma (French Bean)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(398,'011923300','Rajmash Bean','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(399,'011623400','Raya (Indian Mustard)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(400,'041318700','Red Banana','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(401,'022123500','Red Chillies','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(402,'022123501','Red Chillies - IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(403,'022123502','Red Chillies - RF','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(404,'010923600','Red Clover','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(405,'022123700','Ribbed Gourd (Kali Tori)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(406,'010431500','Rice','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(407,'011923800','Rice Fallow Black Gram','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(408,'020723900','Rice Fallow Cotton','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(409,'011924000','Rice Fallow Cow Pea','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(410,'011624100','Rice Fallow Gingelly','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(411,'011924200','Rice Fallow Green Gram','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(412,'011924300','Rice Fallow Red Gram','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(413,'010924400','Ricebean','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(414,'022124500','Ridge Gourd','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(415,'011624600','Rocket Salad (Taramira)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(416,'020824700','Rose','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(417,'020724800','Roselle (Mesta)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(418,'041319600','Rosemary (Medicine Crops)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(419,'021824900','Rubber','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(420,'041318400','Rubber','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(421,'041319000','Rubber nursery','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(422,'022125000','Runner Bean','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(423,'010925100','Ryegrass','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(424,'021425200','Safed Musli','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(425,'011625700','Safflower (Kusum/Kardi)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(426,'011625701','Safflower (Kusum/Kardi) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(427,'020525800','Saffron','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(428,'021026200','Sapota','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(429,'041321400','Sapota / Chikku Above 5 Years','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(430,'021426300','Satawar','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(431,'011526401','Save - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(432,'011526402','Save - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(433,'010926500','Sen Grass','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(434,'011626700','Sesame (Gingelly/Til)/Sesamum','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(435,'011626701','Sesame (Gingelly/Til)/Sesamum - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(436,'010926800','Setaria Grass','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(437,'041322800','Silk / Mulberry (I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(438,'022127000','Smooth Guard','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(439,'022127100','Snake Gourd','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(440,'022127101','Snake Gourd (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(441,'022127102','Snake Gourd (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(442,'022127200','Snap Melon','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(443,'011527300','Sorghum (Jowar/Great Millet)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(444,'011527301','Sorghum (Jowar/Great Millet) - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(445,'011527302','Sorghum (Jowar/Great Millet) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(446,'011627400','Soybean (Bhat)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(447,'011627401','Soybean (Bhat) - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(448,'011627402','Soybean (Bhat) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(449,'022127500','Spinach (Palak)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(450,'022127600','Spine Gourd','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(451,'022127700','Sponge Gourd','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(452,'021427800','Stevia','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(453,'021027900','Strawberry','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(454,'010928000','Stylosanthes','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(455,'010928100','Sudan Grass','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(456,'022028200','Sugar Beet','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(457,'022028300','Sugarcane - Adsali','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(458,'022028301','Sugarcane - Noble Cane','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(459,'022028304','Sugarcane - Pre-Seasonal','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(460,'022028302','Sugarcane - Ratoon','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(461,'022028303','Sugarcane - Seasonal/suru/plant','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(462,'041322200','Sugarcane Ratton(I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(463,'022128400','Summer Squash (Vegetable Marrow)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(464,'011628500','Sunflower (Suryamukhi)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(465,'011628501','Sunflower (Suryamukhi) - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(466,'011628502','Sunflower (Suryamukhi) - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(467,'011628503','Sunflower (Suryamukhi) - Summer IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(468,'011128600','Sunnhemp (Patua)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(469,'021028700','Sweet Cherry','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(470,'022128800','Sweet Potato','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(471,'010928900','Tall Fescue Grass','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(472,'041324100','Tamarind','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(473,'041324200','Tamarind (Maintenance)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(474,'022029000','Tapioca','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(475,'022029001','Tapioca - Rabi -I','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(476,'022029002','Tapioca - Rabi -II','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(477,'021829100','Tea','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(478,'010929300','Teosinte','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(479,'041325100','Thysanolaena ( Broom Plant/ Grass)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(480,'041322500','Til (R)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(481,'020629400','Tobacco','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(482,'041321600','Tobacco (I)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(483,'041321500','Tobacco (R)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(484,'022129500','Tomato','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(485,'022129503','Tomato - Hills','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(486,'022129501','Tomato - IR','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(487,'022129502','Tomato - RF','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(488,'011629600','Toria','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(489,'010429700','Triticale','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(490,'020829800','Tuberose','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(491,'041324900','Tuberose (Maintenance)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(492,'021429900','Tulsi','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(493,'022130000','Tumba','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(494,'020530200','Turmeric','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(495,'022130300','Turnip (Saljam)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(496,'020530400','Vanilla','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(497,'010930500','Velimasal','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(498,'021030600','Walnut','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(499,'041321300','Water Melon Hybrid','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(500,'022130700','Watermelon','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(501,'010430800','Wheat','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(502,'010430803','Wheat - Hills','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(503,'010430801','Wheat - IR','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(504,'010430802','Wheat - RF','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(505,'010930900','White Clover (Shaftal)','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(506,'022131100','White Yam','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(507,'011931200','Winged Bean','01','Agri Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(508,'022131300','Yard Long Bean','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(509,'022131301','Yard Long Bean (Summer)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(510,'022131302','Yard Long Bean (Winter)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(511,'020831400','Zentedeschia','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(512,'021000100','KAGADI LIMBU','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(513,'021400400','KORPHAD','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(514,'041319300','AAVALA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(515,'010919200','BAAJARI (HIRWA CHAARA)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(516,'021002000','BANANA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(517,'021002000','GENERAL BANANA KHODVA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(518,'021002000','KELI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(519,'021002000','TISSUECULTURE BANANA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(520,'021002000','TISSUECULTURE BANANA KHODVA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(521,'022102500','GHEVADA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(522,'022102500','WAAL  (KHARIP/RABBI)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(523,'022102700','BEET','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(524,'011902901','HARBHARA BAAGAYAT','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(525,'011902902','HARBHARA JIRAYAT','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(526,'021003000','BOR','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(527,'022103300','WANGI/PADWAD/BHENDI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(528,'022103600','KARLI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(529,'011903700','UDID','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(530,'022105300','10 UNIT PLOT(DHOBALI )','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(531,'022105300','20 UNIT PLOT(DHOBALI )','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(532,'022105300','5 UNIT PLOT( DHOBALI )','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(533,'022105300','DHOBLIMIRCHI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(534,'022105300','DHOBLIMIRCHI -GREENHOUSE CULTIVATION','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(535,'020805500','CORNETION','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(536,'021805700','KAJU','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(537,'022205800','YERANDI (KHARIP/RABBI)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(538,'022106400','MIRCHI  (KHARIP/RABBI)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(539,'022106400','MIRCHI (CHILI)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(540,'021807400','NARAL','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(541,'020708000','COTTON','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(542,'022108400','KAKADI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(543,'021008700','SITAFAL','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(544,'021010200','ANJIR','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(545,'011510300','NAGALI (KHARIP/RABBI)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(546,'020511000','LASUN','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(547,'020811100','JARBERA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(548,'020811100','ORCHED','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(549,'020511200','AALE','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(550,'020811300','GULCHHADI/TILI/NISHIGANDHA/GLADIOLA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(551,'021011800','GRAPES FOR EXPORT','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(552,'021011800','GRAPES FOR WINERY','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(553,'011912000','MUNG','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(554,'011612100','BHUIMUNGA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(555,'011612103','UNHALI BHUIMUNGA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(556,'021012300','PERU','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(557,'011913000','KULTHI (HULGA)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(558,'011517400','SWEET CORN MAKAA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(559,'021017600','HAPUS AMBA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(560,'022119900','KANDA - RABBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(561,'022119900','KANDA KHARIP','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(562,'021020100','MOSAMBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(563,'021020100','SANTRI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(564,'041318800','OTHER','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(565,'041318800','PALEBHAJYA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(566,'010420210','UNHALI RICE - RABBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(567,'021020400','PAPAI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(568,'011501902','UNHALI BAAJARI - RABBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(569,'011921700','TUR - IBPCL','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(570,'021022200','DALIMB - SUDHARIT','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(571,'022122400','POTATO','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(572,'022122400','POTATO - KHARIP','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(573,'022122400','POTATO - RABBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(574,'022122400','POTATO MODIFIED SEEDS -  FOR WAFERS','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(575,'010431500','HYBRID RICE - KHARIP','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(576,'010431500','MODIFIED RICE - KHARIP','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(577,'010431500','SANKARIT RICE - RABBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(578,'022124500','DODKA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(579,'020824700','CUT-FLOWERS - ROSE','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(580,'020824700','GULAB','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(581,'010925100','GAJRAAJ','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(582,'010925100','PAWANGAWAT','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(583,'011625700','KARDAI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(584,'021026200','CHIKU','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(585,'011626700','TIL','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(586,'011527300','JWARI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(587,'011527300','JWARI (HIRWA CHAARA)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(588,'011527300','JWARI (JIRAYAT SANKARIT KHARIP)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(589,'011527300','JWARI (SANKARIT -- BAAGAYAT) - RABBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(590,'011527300','JWARI (SUDHARIT JIRAYAT) - RABBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(591,'011527300','JWARI (SUDHARIT- KHARIP)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(592,'011527300','JWARI SANKARIT - KHARIP','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(593,'011627400','SOYABIN','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(594,'021027900','STRAWBERRY','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(595,'022028200','SUGAR BEET','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(596,'022028303','SURU','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(597,'011628500','SURYAFUL','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(598,'011628503','SURYAFUL (UNHALI)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(599,'012329500','CHERRY TOMATO','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(600,'012329500','TOMATO (HYBRID) KHARIP/RABBI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(601,'020530200','HALAD','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(602,'022130700','KALINGAD','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(603,'022130700','TARBUJ','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(604,'010430800','GAHU BAAGAYAT','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(605,'041319100','GARDNING HYBRID','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(606,'022028302','KHODVA','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(607,'041318800','KOBI/FLOWER','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(608,'010925100','LASUNGHHAS','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(609,'011517400','MAKAA (HIRWA CHAARA)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(610,'011517400','MAKAA HYBRID (KHARIP/RABBI)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(611,'041319100','OTHER FLOWERS (ASTAR/SHEWANTI/ZENDU)','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(612,'022028302','PURVA HANGAMI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(613,'022128800','SHABUKAND','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(614,'041322800','SOW CULTIVATION - SILK PROJECT','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(615,'041324100','SUBABHUL','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(616,'010919200','SUDHARIT(BJ) - KHARIP','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(617,'021011800','GRAPES FOR EXPORT','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(618,'021022200','DALIMB','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37'),(619,'022028300','ADSALI','02','Horti & Veg Crops','admin','2023-07-22 02:02:37','admin','2023-07-22 02:02:37');
/*!40000 ALTER TABLE `crop_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2023-07-22 11:40:34',1,'EXECUTED','8:b290fed1390e1edda6ca8efe0e33c8e6','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621132350-1','jhipster','config/liquibase/changelog/20230621132350_added_entity_StateMaster.xml','2023-07-22 11:40:34',2,'EXECUTED','8:1af51463695209523e87b707cfae5520','createTable tableName=state_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621132802-1','jhipster','config/liquibase/changelog/20230621132802_added_entity_DistrictMaster.xml','2023-07-22 11:40:34',3,'EXECUTED','8:1e1250835fb423c47c49e67083f5cc92','createTable tableName=district_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621132950-1','jhipster','config/liquibase/changelog/20230621132950_added_entity_TalukaMaster.xml','2023-07-22 11:40:34',4,'EXECUTED','8:8c5c8a81fc647d8ce2f9d77832c52ea5','createTable tableName=taluka_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621134531-1','jhipster','config/liquibase/changelog/20230621134531_added_entity_BankMaster.xml','2023-07-22 11:40:34',5,'EXECUTED','8:97a01a9fbacd679f5612a41c763fbc75','createTable tableName=bank_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621135333-1','jhipster','config/liquibase/changelog/20230621135333_added_entity_BankBranchMaster.xml','2023-07-22 11:40:34',6,'EXECUTED','8:c1e9967198ce45a6913f6f2ca6596832','createTable tableName=bank_branch_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621140146-1','jhipster','config/liquibase/changelog/20230621140146_added_entity_PacsMaster.xml','2023-07-22 11:40:34',7,'EXECUTED','8:87c446adc8b69046eee00d4fa9ee44ec','createTable tableName=pacs_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621140419-1','jhipster','config/liquibase/changelog/20230621140419_added_entity_CastCategoryMaster.xml','2023-07-22 11:40:34',8,'EXECUTED','8:948a651e5074488dd0b8bcea6191de46','createTable tableName=cast_category_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141245-1','jhipster','config/liquibase/changelog/20230621141245_added_entity_CategoryMaster.xml','2023-07-22 11:40:34',9,'EXECUTED','8:38b135676277a7c9a93b5e0f56b0a11e','createTable tableName=category_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141423-1','jhipster','config/liquibase/changelog/20230621141423_added_entity_RelativeMaster.xml','2023-07-22 11:40:34',10,'EXECUTED','8:0d7dc6a4b1f3ae0bfc8386bd4911aa1d','createTable tableName=relative_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141713-1','jhipster','config/liquibase/changelog/20230621141713_added_entity_CropMaster.xml','2023-07-22 11:40:34',11,'EXECUTED','8:82a234af0d951f7dbbdd04f4ff7d466c','createTable tableName=crop_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141827-1','jhipster','config/liquibase/changelog/20230621141827_added_entity_CropCategoryMaster.xml','2023-07-22 11:40:34',12,'EXECUTED','8:c31c7d4437452d83b81d193b2c4da79c','createTable tableName=crop_category_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621142059-1','jhipster','config/liquibase/changelog/20230621142059_added_entity_OccupationMaster.xml','2023-07-22 11:40:34',13,'EXECUTED','8:3d7e7b45e2ea70276566fbad8e41da0b','createTable tableName=occupation_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621142408-1','jhipster','config/liquibase/changelog/20230621142408_added_entity_DesignationMaster.xml','2023-07-22 11:40:34',14,'EXECUTED','8:9d2224d79bea074a14dba54cba677bf9','createTable tableName=designation_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230710064404-1','jhipster','config/liquibase/changelog/20230710064404_added_entity_IssFileParser.xml','2023-07-22 11:40:34',15,'EXECUTED','8:58ec7dcbe0c886b9b8eff839a3889319','createTable tableName=iss_file_parser','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141423-1','jhipster','config/liquibase/changelog/20230621141423_added_entity_AccountHolderMaster.xml','2023-07-22 11:40:34',16,'EXECUTED','8:8dc1b5b5616f3ac71b02d2b1dbefbeeb','createTable tableName=account_holder_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141423-1','jhipster','config/liquibase/changelog/20230621141423_added_entity_FarmerCategoryMaster.xml','2023-07-22 11:40:34',17,'EXECUTED','8:75486b355a090f9268e29e6203ff1cf7','createTable tableName=farmer_category_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141423-1','jhipster','config/liquibase/changelog/20230621141423_added_entity_FarmerTypeMaster.xml','2023-07-22 11:40:34',18,'EXECUTED','8:71f8bd1cf9015951465b0949e5f71dc9','createTable tableName=farmer_type_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141423-1','jhipster','config/liquibase/changelog/20230621141423_added_entity_LandTypeMaster.xml','2023-07-22 11:40:34',19,'EXECUTED','8:a9f1ad883146dcec61ddd99e645f6ab2','createTable tableName=land_type_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230621141423-1','jhipster','config/liquibase/changelog/20230621141423_added_entity_SeasonMaster.xml','2023-07-22 11:40:34',20,'EXECUTED','8:03b54879dcdce9b49b6f92d26d0f1013','createTable tableName=season_master','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230711043833-1','jhipster','config/liquibase/changelog/20230711043833_added_entity_IssPortalFile.xml','2023-07-22 11:40:34',21,'EXECUTED','8:114c32d712cd3495a883a9cfc1d33828','createTable tableName=iss_portal_file','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230712043847-1','jhipster','config/liquibase/changelog/20230712043847_added_entity_BatchTransaction.xml','2023-07-22 11:40:34',22,'EXECUTED','8:1484534fb4a98470989ac7b6dc99157d','createTable tableName=batch_transaction','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230712044312-1','jhipster','config/liquibase/changelog/20230712044312_added_entity_ApplicationLog.xml','2023-07-22 11:40:34',23,'EXECUTED','8:c7c5d7b3ec1c975fa61c723d70c4bf5a','createTable tableName=application_log','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230712045701-1','jhipster','config/liquibase/changelog/20230712045701_added_entity_ApplicationLogHistory.xml','2023-07-22 11:40:34',24,'EXECUTED','8:221f92ab03fb6c9078f9cfe43d83dc40','createTable tableName=application_log_history','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230712051056-1','jhipster','config/liquibase/changelog/20230712051056_added_entity_Application.xml','2023-07-22 11:40:34',25,'EXECUTED','8:78870e8266ac71df54ff7821904e180c','createTable tableName=application_transaction','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230718120922-1','jhipster','config/liquibase/changelog/20230718120922_added_entity_ActivityType.xml','2023-07-22 11:40:34',26,'EXECUTED','8:3f3c58b7dd62582d0d28c6bd97552002','createTable tableName=activity_type','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230712051569-1','jhipster','config/liquibase/changelog/20230712051569_added_entity_FinancialYear.xml','2023-07-22 11:40:35',27,'EXECUTED','8:33d7fde0051879ddb22bf5dceb794324','createTable tableName=financial_year','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230718120759-1','jhipster','config/liquibase/changelog/20230718120759_added_entity_permission.xml','2023-07-22 11:40:35',28,'EXECUTED','8:141033e487bf8cf7c686f98204a30959','createTable tableName=permission','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230710064404-2','jhipster','config/liquibase/changelog/20230710064404_added_entity_constraints_IssFileParser.xml','2023-07-22 11:40:35',29,'EXECUTED','8:8734f24e075562dda2a37dde1c2ce0c9','addForeignKeyConstraint baseTableName=iss_file_parser, constraintName=fk_iss_file_parser__iss_portal_file_id, referencedTableName=iss_portal_file','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230712044312-2','jhipster','config/liquibase/changelog/20230712044312_added_entity_constraints_ApplicationLog.xml','2023-07-22 11:40:35',30,'EXECUTED','8:8ee53c7b8ad14a95d693ea330906172b','addForeignKeyConstraint baseTableName=application_log, constraintName=fk_application_log__iss_file_parser_id, referencedTableName=iss_file_parser','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230712045701-2','jhipster','config/liquibase/changelog/20230712045701_added_entity_constraints_ApplicationLogHistory.xml','2023-07-22 11:40:35',31,'EXECUTED','8:ed8308acdf5811faa6ed87eb4364fc76','addForeignKeyConstraint baseTableName=application_log_history, constraintName=fk_application_log_history__iss_file_parser_id, referencedTableName=iss_file_parser','',NULL,'4.15.0',NULL,NULL,'0006233584'),('20230712051056-2','jhipster','config/liquibase/changelog/20230712051056_added_entity_constraints_Application.xml','2023-07-22 11:40:35',32,'EXECUTED','8:9a76790cc1c71112dbe884815e29b8c3','addForeignKeyConstraint baseTableName=application_transaction, constraintName=fk_application__iss_file_parser_id, referencedTableName=iss_file_parser','',NULL,'4.15.0',NULL,NULL,'0006233584');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `designation_master`
--

DROP TABLE IF EXISTS `designation_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `designation_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `designation_code` varchar(255) DEFAULT NULL,
  `designation_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `designation_master`
--

LOCK TABLES `designation_master` WRITE;
/*!40000 ALTER TABLE `designation_master` DISABLE KEYS */;
INSERT INTO `designation_master` VALUES (1,'22072315639008',NULL,'admin','2023-07-22 00:51:51','admin','2023-07-22 00:51:51'),(2,'22072315636030',NULL,'admin','2023-07-22 01:15:36','admin','2023-07-22 01:15:36'),(3,'22072315611577',NULL,'admin','2023-07-22 01:17:44','admin','2023-07-22 01:17:44'),(4,'22072315618666',NULL,'admin','2023-07-22 01:19:56','admin','2023-07-22 01:19:56'),(5,'22072315699943',NULL,'admin','2023-07-22 01:21:15','admin','2023-07-22 01:21:15'),(6,'22072315642919',NULL,'admin','2023-07-22 01:25:12','admin','2023-07-22 01:25:12'),(7,'22072315621425',NULL,'admin','2023-07-22 01:43:28','admin','2023-07-22 01:43:28'),(8,'22072315662244',NULL,'admin','2023-07-22 02:02:50','admin','2023-07-22 02:02:50'),(9,'22072315648747',NULL,'admin','2023-07-22 02:03:00','admin','2023-07-22 02:03:00'),(10,'22072315614887',NULL,'admin','2023-07-22 02:03:12','admin','2023-07-22 02:03:12'),(11,'22072315653413',NULL,'admin','2023-07-22 02:10:29','admin','2023-07-22 02:10:29'),(12,'22072315639542',NULL,'admin','2023-07-22 02:11:02','admin','2023-07-22 02:11:02');
/*!40000 ALTER TABLE `designation_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `district_master`
--

DROP TABLE IF EXISTS `district_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `district_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `district_code` varchar(255) DEFAULT NULL,
  `district_name` varchar(255) DEFAULT NULL,
  `state_code` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `district_master`
--

LOCK TABLES `district_master` WRITE;
/*!40000 ALTER TABLE `district_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `district_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farmer_category_master`
--

DROP TABLE IF EXISTS `farmer_category_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farmer_category_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `farmer_category_code` int DEFAULT NULL,
  `farmer_category` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farmer_category_master`
--

LOCK TABLES `farmer_category_master` WRITE;
/*!40000 ALTER TABLE `farmer_category_master` DISABLE KEYS */;
INSERT INTO `farmer_category_master` VALUES (1,1,'OWNER','admin',NULL,NULL,NULL),(2,2,'SHARECROPPER','admin',NULL,NULL,NULL),(3,3,'TENANT','admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `farmer_category_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `farmer_type_master`
--

DROP TABLE IF EXISTS `farmer_type_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `farmer_type_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `farmer_type_code` int DEFAULT NULL,
  `farmer_type` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `farmer_type_master`
--

LOCK TABLES `farmer_type_master` WRITE;
/*!40000 ALTER TABLE `farmer_type_master` DISABLE KEYS */;
INSERT INTO `farmer_type_master` VALUES (1,1,'SMALL','admin',NULL,NULL,NULL),(2,2,'OTHER','admin',NULL,NULL,NULL),(3,3,'MARGINAL','admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `farmer_type_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `financial_year`
--

DROP TABLE IF EXISTS `financial_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `financial_year` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `financial_year` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `financial_year`
--

LOCK TABLES `financial_year` WRITE;
/*!40000 ALTER TABLE `financial_year` DISABLE KEYS */;
/*!40000 ALTER TABLE `financial_year` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iss_file_parser`
--

DROP TABLE IF EXISTS `iss_file_parser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iss_file_parser` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `financial_year` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `bank_code` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `branch_code` varchar(255) DEFAULT NULL,
  `scheme_wise_branch_code` varchar(255) DEFAULT NULL,
  `ifsc` varchar(255) DEFAULT NULL,
  `loan_account_numberkcc` varchar(255) DEFAULT NULL,
  `farmer_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `aadhar_number` varchar(255) DEFAULT NULL,
  `dateof_birth` varchar(255) DEFAULT NULL,
  `age_at_time_of_sanction` varchar(255) DEFAULT NULL,
  `mobile_no` varchar(255) DEFAULT NULL,
  `farmers_category` varchar(255) DEFAULT NULL,
  `farmer_type` varchar(255) DEFAULT NULL,
  `social_category` varchar(255) DEFAULT NULL,
  `relative_type` varchar(255) DEFAULT NULL,
  `relative_name` varchar(255) DEFAULT NULL,
  `state_name` varchar(255) DEFAULT NULL,
  `state_code` varchar(255) DEFAULT NULL,
  `district_name` varchar(255) DEFAULT NULL,
  `district_code` varchar(255) DEFAULT NULL,
  `block_code` varchar(255) DEFAULT NULL,
  `block_name` varchar(255) DEFAULT NULL,
  `village_code` varchar(255) DEFAULT NULL,
  `village_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `pin_code` varchar(255) DEFAULT NULL,
  `account_type` varchar(255) DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `pacs_name` varchar(255) DEFAULT NULL,
  `pacs_number` varchar(255) DEFAULT NULL,
  `account_holder_type` varchar(255) DEFAULT NULL,
  `primary_occupation` varchar(255) DEFAULT NULL,
  `loan_saction_date` varchar(255) DEFAULT NULL,
  `loan_sanction_amount` varchar(255) DEFAULT NULL,
  `tenure_of_loan` varchar(255) DEFAULT NULL,
  `date_of_over_due_payment` varchar(255) DEFAULT NULL,
  `crop_name` varchar(255) DEFAULT NULL,
  `survey_no` varchar(255) DEFAULT NULL,
  `sat_bara_subsurvey_no` varchar(255) DEFAULT NULL,
  `season_name` varchar(255) DEFAULT NULL,
  `area_hect` varchar(255) DEFAULT NULL,
  `land_type` varchar(255) DEFAULT NULL,
  `disbursement_date` varchar(255) DEFAULT NULL,
  `disburse_amount` varchar(255) DEFAULT NULL,
  `maturity_loan_date` varchar(255) DEFAULT NULL,
  `recovery_amount_principle` varchar(255) DEFAULT NULL,
  `recovery_amount_interest` varchar(255) DEFAULT NULL,
  `recovery_date` varchar(255) DEFAULT NULL,
  `iss_portal_file_id` bigint DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_iss_file_parser__iss_portal_file_id` (`iss_portal_file_id`),
  CONSTRAINT `fk_iss_file_parser__iss_portal_file_id` FOREIGN KEY (`iss_portal_file_id`) REFERENCES `iss_portal_file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iss_file_parser`
--

LOCK TABLES `iss_file_parser` WRITE;
/*!40000 ALTER TABLE `iss_file_parser` DISABLE KEYS */;
/*!40000 ALTER TABLE `iss_file_parser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iss_portal_file`
--

DROP TABLE IF EXISTS `iss_portal_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iss_portal_file` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) DEFAULT NULL,
  `file_extension` varchar(255) DEFAULT NULL,
  `unique_name` varchar(255) DEFAULT NULL,
  `branch_code` bigint DEFAULT NULL,
  `financial_year` varchar(255) DEFAULT NULL,
  `from_disbursement_date` date DEFAULT NULL,
  `to_disbursement_date` date DEFAULT NULL,
  `pacs_code` bigint DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `application_count` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `pacs_name` varchar(255) DEFAULT NULL,
  `error_record_count` bigint DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iss_portal_file`
--

LOCK TABLES `iss_portal_file` WRITE;
/*!40000 ALTER TABLE `iss_portal_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `iss_portal_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_BRANCH_ADMIN'),('ROLE_BRANCH_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(10) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `bank_code` varchar(100) DEFAULT NULL,
  `branch_code` varchar(100) DEFAULT NULL,
  `branch_name` varchar(100) DEFAULT NULL,
  `pacs_name` varchar(100) DEFAULT NULL,
  `pacs_number` varchar(100) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','',_binary '','en',NULL,NULL,'156',NULL,'KALAS',NULL,NULL,'system',NULL,NULL,'system',NULL),(2,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',_binary '','en',NULL,NULL,'156','198','KALAS',NULL,NULL,'system',NULL,NULL,'system',NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_BRANCH_ADMIN');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `land_type_master`
--

DROP TABLE IF EXISTS `land_type_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `land_type_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `land_type_code` int DEFAULT NULL,
  `land_type` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `land_type_master`
--

LOCK TABLES `land_type_master` WRITE;
/*!40000 ALTER TABLE `land_type_master` DISABLE KEYS */;
INSERT INTO `land_type_master` VALUES (1,1,'IRRIGATED','admin',NULL,NULL,NULL),(2,2,'NON-IRRIGATED','admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `land_type_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `occupation_master`
--

DROP TABLE IF EXISTS `occupation_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `occupation_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `occupation_code` int DEFAULT NULL,
  `occupation_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `occupation_master`
--

LOCK TABLES `occupation_master` WRITE;
/*!40000 ALTER TABLE `occupation_master` DISABLE KEYS */;
INSERT INTO `occupation_master` VALUES (1,1,'FARMER','admin',NULL,NULL,NULL),(2,2,'FISHRIES','admin',NULL,NULL,NULL),(3,3,'ANIMAL HUSBANDARY','admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `occupation_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pacs_master`
--

DROP TABLE IF EXISTS `pacs_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pacs_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `pacs_name` varchar(255) DEFAULT NULL,
  `pacs_number` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pacs_master`
--

LOCK TABLES `pacs_master` WRITE;
/*!40000 ALTER TABLE `pacs_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `pacs_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bank_object` varchar(255) NOT NULL,
  `permission` varchar(255) NOT NULL,
  `action` varchar(255) NOT NULL,
  `bank_role` varchar(255) NOT NULL,
  `scope` varchar(255) NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'USER','YES','CREATE','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(2,'USER','YES','CREATE','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(3,'USER','YES','EDIT','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(4,'USER','YES','EDIT','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(5,'USER','YES','EDIT','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(6,'USER','YES','VIEW','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(7,'USER','YES','VIEW','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(8,'USER','YES','VIEW','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(9,'USER','YES','DELETE','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(10,'USER','YES','DELETE','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(11,'FILE_VALIDATE','YES','VALIDATE','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(12,'FILE_VALIDATE','YES','VALIDATE','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(13,'FILE_VALIDATE','YES','VALIDATE','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(14,'FILE_DOWNLOAD','YES','DOWNLOAD','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(15,'FILE_DOWNLOAD','YES','DOWNLOAD','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(16,'FILE_DOWNLOAD','YES','DOWNLOAD','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(17,'FILE_UPLOAD','YES','UPLOAD','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(18,'FILE_UPLOAD','YES','UPLOAD','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(19,'FILE_UPLOAD','YES','UPLOAD','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(20,'SUBMIT_BATCH','YES','SUBMIT','ROLE_ADMIN','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(21,'SUBMIT_BATCH','YES','SUBMIT','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(22,'SUBMIT_BATCH','NO','SUBMIT','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(23,'UPDATE_RECORD','YES','EDIT','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(24,'UPDATE_RECORD','YES','EDIT','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(25,'UPDATE_RECORD','YES','EDIT','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(26,'VIEW_RECORD','YES','VIEW','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(27,'VIEW_RECORD','YES','VIEW','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(28,'VIEW_RECORD','YES','VIEW','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(29,'DELETE_RECORD','YES','DELETE','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(30,'DELETE_RECORD','YES','DELETE','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(31,'DELETE_RECORD','NO','DELETE','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(32,'CHANGE_PASSWORD','YES','EDIT','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(33,'CHANGE_PASSWORD','YES','EDIT','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(34,'CHANGE_PASSWORD','YES','EDIT','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(35,'RESET_PASSWORD','YES','EDIT','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(36,'RESET_PASSWORD','YES','EDIT','ROLE_BRANCH_ADMIN','OWN','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(37,'RESET_PASSWORD','YES','EDIT','ROLE_BRANCH_USER','SELF','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(38,'MASTER_RECORD_UPDATE','YES','EDIT','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(39,'MASTER_RECORD_UPDATE','YES','EDIT','ROLE_BRANCH_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(40,'MASTER_RECORD_UPDATE','NO','EDIT','ROLE_BRANCH_USER','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(41,'MASTER_RECORD_DELETE','YES','DELETE','ROLE_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(42,'MASTER_RECORD_DELETE','YES','DELETE','ROLE_BRANCH_ADMIN','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39'),(43,'MASTER_RECORD_DELETE','NO','DELETE','ROLE_BRANCH_USER','ALL','admin','2023-07-22 00:50:39','admin','2023-07-22 00:50:39');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relative_master`
--

DROP TABLE IF EXISTS `relative_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relative_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `relative_code` int DEFAULT NULL,
  `relative_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relative_master`
--

LOCK TABLES `relative_master` WRITE;
/*!40000 ALTER TABLE `relative_master` DISABLE KEYS */;
INSERT INTO `relative_master` VALUES (1,1,'SON OF','admin',NULL,NULL,NULL),(2,2,'DAUGHTER OF','admin',NULL,NULL,NULL),(3,3,'CARE OF','admin',NULL,NULL,NULL),(4,4,'WIFE OF','admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `relative_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `season_master`
--

DROP TABLE IF EXISTS `season_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `season_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `season_code` int DEFAULT NULL,
  `season_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `season_master`
--

LOCK TABLES `season_master` WRITE;
/*!40000 ALTER TABLE `season_master` DISABLE KEYS */;
INSERT INTO `season_master` VALUES (1,1,'KHARIF','admin',NULL,NULL,NULL),(2,2,'RABI','admin',NULL,NULL,NULL),(3,3,'SUMMER/ZAID/OTHERS','admin',NULL,NULL,NULL),(4,3,'HORTICULTURE','admin',NULL,NULL,NULL),(5,3,'SUGARCANE','admin',NULL,NULL,NULL);
/*!40000 ALTER TABLE `season_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state_master`
--

DROP TABLE IF EXISTS `state_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `state_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `state_code` varchar(255) DEFAULT NULL,
  `state_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state_master`
--

LOCK TABLES `state_master` WRITE;
/*!40000 ALTER TABLE `state_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `state_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taluka_master`
--

DROP TABLE IF EXISTS `taluka_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taluka_master` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `taluka_code` varchar(255) DEFAULT NULL,
  `taluka_name` varchar(255) DEFAULT NULL,
  `district_code` varchar(255) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taluka_master`
--

LOCK TABLES `taluka_master` WRITE;
/*!40000 ALTER TABLE `taluka_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `taluka_master` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-22 13:26:07
