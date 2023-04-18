CREATE TABLE `activity_participant`
(
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `activity_code` varchar(40) NOT NULL DEFAULT '' COMMENT '活动配置编码',
    `status` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '活动配置状态 1=未处理 2=已处理',
    `unit_ids` mediumtext COMMENT '房屋ID列表，按照升序排列的房屋列表：最大16M',
    `data` mediumblob COMMENT '字节数据：最大16M',
    `md5` varchar(64) NOT NULL default '' COMMENT 'MD5值',
    `create_time` datetime NOT NULL default CURRENT_TIMESTAMP COMMENT '创建时间',
    `version` int unsigned NOT NULL DEFAULT '1' COMMENT '版本：初始值为1',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_idx_activity_code_version` (`activity_code`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动参与房屋';

