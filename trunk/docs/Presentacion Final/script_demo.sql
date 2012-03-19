DROP SCHEMA TEMPORE;

CREATE SCHEMA TEMPORE;

USE Tempore;

-- MySQL dump 10.13  Distrib 5.5.17, for Win32 (x86)
--
-- Host: localhost    Database: tempore
-- ------------------------------------------------------
-- Server version	5.5.17

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
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alert` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `userProjectId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userProjectId` (`userProjectId`),
  CONSTRAINT `FK_Alert_UserProject` FOREIGN KEY (`userProjectId`) REFERENCES `userproject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
INSERT INTO `alert` VALUES (1,'Analista','Alerta 1',1),(2,'PM','Alerta 2',1),(3,'Desarrollador','Alerta 2',2);
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `zip` varchar(15) DEFAULT NULL,
  `fiscalNumber` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `imageName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Fi Uba','Avda. Paseo Colón 850','Argentina','Buenos Aires',NULL,'30-37646985-2',NULL,NULL),(2,'Experian SRL','Avda. Corrientes 1332','Argentina','Buenos Aires',NULL,'30-12456476-6',NULL,NULL),(3,'Google','Alicia M. de Justo 350','Argentina','Buenos Aires',NULL,'30-37646985-2',NULL,NULL),(4,'Microsoft','Bouchard 101','Argentina','Buenos Aires',NULL,'30-12456476-6',NULL,NULL),(5,'Sony','Peña Loza125 2 Piso','Argentina','Buenos Aires',NULL,'30-37646985-2',NULL,NULL),(6,'Movistar','Edificio Central Park','Argentina','Buenos Aires',NULL,'30-12456476-6',NULL,NULL),(7,'Claro','Ingeniero Huergo 15','Argentina','Buenos Aires',NULL,'30-37646985-2',NULL,NULL),(8,'Personal','Eduardo Madero 50','Argentina','Buenos Aires',NULL,'30-12456476-6',NULL,NULL),(9,'Banco Santander','Mitre 357','Argentina','Buenos Aires',NULL,'30-37646985-2',NULL,NULL),(10,'Banco Frances','Reconquista 654','Argentina','Buenos Aires',NULL,'30-12456476-6',NULL,NULL);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientcontact`
--

DROP TABLE IF EXISTS `clientcontact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientcontact` (
  `clientId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`clientId`,`userId`),
  KEY `userId` (`userId`),
  KEY `clientId` (`clientId`),
  CONSTRAINT `FK_ClientContact_User` FOREIGN KEY (`userId`) REFERENCES `user` (`personId`),
  CONSTRAINT `FK_ClienteContacto_Cliente` FOREIGN KEY (`clientId`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientcontact`
--

LOCK TABLES `clientcontact` WRITE;
/*!40000 ALTER TABLE `clientcontact` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientcontact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configalert`
--

DROP TABLE IF EXISTS `configalert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configalert` (
  `id` bigint(20) NOT NULL,
  `name` bigint(20) DEFAULT NULL,
  `projectId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `projectId` (`projectId`),
  CONSTRAINT `FK_ConfigAlert_Project` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configalert`
--

LOCK TABLES `configalert` WRITE;
/*!40000 ALTER TABLE `configalert` DISABLE KEYS */;
/*!40000 ALTER TABLE `configalert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `country` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `zip` varchar(25) DEFAULT NULL,
  `imageName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Nicolás','García','15-6330-5220','garcian@gmail.com','Argentina','Tte JD Peron 4420 5°C - Almagro','1199','1317575837812_Nico.jpg'),(2,'Juan Pablo','Gigante','4567-9938','juanpablo.gigante@gmail.com','Argentina','Direccion - Florencio Varela','ADFAAFSD','1318188597703_Juan.jpg'),(3,'Ludmila Lis','Rinaudo','6445-9980','rinaudo.ludmila@gmail.com','Mexico','Direccion - Mexico DF',NULL,'1318189046500_LudmilaRinaudo.png'),(11,'Juan','Perez','4000-0000','perez@gmail.com','Argentina','',NULL,NULL),(12,'Jose','Lopez','4111-1111','lopez@gmail.com','Argentina','',NULL,NULL),(13,'Luis','Gutierrez','4222-2222','gutierrez@gmail.com','Argentina','',NULL,NULL),(14,'Emilio','Martinez','4333-3333','martinez@gmai1.com','Argentina','','',NULL),(20,'Mateo','Andreu','4444-4444','tcs1@gmail.com','Argentina','',NULL,NULL),(21,'Andres','Gomez','4555-5555','tcs2@gmail.com','Argentina','','',NULL),(22,'Administrador','-',NULL,'ngarcia@gmail.com','Argentina',NULL,NULL,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL,
  `projectStateId` bigint(20) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `initDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `budget` bigint(20) DEFAULT NULL,
  `clientId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `clientId` (`clientId`),
  KEY `projectStateId` (`projectStateId`),
  CONSTRAINT `FK_Project_Client` FOREIGN KEY (`clientId`) REFERENCES `client` (`id`),
  CONSTRAINT `FK_Project_ProjectState` FOREIGN KEY (`projectStateId`) REFERENCES `projectstate` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Tempore',3,'Trabajo Profesional para la FI-UBA','2011-06-09','2012-03-01',0,1),(2,'Google+',2,'Proyecto ','2012-01-01','2013-07-01',77000,3),(3,'Contabilidad',1,'Trabajo demo','2012-02-15','2013-01-01',150000,4),(4,'Diseño',2,'Trabajo demo','2012-06-01','2014-07-01',1200000,5);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projectstate`
--

DROP TABLE IF EXISTS `projectstate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projectstate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectstate`
--

LOCK TABLES `projectstate` WRITE;
/*!40000 ALTER TABLE `projectstate` DISABLE KEYS */;
INSERT INTO `projectstate` VALUES (1,'Adquirido','Proyecto listo para empezar'),(2,'Iniciado','Proyecto ya esta iniciado'),(3,'UAT','Proyecto en Aceptacion por el Usuario'),(4,'Cerrado','Proyecto Cerrado'),(5,'Cancelado','Proyecto Cancelado'),(6,'Suspendido','Proyecto Suspendido');
/*!40000 ALTER TABLE `projectstate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` bigint(20) DEFAULT NULL,
  `x` bigint(20) DEFAULT NULL,
  `y` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportproject`
--

DROP TABLE IF EXISTS `reportproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reportproject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectId` bigint(20) NOT NULL,
  `reportId` bigint(20) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `projectId` (`projectId`),
  KEY `reportId` (`reportId`),
  CONSTRAINT `FK_ReportProject_Project` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`),
  CONSTRAINT `FK_ReportProject_Report` FOREIGN KEY (`reportId`) REFERENCES `report` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportproject`
--

LOCK TABLES `reportproject` WRITE;
/*!40000 ALTER TABLE `reportproject` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportvisibility`
--

DROP TABLE IF EXISTS `reportvisibility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reportvisibility` (
  `userProjectId` bigint(20) NOT NULL,
  `reportProjectId` bigint(20) NOT NULL,
  PRIMARY KEY (`userProjectId`,`reportProjectId`),
  KEY `reportProjectId` (`reportProjectId`),
  KEY `userProjectId` (`userProjectId`),
  CONSTRAINT `FK_ReportProjectUserProject_ReportProject` FOREIGN KEY (`reportProjectId`) REFERENCES `reportproject` (`id`),
  CONSTRAINT `FK_ReportProjectUserProject_UserProject` FOREIGN KEY (`userProjectId`) REFERENCES `userproject` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportvisibility`
--

LOCK TABLES `reportvisibility` WRITE;
/*!40000 ALTER TABLE `reportvisibility` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportvisibility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Administrador'),(2,'Usuario'),(3,'Cliente');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roleuserproject`
--

DROP TABLE IF EXISTS `roleuserproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roleuserproject` (
  `roleId` bigint(20) NOT NULL,
  `userProjectId` bigint(20) NOT NULL,
  PRIMARY KEY (`roleId`,`userProjectId`),
  KEY `userProjectId` (`userProjectId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `FK_RoleUserProject_UserProject` FOREIGN KEY (`userProjectId`) REFERENCES `userproject` (`id`),
  CONSTRAINT `FK_RolUsuario_Rol` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roleuserproject`
--

LOCK TABLES `roleuserproject` WRITE;
/*!40000 ALTER TABLE `roleuserproject` DISABLE KEYS */;
INSERT INTO `roleuserproject` VALUES (2,1),(1,2),(3,3);
/*!40000 ALTER TABLE `roleuserproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectId` bigint(20) NOT NULL,
  `taskTypeId` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `taskId` bigint(20) DEFAULT NULL,
  `budget` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `taskTypeId` (`taskTypeId`),
  KEY `projectId` (`projectId`),
  KEY `taskId` (`taskId`),
  CONSTRAINT `FK_Tarea_TipoTarea` FOREIGN KEY (`taskTypeId`) REFERENCES `tasktype` (`id`),
  CONSTRAINT `FK_Task_Project` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`),
  CONSTRAINT `FK_Task_Task` FOREIGN KEY (`taskId`) REFERENCES `task` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,1,2,'Investigación','Investigacion de las herramientas a utilizar',NULL,0),(2,1,4,'Seguimiento y Administración','Reuniones de Avance y de toma de decisiones',NULL,0),(3,1,1,'Análisis','Análisis Funcional de la aplicacion',NULL,0),(4,1,2,'Desarrollo','Implementación de la aplicación',NULL,0),(5,1,5,'Pruebas','QC a la aplicación y Reporte de Errores',NULL,0),(6,1,3,'Soporte','Soporte a la aplicación',NULL,0),(11,1,2,'Framework SmartGWT','Nuevo Framework SmartGWT',1,86400000),(12,1,2,'Hibernate','Frameworks para la persistencia de datos',1,0),(13,1,2,'Reportes','Frameworks para Reportes - Visualization',1,64800000),(14,1,2,'Dozer','Frameworks para Mapeos entre Entities y DTOs',1,43200000),(21,1,4,'Reuniones de Grupo','Reuniones para definición de siguientes pasos en el proyecto',2,0),(22,1,4,'Reuniones con Tutor','Renuniones para motivación y compromiso con el proyecto',2,0),(23,1,1,'Documentación','Documentacion de la aplicacion',2,12351),(24,1,4,'Presentación','Presentacion de la aplicacion',2,12351),(31,1,1,'Requerimientos','Listado de Requerimientos',3,12351),(32,1,1,'Reglas de Negocio','Reglas de Negocio',3,12351),(33,1,1,'CUSO','Casos de Usos',3,12351),(34,1,1,'Base de Datos','Analisis de la estructura de BBDD',3,12351),(35,1,1,'Diseño Pantallas','Diseño de Secuencias y Pantallas',3,12351),(36,1,1,'Arquitectura','Analisis de la Arquitectura',3,12351),(41,1,2,'Back End','Implementacion de Persistencia de Datos',4,0),(42,1,2,'Servicios','Implementacion de Servicios',4,0),(43,1,2,'Front End','Implementacion de logica de pantallas',4,0),(44,1,2,'Reportes','Implementacion de Reportes - Visualization',4,0),(45,1,2,'Interfaces ABMs','Implementacion de Framework de ABMs',4,0),(46,1,2,'Diseño','Implementacion del Diseño de Pantallas',4,0),(51,1,5,'QC DAOs','Pruebas unitarias de los DAOs',5,12351),(52,1,5,'QC Funcional','Pruebas Funcionales',5,12351),(53,1,2,'Correcciones de Bugs','Correcciones de Bugs encontrados',5,12351),(54,1,5,'Revisión de Bugs','Revision de Bugs Reportados como corregidos',5,12351),(200,2,1,'Análisis de proyecto','Análisis',NULL,72000000),(201,2,2,'Desarrollo de aplicación','Desarrollo de los módulos de la aplicación',NULL,0),(202,2,3,'Soporte','Soporte de la aplicación, post implementación',NULL,43200000),(203,2,4,'Administración de proyecto','Seguimiento de proyecto',NULL,144000000),(204,2,5,'Control de calidad','Quality Control',NULL,54000000),(300,3,1,'Análisis de requerimientos','Relevamiento y Análisis de requerimientos',NULL,72000000),(301,3,2,'Desarrollo de la aplicación','Desarrollo de los módulos',NULL,180000000),(302,3,3,'Soporte y bugs','Soporte y corrección de errores',NULL,50400000),(303,3,4,'Planificación y seguimiento','Planificación y seguimiento',NULL,126000000),(304,3,5,'Pruebas','Pruebas unitarias y de aceptación',NULL,54000000),(400,4,1,'Especificación requerimientos','Análisis de la aplicación',NULL,108000000),(401,4,2,'Desarrollo de aplicación','Dessarrollo',NULL,180000000),(403,4,4,'Administración y seguimiento','Adminstración del proyecto e informes',NULL,43200000),(404,4,5,'Pruebas','Quality Control',NULL,54000000);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasktype`
--

DROP TABLE IF EXISTS `tasktype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasktype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasktype`
--

LOCK TABLES `tasktype` WRITE;
/*!40000 ALTER TABLE `tasktype` DISABLE KEYS */;
INSERT INTO `tasktype` VALUES (1,'Análisis','Análisis Funcional'),(2,'Desarrollo','Implementación y Desarrollo'),(3,'Soporte','Soporte al proyecto'),(4,'Administración','Administración del Proyecto'),(5,'QC','Quality Control');
/*!40000 ALTER TABLE `tasktype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taskuser`
--

DROP TABLE IF EXISTS `taskuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taskuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `taskId` bigint(20) NOT NULL,
  `hourCount` int(11) NOT NULL,
  `date` date NOT NULL,
  `comment` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `taskId` (`taskId`),
  KEY `userId` (`userId`),
  CONSTRAINT `FK_TareaUsuario_Tarea` FOREIGN KEY (`taskId`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_TaskUser_User` FOREIGN KEY (`userId`) REFERENCES `user` (`personId`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taskuser`
--

LOCK TABLES `taskuser` WRITE;
/*!40000 ALTER TABLE `taskuser` DISABLE KEYS */;
INSERT INTO `taskuser` VALUES (44,2,31,10800000,'2012-03-09',NULL),(46,2,35,10800000,'2012-03-12',NULL),(52,1,200,7200000,'2012-03-08',NULL),(53,1,400,7200000,'2012-03-12','Especificación funcional'),(54,1,401,10800000,'2012-03-13',NULL),(57,1,41,14400000,'2012-03-16','Página de inicio del backend'),(58,1,42,12600000,'2012-03-16','Desarrollo de los principales servicios\n'),(59,1,45,21600000,'2012-03-15','ABM de usuarios\nABM de clientes'),(60,1,11,28800000,'2012-03-05','Investigación de como crear proyectos SamrtGWT con otras tecnologías'),(61,1,11,21600000,'2012-03-06','Investigación de componentes SmartGWT'),(62,1,12,3600000,'2012-03-06','Integración con Hibernate'),(63,1,14,14400000,'2012-03-07','Pruebas'),(64,1,14,10800000,'2012-03-08',NULL),(65,1,13,10800000,'2012-03-09','Integración de reportes'),(66,1,14,7200000,'2012-03-09',NULL),(67,1,46,3600000,'2012-03-15','Diseño de ABMs'),(68,3,11,3600000,'2012-03-05','Investigación de componentes'),(69,3,23,14400000,'2012-03-05','Documentación para el cliente'),(70,3,24,7200000,'2012-03-19','Presentación al cliente'),(76,3,54,7200000,'2012-03-12',NULL),(78,3,54,7200000,'2012-03-14',NULL),(79,3,31,10800000,'2012-03-05',NULL),(80,3,32,10800000,'2012-03-06',NULL),(81,3,31,14400000,'2012-03-06',NULL),(82,3,31,14400000,'2012-03-08','Especificación de CUs'),(83,2,31,21600000,'2012-03-05',NULL),(84,2,31,10800000,'2012-03-06',NULL),(85,2,34,7200000,'2012-03-06','Diseño de BD'),(86,2,35,3600000,'2012-03-06','Diseño de pantallas'),(87,2,35,10800000,'2012-03-07','Diseño de pantallas'),(88,2,33,10800000,'2012-03-07','Casos de Uso'),(89,2,33,10800000,'2012-03-08','Desarrollo de CUs'),(90,2,32,7200000,'2012-03-08','Relevamiento de las reglas de negocio'),(91,2,23,7200000,'2012-03-08','Documentación'),(92,2,23,7200000,'2012-03-07','Documentación'),(93,2,23,3600000,'2012-03-06',NULL),(94,2,23,3600000,'2012-03-09',NULL),(97,2,46,10800000,'2012-03-14',NULL),(98,2,41,10800000,'2012-03-14',NULL),(99,2,21,7200000,'2012-03-05','Reuniones de avance'),(100,2,21,3600000,'2012-03-06','Reunión de avance'),(101,1,21,3600000,'2012-03-08',NULL),(103,1,43,10800000,'2012-03-12',NULL),(104,1,43,14400000,'2012-03-14',NULL),(105,1,42,14400000,'2012-03-14',NULL),(106,1,41,14400000,'2012-03-12',NULL),(112,3,200,7200000,'2012-03-07',NULL),(113,3,201,7200000,'2012-03-07',NULL),(116,2,46,18000000,'2012-03-13','Usabilidad'),(117,3,43,14400000,'2012-03-12','Panel de tareas'),(118,3,43,18000000,'2012-03-13','Panel de tareas'),(119,3,43,21600000,'2012-03-15','Panel de tareas y servicios'),(120,3,41,18000000,'2012-03-16',NULL),(121,3,46,14400000,'2012-03-14',NULL),(122,3,21,7200000,'2012-03-07','Reuniones de avance'),(123,3,21,7200000,'2012-03-14',NULL),(124,3,22,3600000,'2012-03-13',NULL),(125,3,23,3600000,'2012-03-12','Reporte para gerencia'),(126,3,21,3600000,'2012-03-09',NULL),(127,3,54,7200000,'2012-03-13',NULL),(128,3,52,7200000,'2012-03-15','Pruebas funcionales con el usuario'),(129,3,54,10800000,'2012-03-16',NULL),(130,3,52,21600000,'2012-03-19',NULL),(131,1,201,3600000,'2012-03-13',NULL),(132,1,42,14400000,'2012-03-13',NULL),(133,1,42,10800000,'2012-03-07','Servicios'),(134,1,41,7200000,'2012-03-08',NULL),(135,3,45,3600000,'2012-03-12',NULL),(136,3,46,14400000,'2012-03-09',NULL),(137,2,24,7200000,'2012-03-16',NULL),(138,2,24,7200000,'2012-03-15','Presentación para la entrega'),(139,2,23,3600000,'2012-03-14',NULL),(140,2,200,10800000,'2012-03-13',NULL),(141,2,203,7200000,'2012-03-15','Seguimiento de proyecto'),(142,2,400,3600000,'2012-03-09',NULL),(144,2,200,7200000,'2012-03-09',NULL),(145,3,202,7200000,'2012-03-09',NULL),(146,3,201,14400000,'2012-03-08','Desarrollo de plugin de integración con G+');
/*!40000 ALTER TABLE `taskuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tempcounter`
--

DROP TABLE IF EXISTS `tempcounter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tempcounter` (
  `userId` bigint(20) NOT NULL,
  `timeIni` bigint(20) DEFAULT NULL,
  `control` int(11) DEFAULT NULL,
  `timeAcumulated` bigint(20) DEFAULT NULL,
  `taskId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  KEY `userId` (`userId`),
  KEY `FK25AC6F881D0E6D08` (`taskId`),
  CONSTRAINT `FK25AC6F881D0E6D08` FOREIGN KEY (`taskId`) REFERENCES `task` (`id`),
  CONSTRAINT `FK_TempCounter_User` FOREIGN KEY (`userId`) REFERENCES `user` (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tempcounter`
--

LOCK TABLES `tempcounter` WRITE;
/*!40000 ALTER TABLE `tempcounter` DISABLE KEYS */;
/*!40000 ALTER TABLE `tempcounter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `personId` bigint(20) NOT NULL,
  `state` char(1) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `admin` char(1) DEFAULT NULL,
  PRIMARY KEY (`personId`),
  KEY `personId` (`personId`),
  CONSTRAINT `FK_User_Person` FOREIGN KEY (`personId`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'A','ngarcia','cRDtpNCeBiql5KOQsKVyrA0sAiA=','N'),(2,'A','jgigante','cRDtpNCeBiql5KOQsKVyrA0sAiA=','N'),(3,NULL,'lrinaudo','cRDtpNCeBiql5KOQsKVyrA0sAiA=','N'),(11,NULL,'jperez','cRDtpNCeBiql5KOQsKVyrA0sAiA=','N'),(12,NULL,'jlopez','cRDtpNCeBiql5KOQsKVyrA0sAiA=','N'),(13,NULL,'lgutierrez','cRDtpNCeBiql5KOQsKVyrA0sAiA=','N'),(20,NULL,'mandreu','cRDtpNCeBiql5KOQsKVyrA0sAiA=','N'),(21,'A','agomez','cRDtpNCeBiql5KOQsKVyrA0sAiA=','N'),(22,NULL,'admin','cRDtpNCeBiql5KOQsKVyrA0sAiA=','S');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userproject`
--

DROP TABLE IF EXISTS `userproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userproject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `projectId` bigint(20) NOT NULL,
  `owner` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `projectId` (`projectId`),
  KEY `userId` (`userId`),
  CONSTRAINT `FK_UserProject_Project` FOREIGN KEY (`projectId`) REFERENCES `project` (`id`),
  CONSTRAINT `FK_UserProject_User` FOREIGN KEY (`userId`) REFERENCES `user` (`personId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userproject`
--

LOCK TABLES `userproject` WRITE;
/*!40000 ALTER TABLE `userproject` DISABLE KEYS */;
INSERT INTO `userproject` VALUES (1,1,1,1),(2,2,1,0),(3,3,1,0),(4,1,2,0),(5,3,2,1),(6,3,3,0),(7,2,3,1),(8,1,4,0),(9,2,4,1),(10,11,4,0),(11,12,4,0),(12,13,4,0),(13,2,2,0);
/*!40000 ALTER TABLE `userproject` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-03-17 19:59:25
