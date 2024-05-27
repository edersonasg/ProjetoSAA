-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: seu_banco_de_dados
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cassetes`
--

DROP TABLE IF EXISTS `cassetes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cassetes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cassete` varchar(20) NOT NULL,
  `nivel` varchar(10) NOT NULL,
  `estado` enum('Vazio','Ocupado') NOT NULL DEFAULT 'Vazio',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cassete` (`cassete`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cassetes`
--

LOCK TABLES `cassetes` WRITE;
/*!40000 ALTER TABLE `cassetes` DISABLE KEYS */;
INSERT INTO `cassetes` VALUES (1,'Cassete 1','Nível A','Vazio'),(2,'Cassete 2','Nível A','Vazio'),(3,'Cassete 3','Nível A','Vazio'),(4,'Cassete 4','Nível A','Vazio'),(5,'Cassete 5','Nível A','Vazio'),(6,'Cassete 6','Nível A','Vazio'),(7,'Cassete 7','Nível A','Vazio'),(8,'Cassete 8','Nível A','Vazio'),(9,'Cassete 9','Nível A','Vazio'),(10,'Cassete 10','Nível A','Vazio'),(11,'Cassete 11','Nível A','Vazio'),(12,'Cassete 12','Nível A','Vazio'),(13,'Cassete 13','Nível A','Vazio'),(14,'Cassete 14','Nível A','Vazio'),(15,'Cassete 15','Nível A','Vazio'),(16,'Cassete 16','Nível A','Vazio'),(17,'Cassete 17','Nível A','Vazio'),(18,'Cassete 18','Nível A','Vazio'),(19,'Cassete 19','Nível A','Vazio'),(20,'Cassete 20','Nível A','Vazio'),(21,'Cassete 21','Nível A','Vazio'),(22,'Cassete 22','Nível A','Vazio'),(23,'Cassete 23','Nível A','Vazio'),(24,'Cassete 24','Nível A','Vazio'),(25,'Cassete 25','Nível A','Vazio'),(26,'Cassete 26','Nível B','Ocupado'),(27,'Cassete 27','Nível B','Vazio'),(28,'Cassete 28','Nível B','Vazio'),(29,'Cassete 29','Nível B','Vazio'),(30,'Cassete 30','Nível B','Vazio'),(31,'Cassete 31','Nível B','Vazio'),(32,'Cassete 32','Nível B','Vazio'),(33,'Cassete 33','Nível B','Vazio'),(34,'Cassete 34','Nível B','Vazio'),(35,'Cassete 35','Nível B','Vazio'),(36,'Cassete 36','Nível B','Vazio'),(37,'Cassete 37','Nível B','Vazio'),(38,'Cassete 38','Nível B','Vazio'),(39,'Cassete 39','Nível B','Vazio'),(40,'Cassete 40','Nível B','Vazio'),(41,'Cassete 41','Nível B','Vazio'),(42,'Cassete 42','Nível B','Vazio'),(43,'Cassete 43','Nível B','Vazio'),(44,'Cassete 44','Nível B','Vazio'),(45,'Cassete 45','Nível B','Vazio'),(46,'Cassete 46','Nível B','Vazio'),(47,'Cassete 47','Nível B','Vazio'),(48,'Cassete 48','Nível B','Vazio'),(49,'Cassete 49','Nível B','Vazio'),(50,'Cassete 50','Nível B','Vazio'),(51,'Cassete 51','Nível C','Vazio'),(52,'Cassete 52','Nível C','Vazio'),(53,'Cassete 53','Nível C','Vazio'),(54,'Cassete 54','Nível C','Vazio'),(55,'Cassete 55','Nível C','Vazio'),(56,'Cassete 56','Nível C','Vazio'),(57,'Cassete 57','Nível C','Vazio'),(58,'Cassete 58','Nível C','Vazio'),(59,'Cassete 59','Nível C','Vazio'),(60,'Cassete 60','Nível C','Vazio'),(61,'Cassete 61','Nível C','Vazio'),(62,'Cassete 62','Nível C','Vazio'),(63,'Cassete 63','Nível C','Vazio'),(64,'Cassete 64','Nível C','Vazio'),(65,'Cassete 65','Nível C','Vazio'),(66,'Cassete 66','Nível C','Vazio'),(67,'Cassete 67','Nível C','Vazio'),(68,'Cassete 68','Nível C','Vazio'),(69,'Cassete 69','Nível C','Vazio'),(70,'Cassete 70','Nível C','Vazio'),(71,'Cassete 71','Nível C','Vazio'),(72,'Cassete 72','Nível C','Vazio'),(73,'Cassete 73','Nível C','Vazio'),(74,'Cassete 74','Nível C','Vazio'),(75,'Cassete 75','Nível C','Vazio'),(76,'Cassete 76','Nível D','Vazio'),(77,'Cassete 77','Nível D','Vazio'),(78,'Cassete 78','Nível D','Vazio'),(79,'Cassete 79','Nível D','Vazio'),(80,'Cassete 80','Nível D','Vazio'),(81,'Cassete 81','Nível D','Vazio'),(82,'Cassete 82','Nível D','Vazio'),(83,'Cassete 83','Nível D','Vazio'),(84,'Cassete 84','Nível D','Vazio'),(85,'Cassete 85','Nível D','Vazio'),(86,'Cassete 86','Nível D','Vazio'),(87,'Cassete 87','Nível D','Vazio'),(88,'Cassete 88','Nível D','Vazio'),(89,'Cassete 89','Nível D','Vazio'),(90,'Cassete 90','Nível D','Vazio'),(91,'Cassete 91','Nível D','Vazio'),(92,'Cassete 92','Nível D','Vazio'),(93,'Cassete 93','Nível D','Vazio'),(94,'Cassete 94','Nível D','Vazio'),(95,'Cassete 95','Nível D','Vazio'),(96,'Cassete 96','Nível D','Vazio'),(97,'Cassete 97','Nível D','Vazio'),(98,'Cassete 98','Nível D','Vazio'),(99,'Cassete 99','Nível D','Vazio'),(100,'Cassete 100','Nível D','Vazio');
/*!40000 ALTER TABLE `cassetes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-24 17:59:33
