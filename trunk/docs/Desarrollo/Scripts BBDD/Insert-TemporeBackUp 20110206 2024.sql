-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.27-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


INSERT INTO `client` (`id`,`name`) VALUES 
 (1,'IBM'),
 (2,'Coca-Cola');
INSERT INTO `contact` (`personId`,`contactTypeId`) VALUES 
 (4,1);
INSERT INTO `contacttype` (`id`,`description`,`name`) VALUES 
 (1,'Gerente IT','Gerente del area de IT'),
 (2,'Secretaria','Secretaria'),
 (3,'Gerente Neg','Gerente de Negocios');
INSERT INTO `person` (`id`,`name`,`lastname`,`phone`,`address`,`state`,`country`) VALUES 
 (1,'Nicolas','Garcia','15-6330-5220','Peron 4420 5C - Almagro','Buenos Aires','Argentina'),
 (2,'Juan Pabloi','Gigante','15-3395-3452','Alem 682 5','Buenos Aires','Argentina'),
 (3,'Ludmila Lis','Rinaudo','15-4252-1626','Av Juan de Garay 225','Buenos Aires','Argentina'),
 (4,'Juan','Perez','911','Calle','State','Argentina');
INSERT INTO `project` (`id`,`name`,`projectStateId`) VALUES 
 (1,'Tempore',1),
 (2,'Softmart',1);
INSERT INTO `projectclient` (`projectId`,`clientId`) VALUES 
 (1,1),
 (2,1);
INSERT INTO `projectstate` (`id`,`name`,`description`) VALUES 
 (1,'Abierto','Estado del Proyecto'),
 (2,'Cerrado','Estado del proyecto cerrado');
INSERT INTO `task` (`id`,`projectId`,`taskTypeId`,`name`,`description`,`taskStateId`) VALUES 
 (1,1,1,'Tarea name','Descripcionm de la tarea',1);
INSERT INTO `taskstate` (`id`,`name`,`description`) VALUES 
 (1,'TareaTest','Tarea Iniciada'),
 (2,'Finalizada','Tarea finalizada'),
 (3,'QC','Tarea Lista para ser probada por QC'),
 (4,'Pendiente','Tarea Pendientes a ser tomada por alguien'),
 (5,'Reservada','Tarea reservada por algun usuario'),
 (6,'Nuevo','Nuevo estato');
INSERT INTO `tasktype` (`id`,`name`,`description`) VALUES 
 (1,'Desarrollo','Tarea de Desarrollo'),
 (2,'Control de Calidad','Tarea de Control de calidad (QC)');
INSERT INTO `user` (`personId`) VALUES 
 (1),
 (2),
 (3);



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
