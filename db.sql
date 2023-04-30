/*
SQLyog Community Edition- MySQL GUI v7.15 
MySQL - 5.5.29 : Database - onlinejobportal
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`onlinejobportal` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `onlinejobportal`;

/*Table structure for table `jobdetails` */

DROP TABLE IF EXISTS `jobdetails`;

CREATE TABLE `jobdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobTitle` varchar(255) NOT NULL,
  `jobDescription` varchar(255) NOT NULL,
  `keywords` varchar(255) NOT NULL,
  `createdby` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `experience` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `cutoff` int(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `jobdetails` */

insert  into `jobdetails`(`id`,`jobTitle`,`jobDescription`,`keywords`,`createdby`,`company`,`experience`,`location`,`cutoff`) values (1,'java developer','java,oops,dbms','java','1','infosys','4','hyderabad',65);

/*Table structure for table `userdetails` */

DROP TABLE IF EXISTS `userdetails`;

CREATE TABLE `userdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `resume` varchar(655) NOT NULL,
  `type` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `profileimage` varchar(655) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `userdetails` */

insert  into `userdetails`(`id`,`firstName`,`lastName`,`emailAddress`,`password`,`resume`,`type`,`mobile`,`company`,`profileimage`,`location`) values (1,'shiva','krishna','shiva@gmail.com','123','','recruiter','9898989898','infosys','',''),(2,'geeta','ak','geeta@gmail.com','123','C:\\Users\\WELCOME\\Documents\\NetBeansProjects\\OnlineJobPortal\\build\\webupload\\1674623302717wresume.docx','user','6789876789','','','hyderabad');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
