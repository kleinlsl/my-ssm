CREATE TABLE `promo_due_reminder`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `member_id`   bigint(20) unsigned          default 0 NOT NULL COMMENT '途家会员id',
    `promo_code`  varchar(50)                  default '' not null comment '卡券包编号  防止被遍历',
    `to_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '券到期时间',
    `number`      int unsigned                 default 0 NOT NULL COMMENT '提醒次数',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            NOT NULL default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_idx_promo_code` (`promo_code`) COMMENT '唯一索引',
    KEY `idx_member_id_promo_code` (`member_id`, `promo_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='红包到期提醒记录';