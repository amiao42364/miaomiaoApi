
## 数据库设计三大范式
1. 如果数据库表中的所有字段值都是不可分解的原子值，就说明该数据库表满足了第一范式;
2. 确保数据库表中的每一列都和主键相关，而不能只与主键的某一部分相关（主要针对联合主键而言）也就是说在一个数据库表中，一个表中只能保存一种数据，不可以把多种数据保存在同一张数据库表中；
3. 第三范式需要确保数据表中的每一列数据都和主键直接相关，而不能间接相关。

### 总结一下，就是：
第一范式(确保每列保持原子性)；  
第二范式(确保表中的每列都和主键相关)；  
第三范式(确保每列都和主键列直接相关,而不是间接相关)。  

## mysql ddl
#### user_login
    CREATE TABLE `user_login` (
      `id` int(5) NOT NULL COMMENT 'id',
      `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录名(可以中文)',
      `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
      `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
      `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
      `role` tinyint(2) NOT NULL DEFAULT '2' COMMENT '角色：1：管理员，2：普通用户',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户登录表';
    
#### role
    CREATE TABLE `role` (
      `id` int(8) NOT NULL,
      `role_id` tinyint(2) NOT NULL COMMENT '角色ID',
      `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色说明',
      `authority` tinyint(2) NOT NULL DEFAULT '0' COMMENT '权限等级：0-9逐级递增',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限表';
