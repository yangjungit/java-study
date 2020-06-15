/*

 Source Server Type    : MySQL
 Source Schema         : test

 Date: 14/04/2020 17:33:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

create database IF NOT EXISTS test CHARACTER SET utf8 COLLATE utf8_general_ci;

-- ----------------------------
-- Table structure for uuu
-- ----------------------------
CREATE TABLE IF NOT EXISTS`uuu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "" COMMENT '姓名啊',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT "" COMMENT '描述啊',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

CREATE TABLE IF NOT EXISTS `ttl_product_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录唯一标识',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '类型ID',
  `category_name` varchar(50) NOT NULL COMMENT '冗余分类名称-避免跨表join',
  `branch_id` bigint(20) NOT NULL COMMENT '品牌ID',
  `branch_name` varchar(50) NOT NULL COMMENT '冗余品牌名称-避免跨表join',
  `shop_id` bigint(20) NOT NULL COMMENT '商品ID',
  `shop_name` varchar(50) NOT NULL COMMENT '冗余商店名称-避免跨表join',
  `price` decimal(10,2) NOT NULL COMMENT '商品当前价格-属于热点数据，而且价格变化需要记录，需要价格详情表',
  `stock` int(11) NOT NULL COMMENT '库存-热点数据',
  `sales_num` int(11) NOT NULL COMMENT '销量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '插入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '记录是否已经删除',
  PRIMARY KEY (`id`),
  KEY `idx_shop_category_salesnum` (`shop_id`,`category_id`,`sales_num`),
  KEY `idx_category_branch_price` (`category_id`,`branch_id`,`price`),
  KEY `idx_productname` (`product_name`)
) ENGINE=InnoDB AUTO_INCREMENT=15000001 DEFAULT CHARSET=utf8 COMMENT='商品信息表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称',
  `permission` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 87 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
REPLACE INTO `sys_menu` VALUES (1, '查看用户信息', 'sys:user:info');
REPLACE INTO `sys_menu` VALUES (2, '查看所有权限', 'sys:menu:info');
REPLACE INTO `sys_menu` VALUES (3, '查看所有角色', 'sys:role:info');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_role`  (
  `role_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
REPLACE INTO `sys_role` VALUES (1, 'ADMIN');
REPLACE INTO `sys_role` VALUES (2, 'USER');


-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user`  (
  `user_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态 PROHIBIT：禁用   NORMAL：正常',
  `salt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
REPLACE INTO `sys_user` VALUES (1, 'admin', '$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa', 'NORMAL', NULL);
REPLACE INTO `sys_user` VALUES (2, 'user', '$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS', 'NORMAL', NULL);

CREATE TABLE IF NOT EXISTS `sys_role_menu`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(11) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(11) NULL DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色与权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
REPLACE INTO `sys_role_menu` VALUES (1, 1, 1);
REPLACE INTO `sys_role_menu` VALUES (2, 1, 2);
REPLACE INTO `sys_role_menu` VALUES (3, 1, 3);
REPLACE INTO `sys_role_menu` VALUES (4, 2, 1);


-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
CREATE TABLE IF NOT EXISTS `sys_user_role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint(11) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(11) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户与角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
REPLACE INTO `sys_user_role` VALUES (1, 1, 1);
REPLACE INTO `sys_user_role` VALUES (2, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
