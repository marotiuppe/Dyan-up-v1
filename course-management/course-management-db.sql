/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.3.13-MariaDB : Database - course-management
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) NOT NULL,
  `duration` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `created_at` date DEFAULT NULL,
  `modified_at` date DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_name` (`course_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `course_progress_level` */

DROP TABLE IF EXISTS `course_progress_level`;

CREATE TABLE `course_progress_level` (
  `level_id` int(11) NOT NULL AUTO_INCREMENT,
  `progress_level` varchar(100) NOT NULL,
  PRIMARY KEY (`level_id`),
  UNIQUE KEY `progress_level` (`progress_level`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `customers` */

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `cusId` int(11) NOT NULL AUTO_INCREMENT,
  `cusName` varchar(255) DEFAULT NULL,
  `cusAge` varchar(255) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  PRIMARY KEY (`cusId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `departmenttbl` */

DROP TABLE IF EXISTS `departmenttbl`;

CREATE TABLE `departmenttbl` (
  `departmentId` int(11) NOT NULL AUTO_INCREMENT,
  `deptName` varchar(255) DEFAULT NULL,
  `courses` int(11) DEFAULT NULL,
  `personId` int(11) DEFAULT NULL,
  PRIMARY KEY (`departmentId`),
  KEY `personId` (`personId`),
  CONSTRAINT `departmenttbl_ibfk_1` FOREIGN KEY (`personId`) REFERENCES `person` (`personId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `order_details` varchar(255) DEFAULT NULL,
  `cusId` int(11) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `person` */

DROP TABLE IF EXISTS `person`;

CREATE TABLE `person` (
  `personId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `mobileNumber` varchar(255) NOT NULL,
  `department` int(11) DEFAULT NULL,
  PRIMARY KEY (`personId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `contact_no` varchar(20) NOT NULL,
  `email_id` varchar(20) NOT NULL,
  `created_at` date DEFAULT NULL,
  `modified_at` date DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `user_cource_enrolment_maping` */

DROP TABLE IF EXISTS `user_cource_enrolment_maping`;

CREATE TABLE `user_cource_enrolment_maping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `status_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  KEY `user_id` (`user_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `user_cource_enrolment_maping_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `user_cource_enrolment_maping_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_cource_enrolment_maping_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `course_progress_level` (`level_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
