#### user_login 用户登录
CREATE TABLE `user_login`
(
  `id`       bigint(64)   NOT NULL COMMENT 'id',
  `username` varchar(255) NOT NULL COMMENT '登录名(可以中文)',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt`     varchar(255) NOT NULL COMMENT '密码盐值',
  `mobile`   varchar(255)          DEFAULT NULL COMMENT '手机号',
  `mail`     varchar(255)          DEFAULT NULL COMMENT '邮箱',
  `role`     tinyint(2)   NOT NULL DEFAULT '2' COMMENT '角色：1：管理员，2：普通用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户登录表';

#### role 用户角色
CREATE TABLE `role`
(
  `id`        bigint(16)   NOT NULL,
  `role_id`   tinyint(2)   NOT NULL COMMENT '角色ID',
  `role_desc` varchar(255) NOT NULL COMMENT '角色说明',
  `authority` tinyint(2)   NOT NULL DEFAULT '0' COMMENT '权限等级：0-9逐级递增',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='角色权限表';

#### game_type 游戏类型
CREATE TABLE `game_type`
(
  `id`         bigint(16)   NOT NULL,
  `game_flag`  int(8)       NOT NULL COMMENT '游戏标记',
  `game_index` varchar(255) NOT NULL COMMENT '对应es库的index',
  `game_title` varchar(255) NOT NULL COMMENT '游戏名称',
  `game_desc`  varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `game_flag` (`game_flag`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='支持的游戏类型';

#### user_info 用户信息
CREATE TABLE `user_info`
(
  `id`            bigint(64)   NOT NULL COMMENT 'id',
  `user_id`       bigint(64)   NOT NULL COMMENT '用户登录表id',
  `nick_name`     varchar(255) NOT NULL COMMENT '昵称，默认为用户名',
  `image_url`     varchar(255) COMMENT '头像地址，只保存后缀',
  `sex`           tinyint(4)   NOT NULL COMMENT '性别：0男，1女，2其他',
  `birthday`      varchar(255) COMMENT '出生年月，格式yyyy-MM-dd',
  `age`           int(4) COMMENT '年龄，根据出生年月计算',
  `constellation` varchar(255) COMMENT '星座，根据出生年月计算',
  `zodiac`        varchar(255) COMMENT '生肖，根据出生年月计算',
  `brief`         varchar(255) COMMENT '简介',
  `created`       datetime     NOT NULL DEFAULT NOW() COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户信息表';

#### cs_go配置
CREATE TABLE `csgo_conf`
(
  `id`       bigint(64) NOT NULL COMMENT 'id',
  `user_id`  bigint(64) NOT NULL COMMENT '用户id',
  `start_up` text COMMENT '启动项配置',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='csgo的配置';

#### 雀魂模切牌表
CREATE TABLE `majsoul_card`
(
  `id`            int(11)      NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card`          varchar(255) NOT NULL COMMENT '本家麻将牌: 1m1m1m2m2m2m3m3m3m4m',
  `ming`          varchar(255) DEFAULT NULL COMMENT '本家鸣牌：1z1z1z',
  `card_next`     varchar(255) DEFAULT NULL COMMENT '下家打出去的牌：4s6s3z',
  `ming_next`     varchar(255) DEFAULT NULL COMMENT '下家鸣牌：4p5p6p',
  `card_opposite` varchar(255) DEFAULT NULL COMMENT '对家打出去的牌：4s6s3z',
  `ming_opposite` varchar(255) DEFAULT NULL COMMENT '对家鸣牌：4p5p6p',
  `card_last`     varchar(255) DEFAULT NULL COMMENT '上家打出去的牌：4s6s3z',
  `ming_last`     varchar(255) DEFAULT NULL COMMENT '上家鸣牌：4p5p6p',
  `dora`          varchar(255) DEFAULT NULL COMMENT '宝牌：1m2m',
  `score`         varchar(255) DEFAULT NULL COMMENT '得点：本家、下家、对家、上家，英文逗号隔开',
  `session`       varchar(255) DEFAULT NULL COMMENT '场况：东1',
  `type`          tinyint(4)   DEFAULT NULL COMMENT '牌谱类型：1、简易牌谱，只有本家13张牌考察牌效。2、高级牌谱，包括场况，其他家鸣牌情况',
  `creator`       varchar(255) NOT NULL COMMENT '创建人',
  `created`       datetime     NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='雀魂模切牌表';

#### 雀魂模切牌谱答案表
CREATE TABLE `majsoul_card_answer`
(
  `id`      bigint(20)   NOT NULL COMMENT 'id',
  `card_id` int(11)      NOT NULL COMMENT '雀魂模切牌谱id',
  `key`     varchar(255) NOT NULL COMMENT '切出去的牌',
  `desc`    text         NOT NULL COMMENT '为何这样切描述',
  `up`      int(16) DEFAULT '0' COMMENT '该答案支持的人数',
  `down`    int(16) DEFAULT '0' COMMENT '该答案反对的人数',
  `creator` varchar(255) NOT NULL COMMENT '创建人',
  `created` datetime     NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `cardId` (`card_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='雀魂模切牌谱答案表';

