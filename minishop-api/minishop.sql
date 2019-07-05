/*
Navicat MySQL Data Transfer

Source Server         : wwx
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : minishop

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2019-04-06 15:57:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `persistent_logins`
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('admin', '4q2pgtT+GqcQyU6gOmz08Q==', 'tBbP2f6C59+ycC5GrLGZ/g==', '2019-04-06 06:29:40');
INSERT INTO `persistent_logins` VALUES ('admin', 'Mhf6aTW5q/LV2FqucdVFnQ==', '8COcmBNyEwMZg1wKdGiuxw==', '2019-04-06 04:26:13');

-- ----------------------------
-- Table structure for `tb_area`
-- ----------------------------
DROP TABLE IF EXISTS `tb_area`;
CREATE TABLE `tb_area` (
  `area_id` int(5) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(200) NOT NULL,
  `area_desc` varchar(1000) DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`area_id`),
  UNIQUE KEY `UK_AREA` (`area_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_area
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_head_line`
-- ----------------------------
DROP TABLE IF EXISTS `tb_head_line`;
CREATE TABLE `tb_head_line` (
  `line_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `enable_status` int(11) DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `line_img` varchar(255) DEFAULT NULL,
  `line_link` varchar(255) DEFAULT NULL,
  `line_name` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  PRIMARY KEY (`line_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_head_line
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_local_auth`
-- ----------------------------
DROP TABLE IF EXISTS `tb_local_auth`;
CREATE TABLE `tb_local_auth` (
  `local_auth_id` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`local_auth_id`),
  KEY `FKkxw2mrf0l2isknfmf25ypg1n` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_local_auth
-- ----------------------------
INSERT INTO `tb_local_auth` VALUES ('1', '2019-03-17 20:09:34', '2019-03-17 20:09:37', '123456', 'admin', '1');

-- ----------------------------
-- Table structure for `tb_order`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_inumber` int(100) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `person_info_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `shop_id` int(11) NOT NULL,
  `enable_status` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `fk_order_person` (`person_info_id`),
  KEY `fk_product_id` (`product_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_order
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_person_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_person_info`;
CREATE TABLE `tb_person_info` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `gender` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `profile_img` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `usre_type` int(2) NOT NULL,
  `username` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_person_info
-- ----------------------------
INSERT INTO `tb_person_info` VALUES ('1', '2019-03-05 04:26:54', '15283840975@163.com', '0', '男', '2019-03-05 04:26:54', '吴文锡', null, '1', 'wwx', '123456', null);

-- ----------------------------
-- Table structure for `tb_product`
-- ----------------------------
DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `enable_status` int(11) DEFAULT NULL,
  `img_address` varchar(255) DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `normal_price` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `product_desc` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `promotion_price` varchar(255) DEFAULT NULL,
  `product_category_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `FKqmq0wn52pkw7lqaj1s4xfmxlg` (`product_category_id`),
  KEY `FKqv4x6mruslhyp1o8t2r3gb3a6` (`shop_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product
-- ----------------------------
INSERT INTO `tb_product` VALUES ('1', '2019-02-23 10:59:35', '1', '/upload/item/shop/1/2019022318593512446.jpg', '2019-02-23 10:59:35', '35', null, '原味咖啡受到广大消费者的喜爱，是本店最畅销的一款饮品', '香浓原味咖啡', '35', '1', '1');
INSERT INTO `tb_product` VALUES ('2', '2019-04-06 05:25:08', '0', '/upload/item/shop/3/2019040613502218813.jpg', '2019-04-06 06:19:35', '64.8', null, '学习Java语言编程重要的一本书籍，二手、全新', '《Java编程思想》', '108.00', '2', '3');

-- ----------------------------
-- Table structure for `tb_product_category`
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_category`;
CREATE TABLE `tb_product_category` (
  `product_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `product_category_name` varchar(255) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_category_id`),
  KEY `FKsok858agrnqsg41plrtked78g` (`shop_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_category
-- ----------------------------
INSERT INTO `tb_product_category` VALUES ('1', '2019-02-21 10:34:52', '38', '原味咖啡', '1');
INSERT INTO `tb_product_category` VALUES ('2', '2019-03-30 04:12:36', '87', 'java书籍', '3');
INSERT INTO `tb_product_category` VALUES ('3', '2019-03-30 04:52:01', '1', '高等数学', '3');
INSERT INTO `tb_product_category` VALUES ('4', '2019-03-30 04:55:00', '50', '大学英语', '3');
INSERT INTO `tb_product_category` VALUES ('5', '2019-03-30 04:56:22', '10', '线性代数', '3');

-- ----------------------------
-- Table structure for `tb_product_img`
-- ----------------------------
DROP TABLE IF EXISTS `tb_product_img`;
CREATE TABLE `tb_product_img` (
  `product_img_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `img_address` varchar(255) DEFAULT NULL,
  `img_desc` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_img_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_product_img
-- ----------------------------
INSERT INTO `tb_product_img` VALUES ('1', '2019-02-23 10:59:36', '/upload/item/shop/1/2019022318593618829.jpg', null, null, '1');
INSERT INTO `tb_product_img` VALUES ('2', '2019-02-23 10:59:36', '/upload/item/shop/1/2019022318593615112.jpg', null, null, '1');
INSERT INTO `tb_product_img` VALUES ('3', '2019-04-06 06:19:47', '/upload/item/shop/3/2019040614194718562.jpg', null, null, '2');
INSERT INTO `tb_product_img` VALUES ('4', '2019-04-06 06:19:48', '/upload/item/shop/3/2019040614194715042.jpg', null, null, '2');
INSERT INTO `tb_product_img` VALUES ('5', '2019-04-06 06:19:48', '/upload/item/shop/3/2019040614194810493.jpg', null, null, '2');

-- ----------------------------
-- Table structure for `tb_shop`
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop`;
CREATE TABLE `tb_shop` (
  `shop_id` int(10) NOT NULL AUTO_INCREMENT,
  `priority` int(3) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `enable_status` int(2) NOT NULL DEFAULT '0',
  `last_edit_time` datetime DEFAULT NULL,
  `phone` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `advice` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_desc` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_img` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `shop_name` varchar(256) COLLATE utf8_unicode_ci NOT NULL,
  `owner_id` int(10) NOT NULL COMMENT '店铺创建人',
  `shop_category_id` int(11) DEFAULT NULL,
  `area_id` int(5) DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_profile` (`owner_id`),
  KEY `fk_shop_area` (`area_id`),
  KEY `fk_shop_shopcate` (`shop_category_id`),
  CONSTRAINT `fk_shop_area` FOREIGN KEY (`area_id`) REFERENCES `tb_area` (`area_id`),
  CONSTRAINT `fk_shop_profile` FOREIGN KEY (`owner_id`) REFERENCES `tb_person_info` (`user_id`),
  CONSTRAINT `fk_shop_shopcate` FOREIGN KEY (`shop_category_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_shop
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_shop_category`
-- ----------------------------
DROP TABLE IF EXISTS `tb_shop_category`;
CREATE TABLE `tb_shop_category` (
  `shop_category_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `last_edit_time` datetime DEFAULT NULL,
  `priority` int(2) NOT NULL DEFAULT '0',
  `shop_category_name` varchar(100) NOT NULL DEFAULT '',
  `shop_category_desc` varchar(1000) DEFAULT '',
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`shop_category_id`),
  KEY `fk_shop_category_self` (`parent_id`),
  CONSTRAINT `fk_shop_category_self` FOREIGN KEY (`parent_id`) REFERENCES `tb_shop_category` (`shop_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_shop_category
-- ----------------------------
INSERT INTO `tb_shop_category` VALUES ('1', '2019-03-19 14:41:31', '2019-03-19 14:41:31', '0', '美食饮品', '美食饮品', null);
INSERT INTO `tb_shop_category` VALUES ('2', '2019-03-19 14:41:31', '2019-03-19 14:41:31', '0', '二手市场', '二手市场', null);
INSERT INTO `tb_shop_category` VALUES ('3', '2019-03-19 14:41:31', '2019-03-19 14:41:31', '0', '租赁市场', '租赁市场', null);
INSERT INTO `tb_shop_category` VALUES ('4', '2019-03-19 14:41:31', '2019-03-19 14:41:31', '0', '休闲娱乐', '休闲娱乐', null);
INSERT INTO `tb_shop_category` VALUES ('5', '2019-03-19 14:41:31', '2019-03-19 14:41:31', '0', '美容美发', '美容美发', null);
INSERT INTO `tb_shop_category` VALUES ('6', '2019-03-19 14:41:31', '2019-03-19 14:41:31', '0', '培训教育', '培训教育', null);
INSERT INTO `tb_shop_category` VALUES ('7', '2019-03-19 14:41:31', '2019-03-19 14:41:31', '0', '运动健身', '运动健身', null);
INSERT INTO `tb_shop_category` VALUES ('8', '2019-03-19 14:41:31', '2019-03-19 14:41:31', '0', '其他', '其他', null);
INSERT INTO `tb_shop_category` VALUES ('9', '2019-02-21 09:58:07', '2019-02-21 09:58:07', '0', '咖啡', '咖啡', '1');
INSERT INTO `tb_shop_category` VALUES ('10', '2019-02-21 09:58:07', '2019-02-21 09:58:07', '0', '奶茶', '奶茶', '1');
INSERT INTO `tb_shop_category` VALUES ('11', '2019-02-21 09:58:07', '2019-02-21 09:58:07', '0', '小吃', '小吃', '1');
INSERT INTO `tb_shop_category` VALUES ('12', '2019-02-21 09:58:07', '2019-02-21 09:58:07', '0', '甜品', '甜品', '1');
INSERT INTO `tb_shop_category` VALUES ('13', '2019-02-21 10:00:04', '2019-02-21 10:00:04', '0', '书籍', '书籍', '2');
INSERT INTO `tb_shop_category` VALUES ('14', '2019-02-21 10:00:04', '2019-02-21 10:00:04', '0', '乐器', '乐器', '2');
INSERT INTO `tb_shop_category` VALUES ('15', '2019-02-21 10:00:04', '2019-02-21 10:00:04', '0', '车', '车', '2');
INSERT INTO `tb_shop_category` VALUES ('16', '2019-02-21 10:00:04', '2019-02-21 10:00:04', '0', '电器', '电器', '2');
INSERT INTO `tb_shop_category` VALUES ('17', '2019-02-21 10:00:45', '2019-02-21 10:00:45', '0', '车', '车', '3');
INSERT INTO `tb_shop_category` VALUES ('18', '2019-02-21 10:00:45', '2019-02-21 10:00:45', '0', '电脑', '电脑', '3');
INSERT INTO `tb_shop_category` VALUES ('19', '2019-02-21 10:02:10', '2019-02-21 10:02:10', '0', 'KTV', 'KTV', '4');
INSERT INTO `tb_shop_category` VALUES ('20', '2019-02-21 10:02:10', '2019-02-21 10:02:10', '0', '电玩城', '电玩城', '4');
INSERT INTO `tb_shop_category` VALUES ('21', '2019-02-21 10:02:10', '2019-02-21 10:02:10', '0', '网咖', '网咖', '4');
INSERT INTO `tb_shop_category` VALUES ('22', '2019-02-21 10:02:10', '2019-02-21 10:02:10', '0', '游乐场', '游乐场', '4');
INSERT INTO `tb_shop_category` VALUES ('23', '2019-02-21 10:02:10', '2019-02-21 10:02:10', '0', '棋牌室', '棋牌室', '4');
INSERT INTO `tb_shop_category` VALUES ('24', '2019-02-21 10:03:29', '2019-02-21 10:03:29', '0', '美发店', '美发店', '5');
INSERT INTO `tb_shop_category` VALUES ('25', '2019-02-21 10:03:29', '2019-02-21 10:03:29', '0', '美甲店', '美甲店', '5');
INSERT INTO `tb_shop_category` VALUES ('26', '2019-02-21 10:03:29', '2019-02-21 10:03:29', '0', '美容店', '美容店', '5');
INSERT INTO `tb_shop_category` VALUES ('27', '2019-02-21 10:03:29', '2019-02-21 10:03:29', '0', '按摩店', '按摩店', '5');
INSERT INTO `tb_shop_category` VALUES ('28', '2019-02-21 10:03:29', '2019-02-21 10:03:29', '0', '足浴', '足浴', '5');
INSERT INTO `tb_shop_category` VALUES ('29', '2019-02-21 10:07:18', '2019-02-21 10:07:18', '0', '考研机构', '考研机构', '6');
INSERT INTO `tb_shop_category` VALUES ('30', '2019-02-21 10:07:18', '2019-02-21 10:07:18', '0', '培训机构', '培训机构', '6');
INSERT INTO `tb_shop_category` VALUES ('31', '2019-02-21 10:07:18', '2019-02-21 10:07:18', '0', '驾校', '驾校', '6');
INSERT INTO `tb_shop_category` VALUES ('32', '2019-02-21 10:07:18', '2019-02-21 10:07:18', '0', '托管所', '托管所', '6');
INSERT INTO `tb_shop_category` VALUES ('33', '2019-02-21 10:09:28', '2019-02-21 10:09:28', '0', '游泳馆', '游泳馆', '7');
INSERT INTO `tb_shop_category` VALUES ('34', '2019-02-21 10:09:28', '2019-02-21 10:09:28', '0', '室内运动', '室内运动', '7');
INSERT INTO `tb_shop_category` VALUES ('35', '2019-02-21 10:09:28', '2019-02-21 10:09:28', '0', '健身房', '健身房', '7');
INSERT INTO `tb_shop_category` VALUES ('36', '2019-02-21 10:09:28', '2019-02-21 10:09:28', '0', '室外运动', '室外运动', '7');
INSERT INTO `tb_shop_category` VALUES ('37', '2019-02-21 10:13:26', '2019-02-21 10:13:26', '0', '日常用品', '日常用品', '8');
INSERT INTO `tb_shop_category` VALUES ('38', '2019-02-21 10:13:26', '2019-02-21 10:13:26', '0', '干洗店', '干洗店', '8');
INSERT INTO `tb_shop_category` VALUES ('39', '2019-02-21 10:13:26', '2019-02-21 10:13:26', '0', '杂货铺', '杂货铺', '8');
INSERT INTO `tb_shop_category` VALUES ('40', '2019-02-21 10:13:26', '2019-02-21 10:13:26', '0', '文具店', '文具店', '8');

-- ----------------------------
-- Table structure for `tb_user_collection`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_collection`;
CREATE TABLE `tb_user_collection` (
  `user_collection_id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `person_info_id` int(11) DEFAULT NULL,
  `shop_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_collection_id`),
  KEY `fk_coll_person` (`person_info_id`),
  KEY `fk_coll_shop` (`shop_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_collection
-- ----------------------------
