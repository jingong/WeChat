/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : wechat

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2017-01-08 10:33:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userName` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('jia', '123');
INSERT INTO `user` VALUES ('sa', '123');
