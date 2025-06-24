-- MySQL dump 10.13  Distrib 9.3.0, for macos15.4 (arm64)
--
-- Host: 127.0.0.1    Database: cookbook
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_unique` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Jiachangcai');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_recipe`
--

DROP TABLE IF EXISTS `category_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_recipe` (
  `category_id` int NOT NULL,
  `recipe_id` int NOT NULL,
  PRIMARY KEY (`category_id`,`recipe_id`),
  KEY `category_recipe_recipe_FK` (`recipe_id`),
  CONSTRAINT `category_recipe_category_FK` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `category_recipe_recipe_FK` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_recipe`
--

LOCK TABLES `category_recipe` WRITE;
/*!40000 ALTER TABLE `category_recipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredient` (
  `pair_id` int NOT NULL AUTO_INCREMENT,
  `recipe_id` int NOT NULL,
  `ingredient_name` varchar(100) NOT NULL,
  `ingredient_amount` int NOT NULL,
  `unit` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`pair_id`),
  KEY `ingredient_recipe_FK` (`recipe_id`),
  CONSTRAINT `ingredient_recipe_FK` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`recipe_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient`
--

LOCK TABLES `ingredient` WRITE;
/*!40000 ALTER TABLE `ingredient` DISABLE KEYS */;
/*!40000 ALTER TABLE `ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipe` (
  `recipe_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `prep_time` int NOT NULL,
  `cook_time` int NOT NULL,
  `instruction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `img_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `serve` int NOT NULL,
  PRIMARY KEY (`recipe_id`),
  UNIQUE KEY `recipe_unique_1` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'cookbook'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-24 10:24:23
