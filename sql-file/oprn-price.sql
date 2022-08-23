CREATE TABLE `activity`
(
    `id`                       bigint(20)          NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `activity_id`              int(11)             NOT NULL DEFAULT '0' COMMENT '活动Id',
    `activity_name`            varchar(50)         NOT NULL DEFAULT '' COMMENT '活动名称',
    `status`                   tinyint(4)          NOT NULL DEFAULT '0' COMMENT '活动状态，1-有效，0-无效',
    `source`                   tinyint(4)          NOT NULL DEFAULT '0' COMMENT '活动来源,1-途家营销系统',
    `label`                    int(11)             NOT NULL DEFAULT '0' COMMENT '活动标签，按位存储，是否秒杀之类',
    `channel`                  bigint(20)          NOT NULL DEFAULT '0' COMMENT '分销渠道(按位存储),具体含义参考业务枚举定义',
    `order_preferential_limit` int(11)             NOT NULL DEFAULT '0' COMMENT '每个订单优惠上限金额',
    `booking_rule`             mediumtext COMMENT '活动预订规则，JSON存储',
    `data_entity_status`       tinyint(4)          NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    `create_time`              timestamp(3)        NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`              timestamp(3)        NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    `discount_type`            tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '优惠类型',
    `discount_rule`            text COMMENT '优惠规则,JSON存储',
    `checkIn_rule`             varchar(3000)                DEFAULT NULL COMMENT '入住规则,JSON存储',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_activity_id` (`activity_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 341125
  DEFAULT CHARSET = utf8mb4 COMMENT ='活动配置表'


CREATE TABLE `async_message_repair_queue`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `message_type` int(11)    NOT NULL DEFAULT '0' COMMENT '消息类型，1-房屋信息变化,2-房屋价格变化，3-产品信息变化，4-城市库存信息变化',
    `content`      text       NOT NULL COMMENT '消息内容',
    `status`       int(11)    NOT NULL DEFAULT '0' COMMENT '状态, 0-待处理,1-成功,2-失败',
    `retry_count`  int(11)    NOT NULL DEFAULT '0' COMMENT '重试次数',
    `create_time`  timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_message_type` (`message_type`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 57712
  DEFAULT CHARSET = utf8mb4 COMMENT ='异步消息重跑队列表'

CREATE TABLE `blacklist`
(
    `id`            int(10) unsigned    NOT NULL AUTO_INCREMENT COMMENT '自增加主键',
    `black_key`     bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '黑名单key,hotelId',
    `black_type`    tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '黑名单类型(1.返现黑名单)',
    `create_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `channel_label` varchar(30)         NOT NULL DEFAULT '' COMMENT '白名单渠道标签',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_black_key_type` (`black_key`, `black_type`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 329227
  DEFAULT CHARSET = utf8mb4 COMMENT ='报价黑名单表'

CREATE TABLE `city_quote_modify_queue_del`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `city_id`     bigint(20)   NOT NULL DEFAULT '0' COMMENT '城市Id',
    `data_type`   int(11)      NOT NULL DEFAULT '0' COMMENT '数据类型，1-房屋信息,2-房屋价格，3-产品信息，4-库存信息',
    `create_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    `modify_time` bigint(20)   NOT NULL DEFAULT '0' COMMENT '最后修改时间，单位:纳秒',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_cityid_datatype` (`city_id`, `data_type`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 261
  DEFAULT CHARSET = utf8mb4 COMMENT ='城市级别报价变化队列表'

CREATE TABLE `city_quote_result_del`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `city_id`     bigint(20)   NOT NULL DEFAULT '0' COMMENT '城市Id',
    `data_type`   int(11)      NOT NULL DEFAULT '0' COMMENT '数据类型，1-房屋信息,2-房屋价格，3-产品信息，4-库存信息',
    `data`        mediumblob   NOT NULL COMMENT '字节数据',
    `content`     mediumtext   NOT NULL COMMENT '字符串数据',
    `create_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    `modify_time` bigint(20)   NOT NULL DEFAULT '0' COMMENT '最后修改时间，单位:纳秒',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_cityid_datatype` (`city_id`, `data_type`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2108295
  DEFAULT CHARSET = utf8mb4 COMMENT ='城市级别报价模型结果表'

CREATE TABLE `currency_exchange`
(
    `id`                bigint(20)     NOT NULL AUTO_INCREMENT,
    `currency`          int(11)        NOT NULL DEFAULT '0' COMMENT '币种类型',
    `cny_exchange_rate` decimal(12, 6) NOT NULL DEFAULT '0.000000' COMMENT '当前币种对人民币的汇率',
    `usd_exchange_rate` decimal(12, 6) NOT NULL DEFAULT '0.000000' COMMENT '当前币种对美元的汇率',
    `create_time`       timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_currency` (`currency`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 31
  DEFAULT CHARSET = utf8mb4 COMMENT ='货币兑换对应汇率值表'

CREATE TABLE `global_dict`
(
    `id`          bigint(20)    NOT NULL AUTO_INCREMENT,
    `module_name` varchar(32)   NOT NULL DEFAULT '' COMMENT '模块名称',
    `key`         varchar(50)   NOT NULL DEFAULT '' COMMENT '键',
    `value`       varchar(1000) NOT NULL DEFAULT '' COMMENT '值',
    `create_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_module_name` (`module_name`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7702
  DEFAULT CHARSET = utf8mb4 COMMENT ='公共配置表'

CREATE TABLE `policy_longtermlease`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `policy_code`     varchar(20)         NOT NULL DEFAULT '' COMMENT '政策码',
    `sale_channel`    bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '售卖渠道(按位存储),具体含义参考业务枚举定义',
    `policy_rule`     varchar(3000)       NOT NULL DEFAULT '' COMMENT '政策规则属性',
    `city_id`         int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '城市Id',
    `hotel_id`        bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '门店Id',
    `unit_id`         bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `product_id`      bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '产品id',
    `available_count` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '可使用次数',
    `used_count`      tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '已使用次数',
    `state`           tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态，0:无效 1有效',
    `expireTime`      datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '过期时间',
    `create_time`     datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`     datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_unit_id_product_id` (`unit_id`, `product_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 30274
  DEFAULT CHARSET = utf8mb4 COMMENT ='长租政策配置表'

CREATE TABLE `policy_nobargain`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `policy_code`     varchar(12)         NOT NULL DEFAULT '' COMMENT '政策码',
    `sale_channel`    bigint(20)          NOT NULL DEFAULT '0' COMMENT '售卖渠道(按位存储),具体含义参考业务枚举定义',
    `policy_rule`     varchar(3000)       NOT NULL DEFAULT '' COMMENT '政策规则属性',
    `city_id`         int(10) unsigned    NOT NULL DEFAULT '0' COMMENT '城市Id',
    `hotel_id`        bigint(20)          NOT NULL DEFAULT '0' COMMENT '门店Id',
    `unit_id`         bigint(20)          NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `product_id`      bigint(20)          NOT NULL DEFAULT '0' COMMENT '产品id',
    `available_count` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '可使用次数',
    `used_count`      tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '已使用次数',
    `state`           tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态，0:无效 1有效',
    `expireTime`      datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '过期时间',
    `create_time`     datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`     datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_unit_id_product_id` (`unit_id`, `product_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 702465310
  DEFAULT CHARSET = utf8mb4 COMMENT ='一口价政策配置表'

CREATE TABLE `product_snapshot`
(
    `id`                 bigint(20)          NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `product_id`         bigint(20)          NOT NULL DEFAULT '0' COMMENT '产品Id',
    `unit_id`            bigint(20)          NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `product_status`     tinyint(4)          NOT NULL DEFAULT '0' COMMENT '产品是否可售卖，1-可售卖，0-不可售卖',
    `activity_info`      mediumtext COMMENT '产品活动信息，JSON存储',
    `data_entity_status` tinyint(4)          NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    `create_time`        timestamp(3)        NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`        timestamp(3)        NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    `product_type`       tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '产品类型',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_product_id` (`product_id`),
    KEY `idx_unit_id` (`unit_id`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 86632834
  DEFAULT CHARSET = utf8mb4 COMMENT ='产品快照表'

CREATE TABLE `unit_quote_data`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `unit_id`            bigint(20)   NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `hotel_id`           bigint(20)   NOT NULL DEFAULT '0' COMMENT '门店Id',
    `city_id`            bigint(20)   NOT NULL DEFAULT '0' COMMENT '城市Id',
    `data_type`          int(11)      NOT NULL DEFAULT '0' COMMENT '数据类型，1-房屋信息,2-房屋价格，3-产品信息，4-库存信息',
    `data`               mediumblob COMMENT '字节数据',
    `content`            mediumtext COMMENT '字符串数据',
    `data_entity_status` tinyint(4)   NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    `create_time`        timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`        timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_unitid_datatype` (`unit_id`, `data_type`),
    KEY `idx_update_time` (`update_time`),
    KEY `idx_city_id_status_unit_id` (`city_id`, `data_entity_status`, `unit_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 16104906
  DEFAULT CHARSET = utf8mb4 COMMENT ='房屋级别报价数据表'

CREATE TABLE `unit_quote_data_productinfo`
(
    `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `unit_id`            bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `hotel_id`           bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '门店Id',
    `city_id`            bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '城市Id',
    `data`               mediumblob COMMENT '字节数据',
    `data_entity_status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    `create_time`        datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`        datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_unit_id` (`unit_id`),
    KEY `idx_city_id_status_unit_id` (`city_id`, `data_entity_status`, `unit_id`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14909893
  DEFAULT CHARSET = utf8mb4 COMMENT ='房屋报价产品信息数据表'

CREATE TABLE `unit_quote_data_stockinfo`
(
    `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `unit_id`            bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `hotel_id`           bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '门店Id',
    `city_id`            bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '城市Id',
    `data`               mediumblob COMMENT '字节数据',
    `data_entity_status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    `create_time`        datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`        datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_unit_id` (`unit_id`),
    KEY `idx_city_id_status_unit_id` (`city_id`, `data_entity_status`, `unit_id`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14936440
  DEFAULT CHARSET = utf8mb4 COMMENT ='房屋报价库存信息数据表'

CREATE TABLE `unit_quote_data_unitinfo`
(
    `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `unit_id`            bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `hotel_id`           bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '门店Id',
    `city_id`            bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '城市Id',
    `data`               mediumblob COMMENT '字节数据',
    `data_entity_status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    `create_time`        datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`        datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_unit_id` (`unit_id`),
    KEY `idx_city_id_status_unit_id` (`city_id`, `data_entity_status`, `unit_id`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14882152
  DEFAULT CHARSET = utf8mb4 COMMENT ='房屋报价信息数据表'

CREATE TABLE `unit_quote_data_unitprice`
(
    `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键Id',
    `unit_id`            bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `hotel_id`           bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '门店Id',
    `city_id`            bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '城市Id',
    `data`               mediumblob COMMENT '字节数据',
    `data_entity_status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    `create_time`        datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`        datetime(3)         NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_unit_id` (`unit_id`),
    KEY `idx_city_id_status_unit_id` (`city_id`, `data_entity_status`, `unit_id`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14910355
  DEFAULT CHARSET = utf8mb4 COMMENT ='房屋报价价格信息数据表'

CREATE TABLE `unit_quote_incr_update`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `unit_id`     bigint(20)   NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `hotel_id`    bigint(20)   NOT NULL DEFAULT '0' COMMENT '门店Id',
    `city_id`     bigint(20)   NOT NULL DEFAULT '0' COMMENT '城市Id',
    `data_type`   int(11)      NOT NULL DEFAULT '0' COMMENT '数据类型，1-房屋信息,2-房屋价格，3-产品信息，4-库存信息',
    `create_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_unitid_datatype` (`unit_id`, `data_type`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 62064427
  DEFAULT CHARSET = utf8mb4 COMMENT ='房屋级别报价变化增量更新表'

CREATE TABLE `unit_snapshot`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT,
    `unit_id`            bigint(20)   NOT NULL DEFAULT '0' COMMENT '房屋Id',
    `hotel_id`           bigint(20)   NOT NULL DEFAULT '0' COMMENT '门店Id',
    `city_id`            bigint(20)   NOT NULL DEFAULT '0' COMMENT '城市Id',
    `unit_status`        int(11)      NOT NULL DEFAULT '0' COMMENT '房屋是否可售卖，1-可售卖，0-不可售卖',
    `row_version`        bigint(20)   NOT NULL DEFAULT '0' COMMENT '房屋版本号',
    `create_time`        timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`        timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    `data_entity_status` tinyint(4)   NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_unit_id` (`unit_id`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 21382450
  DEFAULT CHARSET = utf8mb4 COMMENT ='房屋快照表'

CREATE TABLE `wrapper_config`
(
    `id`                 bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `wrapper_id`         varchar(20)  NOT NULL DEFAULT '' COMMENT 'wrapperId',
    `wrapper_name`       varchar(50)  NOT NULL DEFAULT '' COMMENT '马甲名称',
    `sale_channel`       bigint(20)   NOT NULL DEFAULT '0' COMMENT '售卖渠道(按位存储),具体含义参考业务枚举定义',
    `sale_mode`          tinyint(4)   NOT NULL DEFAULT '0' COMMENT '销售模式,1-卖价直销,2-卖价分销,3-底价分润,4-底价加价,5-自定义模式',
    `wrapper_tags`       varchar(100) NOT NULL DEFAULT '' COMMENT 'wrapper标签',
    `sale_rule`          text COMMENT 'wrapper销售规则',
    `check_rule`         text COMMENT 'wrapper检查规则',
    `data_entity_status` tinyint(4)   NOT NULL DEFAULT '0' COMMENT '逻辑删除标识，0:正常，1删除',
    `create_time`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `wrapper_extended`   varchar(50)  NOT NULL DEFAULT '' COMMENT 'wrapper扩展字段',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_wrapper_id` (`wrapper_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 52
  DEFAULT CHARSET = utf8mb4 COMMENT ='wrapper配置信息表'

