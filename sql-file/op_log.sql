CREATE TABLE `op_log`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `type`        int(11)             NOT NULL DEFAULT '0' COMMENT '类型',
    `detail`      json                         DEFAULT NULL COMMENT '操作详情',
    `operator`    varchar(50)         NOT NULL DEFAULT '' COMMENT '操作员',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_type` (`type`),
    KEY `idx_operator` (`operator`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='操作日志表';