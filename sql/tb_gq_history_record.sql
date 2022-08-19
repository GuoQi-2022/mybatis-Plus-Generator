/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : testdemo

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 19/08/2022 17:12:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_gq_history_record
-- ----------------------------
DROP TABLE IF EXISTS `tb_gq_history_record`;
CREATE TABLE `tb_gq_history_record`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `gqUserId` bigint(0) NOT NULL DEFAULT 0 COMMENT '用户userId',
  `record` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '历史记录信息',
  `weights` int(0) NOT NULL DEFAULT 0 COMMENT '权重值/排序',
  `deleted` int(0) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `createTime` bigint(0) NOT NULL DEFAULT 0 COMMENT '创建时间',
  `updateTime` bigint(0) NOT NULL DEFAULT 0 COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_weights`(`weights`) USING BTREE,
  INDEX `idx_user_userId`(`gqUserId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户搜索历史记录' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
