
## mysql ddl
#### user_login 用户登录
    CREATE TABLE `user_login` (
      `id` bigint(64) NOT NULL COMMENT 'id',
      `username` varchar(255) NOT NULL COMMENT '登录名(可以中文)',
      `password` varchar(255) NOT NULL COMMENT '密码',
      `salt` varchar(255) NOT NULL COMMENT '密码盐值',
      `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
      `mail` varchar(255) DEFAULT NULL COMMENT '邮箱',
      `role` tinyint(2) NOT NULL DEFAULT '2' COMMENT '角色：1：管理员，2：普通用户',
      PRIMARY KEY (`id`),
      UNIQUE KEY `username` (`username`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户登录表';
    
#### role 用户角色
    CREATE TABLE `role` (
      `id` bigint(16) NOT NULL,
      `role_id` tinyint(2) NOT NULL COMMENT '角色ID',
      `role_desc` varchar(255) NOT NULL COMMENT '角色说明',
      `authority` tinyint(2) NOT NULL DEFAULT '0' COMMENT '权限等级：0-9逐级递增',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权限表';
    
#### game_type 游戏类型
    CREATE TABLE `game_type` (
     `id` bigint(16) NOT NULL,
     `game_flag` int(8) NOT NULL COMMENT '游戏标记',
     `game_index` varchar(255) NOT NULL COMMENT '对应es库的index',
     `game_title` varchar(255) NOT NULL COMMENT '游戏名称',
     `game_desc` varchar(255) DEFAULT NULL COMMENT '描述',
     PRIMARY KEY (`id`),
     UNIQUE KEY `game_falg` (`game_flag`) USING BTREE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支持的游戏类型';
    
#### user_info 用户信息
    CREATE TABLE `user_info` (
      `id` bigint(64) NOT NULL COMMENT 'id',
      `user_id` bigint(64) NOT NULL COMMENT '用户登录表id',
      `nick_name` varchar(255) NOT NULL COMMENT '昵称，默认为用户名',
      `image_url` varchar(255) COMMENT '头像地址，只保存后缀',
      `sex` tinyint(4) NOT NULL COMMENT '性别：0男，1女，2其他',
      `birthday` varchar(255) COMMENT '出生年月，格式yyyy-MM-dd',
      `age` int(4) COMMENT '年龄，根据出生年月计算',
      `constellation` varchar(255) COMMENT '星座，根据出生年月计算',
      `zodiac` varchar(255) COMMENT '生肖，根据出生年月计算',
      `brief` varchar(255) COMMENT '简介',
      `created` datetime NOT NULL DEFAULT NOW() COMMENT '注册时间',
      PRIMARY KEY (`id`),
      UNIQUE KEY `uesr_id` (`uesr_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
    

