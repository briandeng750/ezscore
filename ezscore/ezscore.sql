CREATE DATABASE  IF NOT EXISTS `ezscore` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ezscore`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: ezscore
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `competitor`
--

DROP TABLE IF EXISTS `competitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competitor` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `team` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competitor`
--

LOCK TABLES `competitor` WRITE;
/*!40000 ALTER TABLE `competitor` DISABLE KEYS */;
INSERT INTO `competitor` VALUES (78,'VO','Alex Deng','123','La Costa Canyon'),(83,'VC','Miranda Achard','176','Torrey Pines'),(84,'VC','Brooke Boney','101','Del Norte'),(85,'VC','Sydney Boney','102','Del Norte'),(86,'VC','Hailey D Souza','103','Del Norte'),(87,'VC','Alexis D Souza','104','Del Norte'),(88,'VC','Lindsay Kang','105','Del Norte'),(89,'VC','Laura LeBlanc','106','Del Norte'),(90,'VC','Baylee Mumma','107','Del Norte'),(91,'VC','Jenna Schram','108','Del Norte'),(92,'VC','Amanda Schroeder','109','Del Norte'),(93,'VC','Karina Palido','110','El Cajon'),(94,'VC','Cheynoa Curo','111','El Capitan'),(95,'VC','Alyssa Jernigan','112','El Capitan'),(96,'VC','Justina DiBacco','113','Fallbrook'),(97,'VC','Sarah Engerbretson','114','Fallbrook'),(98,'VC','Angelica Balbuena','115','Granite Hills'),(99,'VC','Elana Cates','119','La Costa Canyon'),(100,'VC','Rachel Haiduck','120','La Costa Canyon'),(101,'VC','Molly Helmbacher','121','La Costa Canyon'),(102,'VC','Morgan Kindel','122','La Costa Canyon'),(103,'VC','Michaela McDHess','123','La Costa Canyon'),(104,'VC','McKenna O Neil','124','La Costa Canyon'),(105,'VC','Lauren Panetti','125','La Costa Canyon'),(106,'VC','Lexi Paulson','126','La Costa Canyon'),(107,'VC','Ellen Reidy','127','La Costa Canyon'),(108,'VC','Kristina Scheufler','128','La Costa Canyon'),(109,'VC','Michelle Tsang','129','La Costa Canyon'),(110,'VC','Sydney Wennerstrom','130','La Costa Canyon'),(111,'VC','Mackenna Winn','131','La Costa Canyon'),(112,'VC','Abigail Cornejo','132','Montgomery'),(113,'VC','Lea Antrobus','133','Mount Carmel'),(114,'VC','Marissa Baterina','134','Mount Carmel'),(115,'VC','Tehya Belz','135','Mount Carmel'),(116,'VC','Renee Bulda','136','Mount Carmel'),(117,'VC','Paige Jenkins','137','Mount Carmel'),(118,'VC','Annelise Morgan','138','Mount Carmel'),(119,'VC','Neli Razavi','139','Mount Carmel'),(120,'VC','Monica Senese','140','Mount Carmel'),(121,'VC','Rianna Sulit','142','Mount Carmel'),(122,'VC','Briana Thornton','143','Mount Carmel'),(123,'VC','Rachael Watson','144','Mount Carmel'),(124,'VC','Katelyn Heffler','145','OLP'),(125,'VC','Alexa Calgaro','146','Poway'),(126,'VC','Stephanie Colvin','147','Poway'),(127,'VC','Melyssa Donovan','148','Poway'),(128,'VC','Stephanie Motoc','150','Poway'),(129,'VC','Juliette Perrault','151','Poway'),(130,'VC','Elle Pitts','152','Poway'),(131,'VC','Gabrielle Pochard','153','Poway'),(132,'VC','Kassie Schenck','154','Poway'),(133,'VC','Hailey Sokoloff','155','Poway'),(134,'VC','Michelle Bischoff','156','Rancho Bernardo'),(135,'VC','Monica Dixon','157','Rancho Bernardo'),(136,'VC','Marissa Dixon','158','Rancho Bernardo'),(137,'VC','Dana Lohr','159','Rancho Bernardo'),(138,'VC','Goldie Malamud','160','Rancho Bernardo'),(139,'VC','Maria Ota','161','Rancho Bernardo'),(140,'VC','Molly Pobiel','162','Rancho Bernardo'),(141,'VC','Trista Sims','163','Rancho Bernardo'),(142,'VC','Misa Sueoyoshi','164','Rancho Bernardo'),(143,'VC','Kylie  Riccardi','166','Santana'),(144,'VC','Paige Burgin','167','Santana'),(145,'VC','Maddie Duncan','168','Santana'),(146,'VC','Hayley Ford','169','Santana'),(147,'VC','Sam Maddox','170','Santana'),(148,'VC','La Shay Sawyer','171','Santana'),(149,'VC','Selena Wade','172','Santana'),(150,'VC','Alexia Dolamakian','173','Steele Canyon'),(151,'VC','Anna Putnam','174','Steele Canyon'),(152,'VC','Julia Spurgeon','175','Steele Canyon'),(153,'VC','Allison Aguirre','177','Torrey Pines'),(154,'VC','Erica Becker','178','Torrey Pines'),(155,'VC','Tal Cook','181','Torrey Pines'),(156,'VC','Christine Li','182','Torrey Pines'),(157,'VC','Natalia Owcharuk','183','Torrey Pines'),(158,'VC','Caroline Pao','184','Torrey Pines'),(159,'VC','Shaina Wooley','186','Torrey Pines'),(160,'VC','Amanda O Campo','194','Valhalla'),(161,'VC','Grace Avery','197','West Hills'),(162,'VC','Kelsey Barker','198','West Hills'),(163,'VC','Marissa Fleming','199','West Hills'),(164,'VC','Camryn Force','200','West Hills'),(165,'VC','Rikki Herrada','201','West Hills'),(166,'VC','Carly MacBeth-Phares','202','West Hills'),(167,'VC','Marissa Miller','203','West Hills'),(168,'VC','Jenna Pierce','204','West Hills'),(169,'VC','Anika Tellez','206','West Hills'),(170,'VC','Kristin Carpentier','207','Westview'),(171,'VC','Neele Pantfoerder','208','Westview'),(172,'VO','Jiselle deAnda','209','Del Norte'),(173,'VO','Brianna Hoben','210','Del Norte'),(174,'VO','Anna Webb','211','Del Norte'),(175,'VO','Kendyl Naugle','212','Eastlake'),(176,'VO','Katelyn Agunat','213','El Capitan'),(177,'VO','Natalia Herret','214','El Capitan'),(178,'VO','Tori Lomak','215','El Capitan'),(179,'VO','Erika Lopez','216','El Capitan'),(180,'VO','Cammy Squibb','218','Granite Hills'),(181,'VO','Skylar Tisdale','219','Granite Hills'),(182,'VO','Georganna Hempfill','220','Grossmont'),(183,'VO','Melissa Palmer','221','Grossmont'),(184,'VO','Alex Deng','222','La Costa Canyon'),(185,'VO','Natalie Jaynes','223','La Costa Canyon'),(186,'VO','Kelsey Hamlett','225','Monte Vista'),(187,'VO','Alyssa West','226','Monte Vista'),(188,'VO','Rezanne Aussi','227','Mount Carmel'),(189,'VO','Carter Clatterbuck','228','Mount Carmel'),(190,'VO','Sammy Trescott','230','OLP'),(191,'VO','Tatiana Schweigert','231','Otay'),(192,'VO','Shannon Hamilton','232','Poway'),(193,'VO','Cherokee McFadyen','233','Poway'),(194,'VO','Megan Stapley','234','Rancho Bernardo'),(195,'VO','Elyse Frakes','235','Santana'),(196,'VO','Brielle Wilkerson','236','Santana'),(197,'VO','Jen Buki','237','Steele Canyon'),(198,'VO','Brittin Burnworth','238','Steele Canyon'),(199,'VO','Emily Dowling','239','Steele Canyon'),(200,'VO','Delaney Sullivan','240','Steele Canyon'),(201,'VO','Erin Amyer','241','Torrey Pines'),(202,'VO','Ashley Bauchmann','242','Valhalla'),(203,'VO','Gina Bruestle','243','Valhalla'),(204,'VO','Katie Stockwell','244','Valhalla'),(205,'VO','Caitlin Zamudio','245','Valhalla'),(206,'VO','Tori Anderson','246','West Hills'),(207,'VO','Renee Moore','247','West Hills'),(208,'VO','Sarah Robertson','248','West Hills'),(209,'VO','Emily Bellin','249','Westview'),(210,'VO','Angela Zhang','252','Westview'),(211,'VC','Michaela Rush','205','West Hills'),(212,'VO','Clare Chapman','250','Westview'),(213,'VC','Seanna Lechner','251','Westview'),(214,'VC','Brooke Boney','51','Del Norte'),(215,'VC','Lindsay Kang','55','Del Norte'),(216,'VC','Baylee Mumma','57','Del Norte'),(217,'VO','Jiselle De Anda','91','Del Norte'),(218,'VO','Brianna Hoben','92','Del Norte'),(219,'VO','Lanai Modeste','93','Del Norte'),(220,'VO','Anna Webb','94','Del Norte'),(221,'VC','Maddie Brown','102','Our Lady of Peace'),(222,'JV','Marissa Hensley','71','Del Norte'),(223,'JV','Morgan Wallen','73','Del Norte'),(224,'JV','Alana Webb','74','Del Norte'),(225,'JV','Keli Wilkes','75','Del Norte'),(226,'JV','Julianna Yoo','76','Del Norte');
/*!40000 ALTER TABLE `competitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competitor_competitorresults`
--

DROP TABLE IF EXISTS `competitor_competitorresults`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competitor_competitorresults` (
  `competitor_ID` int(11) DEFAULT NULL,
  `COMPETITORRESULTS` double DEFAULT NULL,
  `COMPETITORRESULTS_KEY` int(11) DEFAULT NULL,
  KEY `FK_competitor_COMPETITORRESULTS_competitor_ID` (`competitor_ID`),
  CONSTRAINT `FK_competitor_COMPETITORRESULTS_competitor_ID` FOREIGN KEY (`competitor_ID`) REFERENCES `competitor` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competitor_competitorresults`
--

LOCK TABLES `competitor_competitorresults` WRITE;
/*!40000 ALTER TABLE `competitor_competitorresults` DISABLE KEYS */;
INSERT INTO `competitor_competitorresults` VALUES (83,8.3,0),(84,0,0),(85,9.25,0),(86,0,0),(87,9.05,0),(88,9,0),(89,9.325,0),(90,8.8,0),(91,9.05,0),(92,8.6,0),(93,9.05,0),(94,0,0),(95,8.8,0),(96,0,0),(97,9.65,0),(98,8.4,0),(99,0,0),(100,8.85,0),(101,0,0),(102,9.1,0),(103,9,0),(104,9.375,0),(105,0,0),(106,9.2,0),(107,0,0),(108,9.375,0),(109,0,0),(110,9,0),(111,7.9,0),(112,9.15,0),(113,8.6,0),(114,0,0),(115,0,0),(116,9.4,0),(117,0,0),(118,9,0),(119,9.2,0),(120,7.95,0),(121,8.45,0),(122,8.85,0),(123,9.45,0),(124,9.3,0),(125,7.75,0),(126,9.1,0),(127,0,0),(128,7.9,0),(129,9.05,0),(130,8.2,0),(131,0,0),(132,0,0),(133,0,0),(134,7.75,0),(135,9.4,0),(136,8.4,0),(137,8.55,0),(138,8.05,0),(139,7.45,0),(140,9.1,0),(141,7.75,0),(142,8.25,0),(143,8.35,0),(144,8.575,0),(145,8.4,0),(146,8.45,0),(147,7.7,0),(148,9.275,0),(149,8.475,0),(150,8.75,0),(151,7.85,0),(152,9.1,0),(153,8.1,0),(154,9,0),(155,9.25,0),(156,0,0),(157,7.8,0),(158,9.525,0),(159,8.6,0),(160,0,0),(161,0,0),(162,9,0),(163,0,0),(164,8.95,0),(165,9.05,0),(166,9.4,0),(167,9.35,0),(168,7.95,0),(169,0,0),(170,8.625,0),(171,0,0),(172,9,0),(173,8.05,0),(174,8.05,0),(175,7.95,0),(176,0,0),(177,0,0),(178,0,0),(179,7.25,0),(180,0,0),(181,0,0),(182,8.05,0),(183,9.05,0),(185,9.1,0),(186,7,0),(187,6.65,0),(188,8.125,0),(189,8.35,0),(190,8.7,0),(191,8.05,0),(192,8.3,0),(193,7.9,0),(194,8.1,0),(195,0,0),(196,7,0),(197,7.4,0),(198,0,0),(199,0,0),(200,7.45,0),(201,8.25,0),(202,7.4,0),(203,8.3,0),(204,6,0),(205,7.7,0),(206,8.8,0),(207,8,0),(208,8,0),(209,8.2,0),(210,8.15,0),(211,8.55,0),(212,7.55,0),(213,7.8,0),(83,7.7,1),(84,0,1),(85,9.7,1),(86,0,1),(87,8.15,1),(88,9.25,1),(89,9.725,1),(90,8.85,1),(91,9.425,1),(92,7.2,1),(93,8.8,1),(94,0,1),(95,9,1),(96,0,1),(97,9.5,1),(98,8.7,1),(99,7.75,1),(100,8.65,1),(101,0,1),(102,9.225,1),(103,9.1,1),(104,9,1),(105,0,1),(106,9.25,1),(107,0,1),(108,9.725,1),(109,0,1),(110,9.65,1),(111,0,1),(112,9.1,1),(113,0,1),(114,0,1),(115,0,1),(116,9.375,1),(117,0,1),(118,8.65,1),(119,9.3,1),(120,7.725,1),(121,8.5,1),(122,7.45,1),(123,9.575,1),(124,9.2,1),(125,0,1),(126,6.55,1),(127,0,1),(128,7.95,1),(129,9.1,1),(130,8.575,1),(131,4.9,1),(132,0,1),(133,3.85,1),(134,7.65,1),(135,8.25,1),(136,7.55,1),(137,2.65,1),(138,0,1),(139,0,1),(140,8.35,1),(141,6.75,1),(142,7.625,1),(143,5.2,1),(144,8.05,1),(145,7.7,1),(146,6.65,1),(147,6.7,1),(148,9.325,1),(149,8.2,1),(150,0,1),(151,0,1),(152,9.25,1),(153,0,1),(154,9.2,1),(155,9.6,1),(156,0,1),(157,0,1),(158,9.55,1),(159,6.9,1),(160,0,1),(161,0,1),(162,9.35,1),(163,0,1),(164,8.5,1),(165,9.425,1),(166,9.625,1),(167,9.475,1),(168,6.9,1),(169,0,1),(170,8.525,1),(171,0,1),(172,8.95,1),(173,8.7,1),(174,8.9,1),(175,7.95,1),(176,0,1),(177,0,1),(178,2.5,1),(179,3.25,1),(180,4.85,1),(181,4.75,1),(182,7.65,1),(183,8.875,1),(184,8.775,1),(185,9.2,1),(186,3.7,1),(187,3.75,1),(188,8.7,1),(189,9,1),(190,8.2,1),(191,8.1,1),(192,7.125,1),(193,5.15,1),(194,7.6,1),(195,4.2,1),(196,3.3,1),(197,8,1),(198,4.85,1),(199,4.9,1),(200,5.25,1),(201,7.8,1),(202,7,1),(203,6,1),(204,5.1,1),(205,6.6,1),(206,8.95,1),(207,8.55,1),(208,7.9,1),(209,8.775,1),(210,7.75,1),(211,9.425,1),(212,6.7,1),(213,7.575,1),(83,0,2),(84,9.25,2),(85,9.525,2),(86,0,2),(87,8.075,2),(88,9,2),(89,9.25,2),(90,0,2),(91,9.2,2),(92,8.225,2),(93,8.625,2),(94,0,2),(95,8.7,2),(96,0,2),(97,9.5,2),(98,8.05,2),(99,8.825,2),(100,8.8,2),(101,9.375,2),(102,0,2),(103,9.325,2),(104,9.525,2),(105,0,2),(106,8.8,2),(107,0,2),(108,9.75,2),(109,0,2),(110,9.7,2),(111,0,2),(112,9.15,2),(113,8.325,2),(114,8.675,2),(115,8.55,2),(116,9.475,2),(117,0,2),(118,8.1,2),(119,8.825,2),(120,0,2),(121,8.35,2),(122,0,2),(123,9.5,2),(124,0,2),(125,0,2),(126,9.025,2),(127,0,2),(128,8.725,2),(129,9.125,2),(130,8.35,2),(131,0,2),(132,7.975,2),(133,6.4,2),(134,7.35,2),(135,8,2),(136,7.75,2),(137,7.75,2),(138,7.8,2),(139,8.075,2),(140,8.325,2),(141,6.775,2),(142,8.925,2),(143,7.45,2),(144,8.475,2),(145,8.4,2),(146,7.4,2),(147,6.3,2),(148,9.25,2),(149,8.375,2),(150,0,2),(151,0,2),(152,9.3,2),(153,0,2),(154,9.275,2),(155,9.475,2),(156,8.975,2),(157,0,2),(158,9.425,2),(159,9.075,2),(160,0,2),(161,9.5,2),(162,9.025,2),(163,9.2,2),(164,0,2),(165,9.375,2),(166,9.725,2),(167,9.675,2),(168,0,2),(169,0,2),(170,8.875,2),(171,9.175,2),(172,8.95,2),(173,8.95,2),(174,9.05,2),(175,8.55,2),(176,0,2),(177,7.35,2),(178,0,2),(179,6.3,2),(180,0,2),(181,6.75,2),(182,9.05,2),(183,9.35,2),(184,8.85,2),(185,9.6,2),(186,7.05,2),(187,7.1,2),(188,8.7,2),(189,9.025,2),(190,9.025,2),(191,8.1,2),(192,8.875,2),(193,8.25,2),(194,7.7,2),(195,6.15,2),(196,7,2),(197,8.325,2),(198,0,2),(199,0,2),(200,8.35,2),(201,7.15,2),(202,8.45,2),(203,8.4,2),(204,7.75,2),(205,8.6,2),(206,9.425,2),(207,8.85,2),(208,8.35,2),(209,9.1,2),(210,8,2),(211,9.325,2),(212,8.35,2),(213,8.375,2),(83,7.825,3),(84,9.35,3),(85,9.625,3),(86,0,3),(87,8.7,3),(88,9.325,3),(89,9.3,3),(90,9.1,3),(91,8.6,3),(92,0,3),(93,9.5,3),(94,8.55,3),(95,8.9,3),(96,0,3),(97,9.425,3),(98,0,3),(99,0,3),(100,9.475,3),(101,9.2,3),(102,0,3),(103,9.5,3),(104,9.025,3),(105,0,3),(106,9.375,3),(107,9.35,3),(108,9.65,3),(109,0,3),(110,9.15,3),(111,0,3),(112,9.375,3),(113,0,3),(114,8.35,3),(115,8.1,3),(116,9.375,3),(117,0,3),(118,8.45,3),(119,9,3),(120,0,3),(121,8.5,3),(122,7.95,3),(123,9.4,3),(124,0,3),(125,8.925,3),(126,9.175,3),(127,6.55,3),(128,8.05,3),(129,9,3),(130,8.8,3),(131,7.2,3),(132,0,3),(133,5,3),(134,8.15,3),(135,8.575,3),(136,8.125,3),(137,8.3,3),(138,9.125,3),(139,6.3,3),(140,7.95,3),(141,7.375,3),(142,8.15,3),(143,7.3,3),(144,8.7,3),(145,8.675,3),(146,7.15,3),(147,6.3,3),(148,8.8,3),(149,8.725,3),(150,0,3),(151,0,3),(152,9.1,3),(153,7.625,3),(154,9.4,3),(155,9.35,3),(156,8.4,3),(157,0,3),(158,9.1,3),(159,7.85,3),(160,8.4,3),(161,0,3),(162,9.55,3),(163,0,3),(164,9.1,3),(165,9,3),(166,9.525,3),(167,9.675,3),(168,0,3),(169,8.825,3),(170,9.05,3),(171,0,3),(172,8.8,3),(173,9.1,3),(174,9.125,3),(175,8.475,3),(176,7.1,3),(177,0,3),(178,0,3),(179,7,3),(180,0,3),(181,0,3),(182,8.175,3),(183,9.2,3),(184,9.05,3),(185,9.35,3),(186,7.1,3),(187,7.325,3),(188,8.55,3),(189,9.25,3),(190,8.5,3),(191,8.35,3),(192,8.725,3),(193,8.4,3),(194,8,3),(195,6.5,3),(196,6.525,3),(197,8.3,3),(198,0,3),(199,0,3),(200,7.3,3),(201,7.95,3),(202,7.95,3),(203,7.25,3),(204,7.4,3),(205,8.175,3),(206,9.625,3),(207,8.575,3),(208,8.75,3),(209,8.95,3),(210,8.05,3),(211,9.15,3),(212,0,3),(213,7.95,3),(184,34.7,4),(184,8.025,0),(221,9.2,3),(214,8.85,0),(214,7.95,2),(214,9.35,3),(215,9.55,0),(215,8.25,1),(215,8.9,2),(215,9.15,3),(215,35.85,4),(216,9.65,0),(216,9.1,1),(216,9.2,2),(216,9.2,3),(216,37.15,4),(222,7.8,0),(222,5.2,1),(222,8,2),(222,7,3),(222,28,4),(223,9.2,0),(223,9.35,1),(223,8.65,2),(223,8.85,3),(223,36.05,4),(224,7,0),(224,4.2,1),(224,6.9,2),(224,6.7,3),(224,24.8,4),(225,8.4,0),(225,7.5,1),(225,7.2,2),(225,7.3,3),(225,30.400000000000002,4),(226,6.8,0),(226,6.3,1),(226,8.1,2),(226,7.35,3),(226,28.549999999999997,4),(217,9.15,0),(217,9,1),(217,9.25,2),(217,8.8,3),(217,36.2,4),(218,8.6,0),(218,8.65,1),(218,8.8,2),(218,8.9,3),(218,34.95,4),(219,9.1,0),(219,8.6,1),(219,8.7,2),(219,9.1,3),(219,35.5,4),(220,8,0),(220,8.6,1),(220,8.7,2),(220,8.2,3),(220,33.5,4);
/*!40000 ALTER TABLE `competitor_competitorresults` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meet`
--

DROP TABLE IF EXISTS `meet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meet` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meet`
--

LOCK TABLES `meet` WRITE;
/*!40000 ALTER TABLE `meet` DISABLE KEYS */;
INSERT INTO `meet` VALUES (10,'2013-05-17','CIF 2013'),(11,'2014-04-16','v Independents'),(12,'2014-05-01','DN v OLP');
/*!40000 ALTER TABLE `meet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meet_competitor`
--

DROP TABLE IF EXISTS `meet_competitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meet_competitor` (
  `meet_ID` int(11) NOT NULL,
  `competitors_ID` int(11) NOT NULL,
  PRIMARY KEY (`meet_ID`,`competitors_ID`),
  KEY `FK_MEET_COMPETITOR_competitors_ID` (`competitors_ID`),
  CONSTRAINT `FK_MEET_COMPETITOR_competitors_ID` FOREIGN KEY (`competitors_ID`) REFERENCES `competitor` (`ID`),
  CONSTRAINT `FK_MEET_COMPETITOR_meet_ID` FOREIGN KEY (`meet_ID`) REFERENCES `meet` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meet_competitor`
--

LOCK TABLES `meet_competitor` WRITE;
/*!40000 ALTER TABLE `meet_competitor` DISABLE KEYS */;
INSERT INTO `meet_competitor` VALUES (10,83),(10,84),(10,85),(10,86),(10,87),(10,88),(10,89),(10,90),(10,91),(10,92),(10,93),(10,94),(10,95),(10,96),(10,97),(10,98),(10,99),(10,100),(10,101),(10,102),(10,103),(10,104),(10,105),(10,106),(10,107),(10,108),(10,109),(10,110),(10,111),(10,112),(10,113),(10,114),(10,115),(10,116),(10,117),(10,118),(10,119),(10,120),(10,121),(10,122),(10,123),(10,124),(10,125),(10,126),(10,127),(10,128),(10,129),(10,130),(10,131),(10,132),(10,133),(10,134),(10,135),(10,136),(10,137),(10,138),(10,139),(10,140),(10,141),(10,142),(10,143),(10,144),(10,145),(10,146),(10,147),(10,148),(10,149),(10,150),(10,151),(10,152),(10,153),(10,154),(10,155),(10,156),(10,157),(10,158),(10,159),(10,160),(10,161),(10,162),(10,163),(10,164),(10,165),(10,166),(10,167),(10,168),(10,169),(10,170),(10,171),(10,172),(10,173),(10,174),(10,175),(10,176),(10,177),(10,178),(10,179),(10,180),(10,181),(10,182),(10,183),(10,184),(10,185),(10,186),(10,187),(10,188),(10,189),(10,190),(10,191),(10,192),(10,193),(10,194),(10,195),(10,196),(10,197),(10,198),(10,199),(10,200),(10,201),(10,202),(10,203),(10,204),(10,205),(10,206),(10,207),(10,208),(10,209),(10,210),(10,211),(10,212),(10,213),(12,214),(12,215),(12,216),(12,217),(12,218),(12,219),(12,220),(12,221),(12,222),(12,223),(12,224),(12,225),(12,226);
/*!40000 ALTER TABLE `meet_competitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `meet_teams`
--

DROP TABLE IF EXISTS `meet_teams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `meet_teams` (
  `meet_ID` int(11) DEFAULT NULL,
  `TEAMS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meet_teams`
--

LOCK TABLES `meet_teams` WRITE;
/*!40000 ALTER TABLE `meet_teams` DISABLE KEYS */;
INSERT INTO `meet_teams` VALUES (10,'Bonita Vista'),(10,'Otay Ranch'),(10,'OLP'),(10,'Del Norte'),(10,'High Tech High'),(10,'Canyon Crest'),(10,'Eastlake'),(10,'Fallbrook'),(10,'Chula Vista'),(10,'Olympian'),(10,'La Costa Canyon'),(10,'Other'),(10,'Hill Top'),(10,'Montgomery'),(10,'High Tech High CV'),(10,'Santana'),(10,'Torrey Pines'),(10,'San Ysidro'),(10,'Castle Park'),(10,'Westview'),(10,'Poway'),(10,'West Hills'),(10,'Mount Carmel'),(10,'Rancho Bernardo'),(10,'Grossmont'),(10,'Santana'),(10,'Valhalla'),(10,'Steele Canyon'),(10,'Granite Hills'),(10,'Otay'),(10,'Monte Vista'),(10,'El Capitan'),(10,'El Cajon'),(11,'Olympian'),(11,'La Costa Canyon'),(11,'Montgomery'),(11,'High Tech High'),(12,'Our Lady of Peace'),(12,'Del Norte');
/*!40000 ALTER TABLE `meet_teams` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-02 12:55:08
