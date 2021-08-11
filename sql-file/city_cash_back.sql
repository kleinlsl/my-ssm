CREATE TABLE `city_cash_back`
(
    `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `date` date NOT NULL COMMENT '日期,粒度/天',
    `city_id` bigint(20) unsigned NOT NULL DEFAULT 0 COMMENT '途家城市id',
    `amount` int NOT NULL default 5 COMMENT '返现金额',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_idx_date_city_id` (`date`,`city_id`) COMMENT '唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='城市返现金额';