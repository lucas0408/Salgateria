CREATE DATABASE  IF NOT EXISTS `banco_salgateria1` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `banco_salgateria1`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: banco_salgateria1
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
-- Table structure for table `estoque_has_item`
--

DROP TABLE IF EXISTS `estoque_has_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estoque_has_item` (
  `item_id` int NOT NULL,
  `consumo` float NOT NULL,
  `insumo_id` int NOT NULL,
  PRIMARY KEY (`insumo_id`,`item_id`),
  KEY `fk_estoque_has_item_item1` (`item_id`),
  CONSTRAINT `fk_estoque_has_item_insumo` FOREIGN KEY (`insumo_id`) REFERENCES `insumo` (`id`),
  CONSTRAINT `fk_estoque_has_item_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_estoque_has_item_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_item_estoque` FOREIGN KEY (`insumo_id`) REFERENCES `insumo` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estoque_has_item`
--

LOCK TABLES `estoque_has_item` WRITE;
/*!40000 ALTER TABLE `estoque_has_item` DISABLE KEYS */;
INSERT INTO `estoque_has_item` VALUES (28,0.8,17),(33,2,17),(28,1,23),(33,1,23),(28,0.1,24),(28,0.67,25),(28,0.012,26),(28,0.05,27),(30,0.012,27),(30,0.5,28),(30,0.5,29),(33,3,30),(33,0.3,31);
/*!40000 ALTER TABLE `estoque_has_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insumo`
--

DROP TABLE IF EXISTS `insumo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insumo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `estoque_minimo` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insumo`
--

LOCK TABLES `insumo` WRITE;
/*!40000 ALTER TABLE `insumo` DISABLE KEYS */;
INSERT INTO `insumo` VALUES (17,'FARINHA DE TRIGO','FARINHA',5),(18,'MILHO','MILHO',15),(20,'FARINHA DE ROSCA','FARINHA',20),(23,'LEITE','LACTIO',10),(24,'TOMATE','VEGETAL',3),(25,'FRANGO','CARNE',4),(26,'SAZON','TEMPERO',0.8),(27,'CALDO DE GALINHA EM PÓ','TEMPERO',0.4),(28,'TRIGO PARA KIBE','FARINHA',3),(29,'CARNE MOIDA PATINHO','CARNE',5),(30,'OVO (UN)','A DEFINIR',20),(31,'QUEIJO','LACTIO',1);
/*!40000 ALTER TABLE `insumo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `tipo` varchar(45) NOT NULL,
  `preco` float NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome_UNIQUE` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (28,'COXINHA','SALGADO',50),(30,'KIBE','SALGADO',50),(33,'RISOLE','SALGADO',50);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_estoque`
--

DROP TABLE IF EXISTS `item_estoque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_estoque` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_validade` date NOT NULL,
  `quantidade` float DEFAULT NULL,
  `insumo_id` int NOT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`,`insumo_id`),
  KEY `fk_estoque_insumo1_idx` (`insumo_id`),
  CONSTRAINT `fk_estoque_insumo1` FOREIGN KEY (`insumo_id`) REFERENCES `insumo` (`id`) ON DELETE CASCADE,
  CONSTRAINT `insumo_id` FOREIGN KEY (`insumo_id`) REFERENCES `insumo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_estoque`
--

LOCK TABLES `item_estoque` WRITE;
/*!40000 ALTER TABLE `item_estoque` DISABLE KEYS */;
INSERT INTO `item_estoque` VALUES (47,'2025-06-25',19.904,17,99.52),(48,'2024-08-21',19.88,23,298.20),(49,'2024-06-19',19.988,24,199.88),(50,'2024-06-10',20.4196,25,408.39),(51,'2024-08-21',0.49856,26,19.94),(52,'2024-06-11',1.994,27,29.91),(53,'2024-07-24',5,28,45.00),(54,'2024-06-18',20,29,500.00),(55,'2024-06-25',40,30,20.00),(56,'2024-06-11',0.5,25,10.00),(57,'2024-07-02',10,31,50.00);
/*!40000 ALTER TABLE `item_estoque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_pedido`
--

DROP TABLE IF EXISTS `item_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_pedido` (
  `quantidade` int NOT NULL,
  `pedido_id` int NOT NULL,
  `item_id` int NOT NULL,
  PRIMARY KEY (`pedido_id`,`item_id`),
  KEY `fk_item_pedido_item1_idx` (`item_id`),
  CONSTRAINT `fk_item_pedido_item1` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  CONSTRAINT `fk_item_pedido_pedido1` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_pedido`
--

LOCK TABLES `item_pedido` WRITE;
/*!40000 ALTER TABLE `item_pedido` DISABLE KEYS */;
INSERT INTO `item_pedido` VALUES (12,6,28),(10000,15,28),(100,18,28),(100,18,30),(100,18,33),(30,19,28),(30,19,30),(30,19,33);
/*!40000 ALTER TABLE `item_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data` date NOT NULL,
  `horario` time NOT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `pagamento` varchar(45) NOT NULL,
  `logradouro` varchar(45) NOT NULL,
  `numero` int NOT NULL,
  `nome_cliente` varchar(200) NOT NULL,
  `ativo` tinyint(1) DEFAULT NULL,
  `custo` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,'2024-06-01','16:30:00',7.50,'CARTÃO','sdadsa',2,'saddsa',0,2.40),(6,'2024-06-01','23:30:00',13.50,'CARTÃO','av dos alpes',2,'marcela',0,4.25),(7,'2024-06-07','13:30:00',13.00,'CARTÃO','teste',234,'teste',0,3.50),(8,'2024-06-05','11:14:00',11.50,'CARTÃO','rua teste',234,'joão',0,8.36),(9,'2024-06-07','13:30:00',50.00,'CARTÃO','teste',234,'pedro',0,49.45),(10,'2024-06-07','13:30:00',52.50,'CARTÃO','teste',234,'pedro',0,18.03),(11,'2024-06-07','13:30:00',50.00,'CARTÃO','teste',234,'larissa',0,28.62),(12,'2024-06-07','13:30:00',50.00,'CARTÃO','test',234,'pedro',0,27.12),(13,'2024-06-07','13:30:00',15.00,'CARTÃO','av. Alda',234,'pedro',0,3.45),(14,'2024-06-07','13:30:00',35.00,'CARTÃO','rua ameixeira',234,'Luana',0,15.05),(15,'2024-06-13','22:45:00',5000.00,'CARTÃO','av. ameixeira',122,'paulo',1,0.00),(16,'2024-06-13','22:45:00',6.00,'CARTÃO','fddsdfs',122,'fdsfds',0,1.57),(17,'2024-06-10','22:45:00',6.00,'DINHEIRO','r. alda',365,'felie',0,4.16),(18,'2024-06-11','14:50:00',150.00,'CARTÃO','av peixoto',13,'luiz',1,0.00),(19,'2024-06-12','16:40:00',45.00,'PIX','R. larissa lispector',345,'larissa',1,0.00);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `cpf` varchar(14) COLLATE utf8mb4_general_ci NOT NULL,
  `nome` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `senha` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `fone` varchar(15) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cep` varchar(9) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `logradouro` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `numero` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bairro` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cidade` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `uf` char(2) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('333.333.333-33','joana','11111','1997-06-19','(11) 11111-1111','22222-222','r alda','12','jardins','são paulo','sp'),('476.000.000-00','paula patricio','1234','1981-06-19','(11) 11111-1111','99999-999','rua patricio','12','jardim','são paulo','sp'),('476.666.666-66','julia','3333','2021-06-17','(44) 44444-4444','09999-999','r. alda','34','jardins','são paulo','sp'),('476.908.688-19','João Pedro da Silva Gomes','joao200373','2002-11-05','(11) 98525-6505','04475-190','Rua das Pargos','326','Jardim Célia','São Paulo','SP'),('777.777.777-77','paulo','3333','2002-06-06','(55) 55555-5555','44444-444','av julia','12','jardins','são paulo','sp');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-09 18:16:13
