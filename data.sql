-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: cauhoi
-- ------------------------------------------------------
-- Server version	8.4.0

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
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `class_id` varchar(255) NOT NULL,
  `image_class` varchar(255) DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES ('f453d','https://i.imgur.com/SHrpSc1.jpeg',NULL,'c#');
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history` (
  `history_id` varchar(255) NOT NULL,
  `answers` varchar(255) DEFAULT NULL,
  `completrd` varchar(255) DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  `questions_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `test_id` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`history_id`),
  KEY `FKc5qpyp1n9rwwcvei0k7j6mtxt` (`questions_id`),
  CONSTRAINT `FKc5qpyp1n9rwwcvei0k7j6mtxt` FOREIGN KEY (`questions_id`) REFERENCES `question` (`questions_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES ('0465c44c-6896-4a93-9e12-9b936c567778','Allows a method to be overridden in derived classes',NULL,'2.50','e1b56648-d9a3-4060-be54-f820f3f75960','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('06c0ca4d-ab7a-4983-9bec-e5af9ed28722','ref and out',NULL,'2.50','ddf84773-541b-4ad5-a528-4b182501360c','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('0cc93235-f546-4498-b2c2-4684a05acfb1','Defines a constant',NULL,'2.50','4bc0cb64-3962-4104-b32e-14335a819ae6','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('0ee7ee90-6e49-4132-9612-3a8dfb4f95c5','readonly',NULL,'3.00','322259f9-f88a-4ec3-b266-afa6ceb9dc8d','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('10ffd4fc-2c67-424e-8386-1fe31ebec054','Array',NULL,'1.36','f01b748b-0654-43ec-ae3f-ab115345e886','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('12089b99-2172-4a66-bae7-c3b72a758af7','Array',NULL,'5.45','f01b748b-0654-43ec-ae3f-ab115345e886','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('14e51efe-cbe4-49bb-92b7-bbfb56fac206','Declares a variable as global',NULL,'1.36','90edb194-5d27-44c5-9772-e7188acf02dc','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('169e25f5-b10a-44e9-be81-127b8936d94b','To perform multithreading',NULL,'1.36','a246bc87-ccdf-4d81-88e7-e924dbf88637','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('26c4cf78-4f5b-4f52-907f-1fd46f16c1ca','The compiler infers the type at compile-time',NULL,'5.45','9fe7bf4c-b077-4886-8ebb-d0aed74d53e4','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('2a3e5c63-3083-48a2-96fc-ce89c264d54c','A member that provides a flexible mechanism to read, write, or compute values',NULL,'3.00','11150a68-99d8-4e64-8d0d-46522fa4e094','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('2ab755ea-6063-48f5-9e90-08bf0154aaa0','To declare constants',NULL,'3.00','270964c2-57cc-4f14-a480-2371053deb6e','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('2ccf9c04-10e3-4ebe-ae65-b0065ef429a8','1',NULL,'5.45','b26626d0-00ff-4285-97f2-2454d1e434c3','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('2eb359b1-eea0-48e0-a40b-9ac7e3b6cef5','A data structure that holds multiple items of different types',NULL,'5.45','7369c18d-ded4-40c9-b36e-703af15b63cf','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('335884e6-c122-414d-90b6-bf19dfd40902','internal',NULL,'5.45','859c3b61-eb1d-41d1-a44e-24ed2c56511c','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('39597816-387c-4937-9e12-4fe22e3c624a','const',NULL,'2.50','322259f9-f88a-4ec3-b266-afa6ceb9dc8d','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('3a77a18b-5309-4897-a7de-043cff75b992','HashSet',NULL,'1.36','13cfe199-9c64-4d80-8b8f-30930d5ffc07','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('3fdccefd-4a0f-4825-9bea-3e03ecfdb66a','Declares a class or method that must be implemented in a derived class',NULL,'2.50','7236a238-4cbd-4544-a6c6-78b4621fcd70','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('413fb6ac-ab36-44d5-9521-5841a0fe75c0','To handle exceptions',NULL,'2.50','899226a2-7967-42ee-8d6c-1266c46ebcf9','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('41dc47d5-a4ad-4923-b6ed-932d353a6142','protected',NULL,'1.36','859c3b61-eb1d-41d1-a44e-24ed2c56511c','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('49af0a56-6cf8-43e4-a1b2-7a126ee7639b','Use the \'try-catch\' block',NULL,'2.50','407a7207-6cfc-43d1-b69d-50b57faf01e4','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('4c8ea9b8-61b7-4959-940b-692050cbeccf','To handle exceptions',NULL,'2.50','8b2033b1-b3da-4560-ae2d-4d71d3a0b710','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('5041e7bf-18bf-42ba-9ed6-baf2896215f3','To declare variables',NULL,'3.00','49f59a8a-a9d0-4a89-aa7d-e829cba2ecbf','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('51020106-a89e-43fa-821d-a0f45ce3bd70','Used for inheritance',NULL,'5.45','31155c8e-e33e-410d-acd6-c3b3a67ee285','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('54be9cd7-bc38-49a9-9721-c75f6fa41715','Allows a method to be overridden in derived classes',NULL,'5.45','90edb194-5d27-44c5-9772-e7188acf02dc','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('560a7d81-b064-4490-87df-ed2258471343','string',NULL,'3.00','87310523-527a-4bd4-8051-3084abb81a9a','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('5854c231-2caa-4bb7-88ca-f4260e88c3e4','Chưa trả lời',NULL,'1.36','41bf16d0-5f84-40ce-9be8-c821ba06c2a2','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('5ba45d42-b2b7-461a-8356-42d925700d01','A method for querying data',NULL,'1.36','7369c18d-ded4-40c9-b36e-703af15b63cf','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('5fd88c72-4f91-41a7-9828-6bc95f689798','Dictionary',NULL,'2.50','4c79ab37-1d94-4fca-9344-ea2e515c586c','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('5fda157b-1a5e-4044-a920-1f9ad58b5502','To define a contract that implementing classes must follow',NULL,'1.36','fccfc9f4-5dd9-4d89-8e36-c71c4a379fff','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('60ac9797-164b-4573-9366-09d0a9c7ea16','It declares constants',NULL,'1.36','9fe7bf4c-b077-4886-8ebb-d0aed74d53e4','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('6613ca22-73bb-439d-806b-bd55eb591687','To provide a new implementation of a base class method',NULL,'5.45','3bdc7ef8-1743-4e0c-9ab1-3d3ae320d036','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('6c3e6380-5f3c-410b-8ca8-593deb556e98','ref and out',NULL,'3.00','ddf84773-541b-4ad5-a528-4b182501360c','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('6c8bb38d-6ce7-453e-a2ea-8d75e0401b74','Tuple',NULL,'3.00','2afd7bdf-268b-4e82-aa49-59a5751e622a','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('6e8b66f9-eff5-4e32-bd2a-93ad135a959d','A feature for exception handling',NULL,'1.36','a173e637-209d-4001-8dfe-4b0d89ef5537','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('6eccb6f5-c291-4763-bb62-e1ead9f0297d','1',NULL,'1.36','b26626d0-00ff-4285-97f2-2454d1e434c3','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('6f27bf49-e384-4948-ad1e-73db240a18cc','Use the \'try-catch\' block',NULL,'3.00','407a7207-6cfc-43d1-b69d-50b57faf01e4','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('76aac2e1-fb81-4b20-b81c-fdd2d21fd071','To declare variables',NULL,'2.50','db75fc96-0dbc-45e4-b6a5-2760f4bfb415','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('7b87293d-e0bc-401c-bd5b-00d8e23ba885','bool',NULL,'3.00','8f2cef42-f18f-48a2-8162-75bf6d55c924','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('8008ad8b-ef68-4b1f-a6df-ca56e02c2c91','1',NULL,'5.45','06495c2c-a652-4b14-a331-bd1ebdd4f558','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('80294f2c-79ea-4af8-9bc2-ab9a0541376a','To handle exceptions',NULL,'1.36','3bdc7ef8-1743-4e0c-9ab1-3d3ae320d036','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('83540295-4576-46cb-8843-64b1b1315a8e','Declares a class or method that must be implemented in a derived class',NULL,'3.00','7236a238-4cbd-4544-a6c6-78b4621fcd70','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('8380057e-43e1-4867-bef1-becf747f0295','Allows a method to be overridden in derived classes',NULL,'3.00','e1b56648-d9a3-4060-be54-f820f3f75960','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('844276e0-c395-4ec5-ac5b-0f22a1ccc487','protected',NULL,'2.50','9ccba76c-be59-449f-8437-7a74fea0e2d6','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('851f4c41-d661-4888-a7c9-e63bd1d09a6e','class',NULL,'5.45','a3dd482f-9a6b-423f-8413-61e1b440e60b','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('897eb32f-fb5d-40c7-a912-5d38f3652400','A member that provides a flexible mechanism to read, write, or compute values',NULL,'5.45','a173e637-209d-4001-8dfe-4b0d89ef5537','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('8aa790e1-9be8-44de-a7ce-0794a55e81db','Restricts access to a variable',NULL,'1.36','31155c8e-e33e-410d-acd6-c3b3a67ee285','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('8b405242-9605-40a2-b005-8d4696493e4f','A method to declare variables',NULL,'2.50','11150a68-99d8-4e64-8d0d-46522fa4e094','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('8b881b79-e0e4-4dbc-842c-a3d84ed510aa','To provide a new implementation of a base class method',NULL,'3.00','899226a2-7967-42ee-8d6c-1266c46ebcf9','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('8ddcdfa4-2b68-4c40-9d03-c83e0b6ca1c3','bool',NULL,'1.36','d5f983c6-8436-41f9-bdec-ea9d7c488b95','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('8e6371fa-4706-4442-9816-c0e882c8dfb3','Belongs to the type rather than an instance',NULL,'2.50','7f00bb61-03ad-432c-a2c9-2f897fc191ad','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('942b00c4-facc-4f77-922c-d292303a5a14','Use the \'static\' keyword',NULL,'5.45','066b7fce-4e47-4c81-88e3-7f7eec585202','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('9b0f7c49-3ec2-421e-b16a-09fc82978d01','int',NULL,'5.45','d5f983c6-8436-41f9-bdec-ea9d7c488b95','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('9ca7e372-43c7-4d2d-9c04-22974cf623f5','To define inheritance',NULL,'5.45','fccfc9f4-5dd9-4d89-8e36-c71c4a379fff','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('9dacf7bb-f960-4e78-9e0a-c577700c88c0','To perform multithreading',NULL,'3.00','8b2033b1-b3da-4560-ae2d-4d71d3a0b710','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('9e9ecaab-cddf-4486-a7e3-5f4365b5813c','class',NULL,'1.36','a3dd482f-9a6b-423f-8413-61e1b440e60b','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('a4a6dfde-d1bb-4acc-96ad-a67ebb1c5e38','To declare constants',NULL,'2.50','270964c2-57cc-4f14-a480-2371053deb6e','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('a4fadd9e-0e3f-45a2-a7e1-645560a88e04','HashSet',NULL,'3.00','4c79ab37-1d94-4fca-9344-ea2e515c586c','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('a51c0cb9-6342-4f4f-a960-90b351ae74ef','To import namespaces',NULL,'5.45','f4e6c452-20de-4286-ae66-7c56df123093','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('a525886a-27ae-444d-b2b7-b3b34425cc5e','It is used for inheritance',NULL,'2.50','cea90bf8-d9b5-4e25-b26e-1a5ba1e38778','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('a6d457d9-061a-4cdf-b152-2045b1926999','To declare variables',NULL,'3.00','db75fc96-0dbc-45e4-b6a5-2760f4bfb415','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('ae0fce61-fcb8-4f1e-98ad-3a300df63f4a','A data structure that holds multiple items of different types',NULL,'3.00','22a8907a-db7c-489c-9b72-b6eafb55a4b0','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('b2ac75f3-e37f-4ae2-a157-6081293d6a04','Used for inheritance',NULL,'5.45','daa610e5-f69d-4189-acf3-cd2aff3b9f86','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('b895c08a-b3a1-4c89-b94d-dd652109cfa3','Used for thread synchronization',NULL,'1.36','daa610e5-f69d-4189-acf3-cd2aff3b9f86','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('c02a012a-76e8-48ae-a465-3dfd8a8f5343','Chưa trả lời',NULL,'1.36','6c73a705-97e3-4063-8653-18b6930804c0','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('c3b5c484-8f2b-4f9d-90bb-f1c8618570ee','None',NULL,'5.45','13cfe199-9c64-4d80-8b8f-30930d5ffc07','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('c3f04ccd-9a81-4b69-942a-63ec039f1e61','const',NULL,'5.45','6c73a705-97e3-4063-8653-18b6930804c0','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('c5880499-e6bf-41fc-bc1f-6060969391d7','Implement IEnumerable',NULL,'1.36','066b7fce-4e47-4c81-88e3-7f7eec585202','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('c5add731-60f1-44c0-9224-e1f0cb013d7c','string',NULL,'2.50','87310523-527a-4bd4-8051-3084abb81a9a','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('ca3ee110-e9c7-41bb-937f-6ffab78ab1f3','Defines a constant',NULL,'3.00','4bc0cb64-3962-4104-b32e-14335a819ae6','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('cbe89441-f031-4dc5-9a40-a4e55ac06767','Belongs to the type rather than an instance',NULL,'3.00','7f00bb61-03ad-432c-a2c9-2f897fc191ad','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('cc35990e-ed96-4ed3-9a36-3bb3b5eee2df','It is used for inheritance',NULL,'3.00','cea90bf8-d9b5-4e25-b26e-1a5ba1e38778','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('d02094ad-e79b-415b-9f9f-82930a37fa67','try and catch',NULL,'1.36','f087d28a-96a2-47fd-a247-8e745ac05528','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('d0ddbc08-3a0c-43cf-8d63-2266f846c105','bool',NULL,'2.50','8f2cef42-f18f-48a2-8162-75bf6d55c924','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('d107025e-3257-408b-8e9c-650fc041dc11','Dictionary',NULL,'2.50','2afd7bdf-268b-4e82-aa49-59a5751e622a','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('e61a086f-6777-48b3-8642-fdda9d4cf700','To declare variables',NULL,'2.50','49f59a8a-a9d0-4a89-aa7d-e829cba2ecbf','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('e8c61e6f-be0c-4342-a645-2c824c57ad80','1',NULL,'1.36','06495c2c-a652-4b14-a331-bd1ebdd4f558','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('e93dddc6-f83e-4123-8dbf-e2e9da28a4bd','A collection of key-value pairs',NULL,'2.50','22a8907a-db7c-489c-9b72-b6eafb55a4b0','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','2'),('eefef9ab-8542-4707-bba4-bd6202d8534d','protected',NULL,'3.00','9ccba76c-be59-449f-8437-7a74fea0e2d6','c1fe064b-6ec7-414f-ac63-0e55960e546e','1a73456e-9502-4443-b028-b8066672ca3f','1'),('f1da3732-b6fb-4527-9699-fa59188a93b3','Enables multithreading',NULL,'1.36','afa5be01-1e63-475e-be99-7159d54cadc0','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('f2da7dd9-8346-42cc-a697-45ac3f5a7751','To import namespaces',NULL,'1.36','f4e6c452-20de-4286-ae66-7c56df123093','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','1'),('f5749f2d-1a1d-41c8-9cd9-69f814324566','Declares a method as private',NULL,'5.45','afa5be01-1e63-475e-be99-7159d54cadc0','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('f64a0646-a5a5-4b1f-aca2-e6ed1d8b28c6','try and catch',NULL,'5.45','f087d28a-96a2-47fd-a247-8e745ac05528','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('f7574f62-7aed-4190-bd4e-3b9d2e5cee0b','To query data from different sources',NULL,'5.45','a246bc87-ccdf-4d81-88e7-e924dbf88637','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2'),('fa2be72e-28e0-4018-8c77-a359467c213c','To define a type that references methods',NULL,'5.45','41bf16d0-5f84-40ce-9be8-c821ba06c2a2','c1fe064b-6ec7-414f-ac63-0e55960e546e','9c648b84-1260-47cf-9b8e-a8ac6eec09d1','2');
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invalided_token`
--

DROP TABLE IF EXISTS `invalided_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invalided_token` (
  `id` varchar(255) NOT NULL,
  `expiry_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invalided_token`
--

LOCK TABLES `invalided_token` WRITE;
/*!40000 ALTER TABLE `invalided_token` DISABLE KEYS */;
INSERT INTO `invalided_token` VALUES ('6d228b0c-9a70-4a3e-8fb6-42936051c743','2024-12-20 13:27:58.000000'),('e7318850-a96e-4cac-8782-6dad4ea37cfa','2024-12-20 10:37:49.000000');
/*!40000 ALTER TABLE `invalided_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `questions_id` varchar(255) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `choive` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `test_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`questions_id`),
  KEY `FKisqd64a7oqr5gd57690rk2cgx` (`test_id`),
  CONSTRAINT `FKisqd64a7oqr5gd57690rk2cgx` FOREIGN KEY (`test_id`) REFERENCES `test` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES ('06495c2c-a652-4b14-a331-bd1ebdd4f558','1','1','1',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('066b7fce-4e47-4c81-88e3-7f7eec585202','Create a class derived from System.Exception','Create a class derived from System.Exception|Use the \'try-catch\' block|Implement IEnumerable|Use the \'static\' keyword','Which of the following can be used to define a custom exception in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('11150a68-99d8-4e64-8d0d-46522fa4e094','A member that provides a flexible mechanism to read, write, or compute values','A member that provides a flexible mechanism to read, write, or compute values|A method to declare variables|A keyword for loops|A feature for exception handling','What is a property in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('13cfe199-9c64-4d80-8b8f-30930d5ffc07','None','None|Dictionary|HashSet|SortedList','Which collection allows duplicate keys in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('1b2aee32-fadf-421f-853c-86b9dfdf775c','Clustering algorithm','Clustering algorithm|Classification algorithm|Regression algorithm|None of the above','What is k-means?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('200d8967-ce86-45bb-be5e-d95b897c40ca','True positives','True positives|False negatives|True negatives|None of the above','What is recall?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('22a8907a-db7c-489c-9b72-b6eafb55a4b0','A data structure that holds multiple items of different types','A data structure that holds multiple items of different types|A collection of key-value pairs|A class that implements IEnumerable|A method for querying data','What is a \'Tuple\' in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('270964c2-57cc-4f14-a480-2371053deb6e','To define a contract that implementing classes must follow','To define a contract that implementing classes must follow|To declare constants|To define inheritance|To handle exceptions','What is the purpose of the \'interface\' keyword in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('279f5807-11cc-4b86-ab6b-3aab15eda950','Using labeled data','Using labeled data|Using unlabeled data|Reinforcement learning|Clustering','What is supervised learning?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('2afd7bdf-268b-4e82-aa49-59a5751e622a','Dictionary','Dictionary|List|Array|Tuple','Which type is used to represent a collection of key-value pairs in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('31155c8e-e33e-410d-acd6-c3b3a67ee285','The value can only be assigned during declaration or in the constructor','The value can only be assigned during declaration or in the constructor|Defines a constant|Restricts access to a variable|Used for inheritance','What does the \'readonly\' keyword mean in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('322259f9-f88a-4ec3-b266-afa6ceb9dc8d','const','const|readonly|static|final','What keyword is used to define a constant in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('3bdc7ef8-1743-4e0c-9ab1-3d3ae320d036','To provide a new implementation of a base class method','To provide a new implementation of a base class method|To declare a method as static|To handle exceptions|To initialize variables','What is the use of the \'override\' keyword in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('3df55f8c-5c1d-4be9-b424-71c109b2fb76','Validation technique','Validation technique|Testing method|Training method|None of the above','What is cross-validation?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('407a7207-6cfc-43d1-b69d-50b57faf01e4','Create a class derived from System.Exception','Create a class derived from System.Exception|Use the \'try-catch\' block|Implement IEnumerable|Use the \'static\' keyword','Which of the following can be used to define a custom exception in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('41bf16d0-5f84-40ce-9be8-c821ba06c2a2','To define a type that references methods','To define a type that references methods|To handle exceptions|To declare variables|To initialize collections','What is the purpose of the \'delegate\' keyword in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('49f59a8a-a9d0-4a89-aa7d-e829cba2ecbf','To manage resources efficiently','To manage resources efficiently|To declare variables|To import namespaces|To handle exceptions','What is the purpose of the \'using\' statement in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('4bc0cb64-3962-4104-b32e-14335a819ae6','The value can only be assigned during declaration or in the constructor','The value can only be assigned during declaration or in the constructor|Defines a constant|Restricts access to a variable|Used for inheritance','What does the \'readonly\' keyword mean in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('4c79ab37-1d94-4fca-9344-ea2e515c586c','None','None|Dictionary|HashSet|SortedList','Which collection allows duplicate keys in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('55b41bfb-a754-47ae-80d0-bbe96816542c','True positives','True positives|False positives|True negatives|None of the above','What is precision?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('59ff0710-e18e-462d-8771-764267ba0f28','Classification algorithm','Classification algorithm|Regression algorithm|Clustering algorithm|None of the above','What is a support vector machine?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('6c36d107-14a5-4ff0-9dc9-de333ee0249a','Error from sensitivity','Error from sensitivity|Error from assumptions|Error from noise|None of the above','What is variance in ML?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('6c73a705-97e3-4063-8653-18b6930804c0','const','const|readonly|static|final','What keyword is used to define a constant in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('7236a238-4cbd-4544-a6c6-78b4621fcd70','Declares a class or method that must be implemented in a derived class','Declares a class or method that must be implemented in a derived class|Declares a method as private|Enables multithreading|Declares a global variable','What does \'abstract\' keyword do in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('7369c18d-ded4-40c9-b36e-703af15b63cf','A data structure that holds multiple items of different types','A data structure that holds multiple items of different types|A collection of key-value pairs|A class that implements IEnumerable|A method for querying data','What is a \'Tuple\' in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('7cec43d6-8458-4d15-877a-3cddc89f9ed1','Dimensionality reduction','Dimensionality reduction|Clustering|Classification|Regression','What is PCA?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('7e3b83c1-aace-47f6-82db-033430d322a6','A tree-like model','A tree-like model|A linear model|A clustering algorithm|A neural network','What is a decision tree?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('7f00bb61-03ad-432c-a2c9-2f897fc191ad','Belongs to the type rather than an instance','Belongs to the type rather than an instance|Used for thread synchronization|Used for inheritance|Defines a constant','What does the \'static\' keyword mean in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('859c3b61-eb1d-41d1-a44e-24ed2c56511c','internal','internal|private|protected|public','What is the default access modifier for a class in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('87310523-527a-4bd4-8051-3084abb81a9a','int','int|string|class|interface','Which of the following is a value type in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('899226a2-7967-42ee-8d6c-1266c46ebcf9','To provide a new implementation of a base class method','To provide a new implementation of a base class method|To declare a method as static|To handle exceptions|To initialize variables','What is the use of the \'override\' keyword in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('8b2033b1-b3da-4560-ae2d-4d71d3a0b710','To query data from different sources','To query data from different sources|To handle exceptions|To perform multithreading|To define classes','What is LINQ used for in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('8f2cef42-f18f-48a2-8162-75bf6d55c924','class','class|int|bool|enum','Which of these is a reference type in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('90edb194-5d27-44c5-9772-e7188acf02dc','Allows a method to be overridden in derived classes','Allows a method to be overridden in derived classes|Declares a method as abstract|Declares a variable as global|Enables multiple inheritance','What does the \'virtual\' keyword in C# signify?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('99c5ba3a-d882-4c30-9776-861ee161dade','Optimization algorithm','Optimization algorithm|Clustering algorithm|Classification algorithm|Regression algorithm','What is gradient descent?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('9ccba76c-be59-449f-8437-7a74fea0e2d6','internal','internal|private|protected|public','What is the default access modifier for a class in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('9db48e6b-fcde-424d-bad7-84e08361ed73','Error measurement','Error measurement|Validation technique|Training method|None of the above','What is a loss function?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('9fe7bf4c-b077-4886-8ebb-d0aed74d53e4','The compiler infers the type at compile-time','The compiler infers the type at compile-time|It defines a variable as global|It is used for inheritance|It declares constants','Which of the following is true about the \'var\' keyword in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('a0964443-d87b-4c99-bb00-24ba1b6947b3','Evaluation metric','Evaluation metric|Training metric|Testing metric|None of the above','What is a confusion matrix?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('a173e637-209d-4001-8dfe-4b0d89ef5537','A member that provides a flexible mechanism to read, write, or compute values','A member that provides a flexible mechanism to read, write, or compute values|A method to declare variables|A keyword for loops|A feature for exception handling','What is a property in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('a246bc87-ccdf-4d81-88e7-e924dbf88637','To query data from different sources','To query data from different sources|To handle exceptions|To perform multithreading|To define classes','What is LINQ used for in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('a3dd482f-9a6b-423f-8413-61e1b440e60b','int','int|string|class|interface','Which of the following is a value type in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('ab44b39c-9737-4b75-a33b-2b5a62f84e07','Ensemble method','Ensemble method|Single tree|Neural network|Linear model','What is a random forest?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('afa5be01-1e63-475e-be99-7159d54cadc0','Declares a class or method that must be implemented in a derived class','Declares a class or method that must be implemented in a derived class|Declares a method as private|Enables multithreading|Declares a global variable','What does \'abstract\' keyword do in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('b26626d0-00ff-4285-97f2-2454d1e434c3','1','1','1',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('bb8f20bd-d64c-41a6-9a41-7cc72834ad66','A network of neurons','A network of neurons|A linear model|A decision tree|A clustering algorithm','What is a neural network?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('c711ce19-2a7d-4763-b872-9233bee03939','Trial and error','Using labeled data|Using unlabeled data|Trial and error|Supervised learning','What is reinforcement learning?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('cea90bf8-d9b5-4e25-b26e-1a5ba1e38778','The compiler infers the type at compile-time','The compiler infers the type at compile-time|It defines a variable as global|It is used for inheritance|It declares constants','Which of the following is true about the \'var\' keyword in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('ced1eca4-6cde-4ec3-94e8-a2537d40e8ea','Using unlabeled data','Using labeled data|Using unlabeled data|Reinforcement learning|Classification','What is unsupervised learning?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('d4b443a7-fb12-4e97-a0fe-3020a4ee7b7b','Not fitting the data','Not fitting the data|Fitting the noise|Fitting the data|None of the above','What is underfitting?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('d5f983c6-8436-41f9-bdec-ea9d7c488b95','class','class|int|bool|enum','Which of these is a reference type in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('daa610e5-f69d-4189-acf3-cd2aff3b9f86','Belongs to the type rather than an instance','Belongs to the type rather than an instance|Used for thread synchronization|Used for inheritance|Defines a constant','What does the \'static\' keyword mean in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('db75fc96-0dbc-45e4-b6a5-2760f4bfb415','To define a type that references methods','To define a type that references methods|To handle exceptions|To declare variables|To initialize collections','What is the purpose of the \'delegate\' keyword in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('ddf84773-541b-4ad5-a528-4b182501360c','async and await','async and await|Thread and Task|try and catch|ref and out','Which of the following is used for asynchronous programming in C#?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('e1b56648-d9a3-4060-be54-f820f3f75960','Allows a method to be overridden in derived classes','Allows a method to be overridden in derived classes|Declares a method as abstract|Declares a variable as global|Enables multiple inheritance','What does the \'virtual\' keyword in C# signify?',NULL,'1a73456e-9502-4443-b028-b8066672ca3f'),('ebba2653-44c5-4e77-ab13-4171644174a5','Selecting important features','Selecting important features|Selecting all features|Selecting no features|None of the above','What is feature selection?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('ebbd8fd3-d6b8-4d56-946a-77470558afd8','Fitting the noise','Fitting the noise|Fitting the data|Not fitting the data|None of the above','What is overfitting?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('f01b748b-0654-43ec-ae3f-ab115345e886','Dictionary','Dictionary|List|Array|Tuple','Which type is used to represent a collection of key-value pairs in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('f087d28a-96a2-47fd-a247-8e745ac05528','async and await','async and await|Thread and Task|try and catch|ref and out','Which of the following is used for asynchronous programming in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('f10354c3-b4cc-4515-8531-df143b9a3dcb','Error from assumptions','Error from assumptions|Error from noise|Error from variance|None of the above','What is bias in ML?',NULL,'f7747798-c92f-4802-9329-9d8400fb9568'),('f4e6c452-20de-4286-ae66-7c56df123093','To manage resources efficiently','To manage resources efficiently|To declare variables|To import namespaces|To handle exceptions','What is the purpose of the \'using\' statement in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1'),('fccfc9f4-5dd9-4d89-8e36-c71c4a379fff','To define a contract that implementing classes must follow','To define a contract that implementing classes must follow|To declare constants|To define inheritance|To handle exceptions','What is the purpose of the \'interface\' keyword in C#?',NULL,'9c648b84-1260-47cf-9b8e-a8ac6eec09d1');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test` (
  `test_id` varchar(255) NOT NULL,
  `image_test` varchar(255) DEFAULT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `count_image_test` int NOT NULL,
  `class_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `trang_thai` bit(1) DEFAULT NULL,
  PRIMARY KEY (`test_id`),
  KEY `FK6reujcx5s28l7e08rlx1w5m8g` (`class_id`),
  KEY `FKqys63qo2geuh95arowdbr29f1` (`user_id`),
  CONSTRAINT `FK6reujcx5s28l7e08rlx1w5m8g` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `FKqys63qo2geuh95arowdbr29f1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` VALUES ('1a73456e-9502-4443-b028-b8066672ca3f','https://i.imgur.com/PWjjJ15.jpeg',NULL,'lân 1 ',32,'f453d',NULL,NULL),('9c648b84-1260-47cf-9b8e-a8ac6eec09d1','https://i.imgur.com/UoC6r67.jpeg',NULL,'kiểm tra lần 1',20,'f453d',NULL,_binary ''),('f7747798-c92f-4802-9329-9d8400fb9568','https://i.imgur.com/DIgcpuf.jpeg',NULL,'học máy',20,'f453d',NULL,NULL);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('9a133acd-5fb1-40b7-af11-131c5c1d530c','hehe','$2a$10$dLE8Be1GZn6ajTwt7wA3XOnTK4MUeb5/HUMhuLVeWgjMTseFd7F.2','hung'),('c1fe064b-6ec7-414f-ac63-0e55960e546e','duc','$2a$10$ESGWeb2z8a/VBNocwH4NzepixJBvfL9QRL7qWDYbPsjwwoXCnc/ba','duc');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_class_role`
--

DROP TABLE IF EXISTS `user_class_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_class_role` (
  `id` varchar(255) NOT NULL,
  `is_delete` bit(1) DEFAULT NULL,
  `role` enum('STUDENT','TEACHER') DEFAULT NULL,
  `class_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1x3clj0gpm63p0wbjx10r64ie` (`class_id`),
  KEY `FKc29rfd4h4gipu1g3pi5lv0m53` (`user_id`),
  CONSTRAINT `FK1x3clj0gpm63p0wbjx10r64ie` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `FKc29rfd4h4gipu1g3pi5lv0m53` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_class_role`
--

LOCK TABLES `user_class_role` WRITE;
/*!40000 ALTER TABLE `user_class_role` DISABLE KEYS */;
INSERT INTO `user_class_role` VALUES ('882fb20e-8a1c-4679-8c13-7f1775303a80',NULL,'TEACHER','f453d','c1fe064b-6ec7-414f-ac63-0e55960e546e');
/*!40000 ALTER TABLE `user_class_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-20 21:52:23
