/*
SQLyog Ultimate v8.55 
MySQL - 5.5.27 : Database - onlineshopping
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`onlineshopping` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `onlineshopping`;

/*Table structure for table `customer_master` */

DROP TABLE IF EXISTS `customer_master`;

CREATE TABLE `customer_master` (
  `customerId` int(11) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `customerContactNo` varchar(255) DEFAULT NULL,
  `customerEmail` varchar(255) DEFAULT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `isActive` varchar(5) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`customerId`),
  UNIQUE KEY `UK_dewdo083iro0t8dk7n1gsetsn` (`customerEmail`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `customer_master` */

insert  into `customer_master`(`customerId`,`createdDate`,`customerContactNo`,`customerEmail`,`customerName`,`isActive`) values (1,'2018-09-28 15:24:32','9370244805','isha.tiwari@yash.com','Isha Tiwari','Y'),(2,'2018-10-01 11:26:38','9370244805','charu.dubey@yash.com','Charu Dubey','Y'),(3,'2018-10-01 13:39:24','9661188224','ruchi.singh@yash.com','Ruchi Singh','Y');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `createdDate` datetime DEFAULT NULL,
  `paidAmount` double DEFAULT NULL,
  `paymentMethod` varchar(255) DEFAULT NULL,
  `customerIdFk` int(11) DEFAULT NULL,
  `productIdFk` int(11) DEFAULT NULL,
  `orderQuantity` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  KEY `FK3c60wntrpslp8miire0lambp7` (`customerIdFk`),
  KEY `FKn7n6533m3k6vd8dva2y0j9jn9` (`productIdFk`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `orders` */

insert  into `orders`(`orderId`,`createdDate`,`paidAmount`,`paymentMethod`,`customerIdFk`,`productIdFk`,`orderQuantity`) values (1,'2018-10-01 18:05:34',20000,'Cash',1,1,1),(3,'2018-10-10 12:27:10',12000,'Cash',2,1,1),(4,'2018-10-11 11:23:05',12000,'Cash',2,1,1);

/*Table structure for table `product_master` */

DROP TABLE IF EXISTS `product_master`;

CREATE TABLE `product_master` (
  `productId` int(11) NOT NULL AUTO_INCREMENT,
  `availableQuantity` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `productDesc` varchar(255) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`productId`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `product_master` */

insert  into `product_master`(`productId`,`availableQuantity`,`price`,`productDesc`,`productName`) values (1,15,12000,'LG Freez','LG Freez'),(3,10,20000,'iPhone X','iPhone X');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
