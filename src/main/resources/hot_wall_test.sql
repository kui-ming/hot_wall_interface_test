/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.7.15 : Database - hot_wall_test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章编号',
  `category_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `title` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `intro` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '简介',
  `content` text COLLATE utf8mb4_bin NOT NULL COMMENT '内容',
  `creation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `visitors` int(11) NOT NULL DEFAULT '0' COMMENT '浏览人数',
  `likes` int(11) NOT NULL DEFAULT '0' COMMENT '点赞人数',
  `picture_list` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片列表',
  `state` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='文章';

/*Data for the table `article` */

insert  into `article`(`id`,`category_id`,`user_id`,`title`,`intro`,`content`,`creation_time`,`update_time`,`visitors`,`likes`,`picture_list`,`state`) values 
(1,1,2,'测试1','这是简介','这是内容','2022-10-20 11:15:44','2022-10-24 15:58:55',5,2,NULL,NULL),
(2,1,1,'测试1','这是简介','这是内容','2022-10-20 19:43:34','2022-10-20 19:43:34',0,1,NULL,NULL),
(3,1,1,'测试2','这是简介','这是内容','2022-10-20 21:55:17','2022-10-20 21:55:17',0,0,NULL,NULL),
(5,1,1,'测试3','这是简介','这是内容','2022-10-20 22:21:00','2022-10-20 22:21:00',0,1,NULL,NULL);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别编号',
  `title` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `nums` int(11) NOT NULL DEFAULT '0' COMMENT '文章数量',
  `creation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='类别';

/*Data for the table `category` */

insert  into `category`(`id`,`title`,`nums`,`creation_time`,`update_time`,`state`) values 
(1,'优秀美文',4,'2022-10-20 19:40:29','2022-10-20 22:20:59',NULL),
(2,'情感',0,'2022-10-20 19:42:55','2022-10-20 19:53:15',NULL),
(3,'科学',0,'2022-10-20 19:43:02','2022-10-20 19:43:02',NULL),
(4,'热点',0,'2022-10-20 19:43:07','2022-10-20 19:43:07',NULL);

/*Table structure for table `collection` */

DROP TABLE IF EXISTS `collection`;

CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏编号',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `creation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='收藏';

/*Data for the table `collection` */

insert  into `collection`(`id`,`user_id`,`article_id`,`creation_time`) values 
(1,1,1,'2022-10-21 08:19:54'),
(5,1,3,'2022-10-21 08:48:46'),
(6,2,1,'2022-10-21 08:48:51'),
(7,2,3,'2022-10-21 08:48:54');

/*Table structure for table `comments` */

DROP TABLE IF EXISTS `comments`;

CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `reply_id` int(11) NOT NULL DEFAULT '-1' COMMENT '回复id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '内容',
  `creation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='评论';

/*Data for the table `comments` */

insert  into `comments`(`id`,`article_id`,`reply_id`,`user_id`,`content`,`creation_time`,`update_time`,`state`) values 
(1,1,-1,1,'1110','2022-10-21 11:33:06','2022-10-21 11:33:06',NULL),
(2,1,-1,1,'xyw','2022-10-21 11:33:53','2022-10-21 11:40:47',NULL),
(3,1,-1,1,'hahha','2022-10-21 11:34:10','2022-10-21 11:34:10',NULL),
(4,1,1,2,'？？？','2022-10-21 11:34:34','2022-10-21 11:34:34',NULL),
(5,1,1,2,'ooo','2022-10-21 11:39:30','2022-10-21 11:39:30',NULL);

/*Table structure for table `follow` */

DROP TABLE IF EXISTS `follow`;

CREATE TABLE `follow` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `follow_id` int(11) NOT NULL COMMENT '关注用户id',
  `creation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`user_id`,`follow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='关注';

/*Data for the table `follow` */

insert  into `follow`(`user_id`,`follow_id`,`creation_time`) values 
(1,2,'2022-10-23 16:10:59');

/*Table structure for table `likes` */

DROP TABLE IF EXISTS `likes`;

CREATE TABLE `likes` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `creation_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`user_id`,`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='点赞';

/*Data for the table `likes` */

insert  into `likes`(`user_id`,`article_id`,`creation_time`) values 
(1,1,'2022-10-24 15:58:55'),
(1,2,'2022-10-24 16:05:59'),
(1,5,'2022-10-24 16:06:09'),
(2,1,'2022-10-24 16:11:11');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `openid` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '开放ID',
  `nickname` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `img_path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像地址',
  `creation_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户';

/*Data for the table `user` */

insert  into `user`(`id`,`openid`,`nickname`,`img_path`,`creation_time`,`update_time`,`state`) values 
(1,'111','狂毛吊炸天',NULL,'2022-10-20 11:17:06','2022-10-24 17:07:10',NULL),
(2,'112','test',NULL,'2022-10-20 11:38:20','2022-10-20 11:38:20',NULL),
(3,'a1f2','卖核弹的小女孩',NULL,'2022-10-24 17:02:43','2022-10-24 17:02:43',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
