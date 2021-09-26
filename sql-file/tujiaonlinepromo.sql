create schema if not exists tujiaonlinepromo collate utf8mb4_general_ci;

create table if not exists activity_participant
(
	id bigint unsigned auto_increment comment '主键id'
		primary key,
	activity_code varchar(40) default '' not null comment '活动配置编码',
	status tinyint unsigned default 1 not null comment '活动配置状态 1=未处理 2=已处理',
	unit_ids mediumtext null comment '房屋ID列表，按照升序排列的房屋列表：最大16M',
	data mediumblob null comment '字节数据：最大16M',
	md5 varchar(64) default '' not null comment 'MD5值',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	version int unsigned default 1 not null comment '版本：初始值为1',
	constraint unique_idx_activity_code_version
		unique (activity_code, version)
)
comment '活动参与房屋';

create table if not exists activitychannel
(
	ActivityChannelID int(11) unsigned auto_increment comment 'id'
		primary key,
	InventoryPoolID int(11) unsigned default 0 not null comment '对应库存池ID',
	activity_type tinyint unsigned default 0 not null comment '红包折扣类型',
	channel_name varchar(50) default '' null comment '渠道名称(门店名称)',
	promo_title varchar(50) default '' null comment '渠道发放的卡券包的title',
	activity mediumtext null comment '活动描述信息 json串',
	promoValidityInfo mediumtext null comment '红包有效期Json，1.区间有效期；2.固定天数
PromoValidity',
	enumPromoPartyType int unsigned default 0 null comment '标识红包是哪类的,0途家，1去哪儿，2携程，flag值',
	resource_used varchar(500) default '' null comment '活动已派发的资源统计信息 json串',
	status tinyint unsigned default 0 not null comment '是否有效0有效 1无效 2删除',
	creator varchar(30) default '' null comment '创建者',
	from_time datetime default '1970-01-01 09:00:00' not null comment '活动开始时间',
	to_time datetime default '1970-01-01 09:00:00' not null comment '活动结束时间',
	create_time datetime default '1970-01-01 09:00:00' not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	remark varchar(500) default '' null comment '活动渠道备注',
	enumProviderType tinyint unsigned default 1 not null comment '承担方类型',
	range_type tinyint(3) default -1 not null comment '适用范围类型',
	ownership_id bigint(19) unsigned default 0 not null comment '归属ID（门店id）',
	preferential_strategy varchar(3000) default '' not null comment '优惠策略Json格式（替换activity）',
	redpacket_config_sub mediumtext null comment '红包配置信息Json格式
RedPacketConfigSub',
	attribute int unsigned default 0 not null comment 'flag 属性 单一的是否, 用此字段扩展（新用户专享1）
（是否限制本人入住 0不限 2限制)
EnumStrategyAttr',
	biz_type tinyint unsigned default 1 not null comment '红包业务类型',
	ctripActivityChannelID varchar(45) default '-1' not null comment '携程券策略id',
	redpacket_displayname varchar(15) default '' not null comment '红包自定义展示文案',
	preference_type tinyint default 0 not null comment '优惠类型 0 直减 1返现，默认直减',
	constraint uniq_channel_name
		unique (channel_name)
)
comment '（红包配置表）库存池使用渠道';

create index idx_InventoryPoolID
	on activitychannel (InventoryPoolID);

create index idx_ctripActivityChannelID
	on activitychannel (ctripActivityChannelID);

create table if not exists ascend_path
(
	id int unsigned auto_increment comment '主键'
		primary key,
	activity_type tinyint unsigned default 1 not null comment '活动类型 com.tujia.trading.promo.api.remote.enums.EnumHelpActivityType',
	status tinyint unsigned default 1 not null comment '活动配置状态 EnumShareConfigStatus',
	activity_code varchar(40) default '' not null comment '活动配置编码',
	activity_channel_id varchar(230) default '0' not null comment '关联红包ID',
	config_name varchar(50) default '' not null comment '活动配置名称',
	activity_config_sub varchar(1000) default '' not null comment '活动配置附属属性',
	last_operator varchar(33) default '' not null comment '最后操作人',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	start_date datetime default '1970-01-01 00:00:00' not null comment '活动开始时间',
	end_date datetime default '2100-01-01 00:00:00' not null comment '活动结束时间',
	activity_limit varchar(1300) default '' not null comment '活动限制',
	send_limit_strategy varchar(100) default '' not null comment '发送红包限制策略',
	attribute smallint unsigned default 1 not null comment 'flag属性 1普通红包 2房东红包',
	biz_type tinyint unsigned default 1 not null comment '红包业务类型',
	platform_type tinyint unsigned default 0 not null comment '平台类型(0途家,1去哪,2携程)',
	applicant varchar(15) default '' not null comment '活动申请人',
	high_risk tinyint default 0 not null comment '高危活动标识0非高危活动 1高危活动',
	pushMsgTempleId varchar(45) default '' not null comment '发券后对应push消息模板id',
	constraint uniq_activity_config_code
		unique (activity_code) comment '活动配置编号唯一索引'
)
comment '（活动配置表）膨胀红包 助力人数和红包金额映射';

create index idx_activity_channel_id
	on ascend_path (activity_channel_id)
	comment '卡券策略ID';

create table if not exists basic_data_config
(
	id int unsigned auto_increment comment '主键'
		primary key,
	data_classify tinyint unsigned default 0 not null comment '数据分类',
	data_name varchar(100) default '' not null comment 'label',
	data_value varchar(100) default '' not null comment '数据值'
)
comment '基础数据配置表';

create table if not exists city_cash_back
(
	id bigint unsigned auto_increment comment '主键id'
		primary key,
	date date not null comment '日期,粒度/天',
	city_id bigint unsigned default 0 not null comment '途家城市id',
	amount int default 5 not null comment '返现金额',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后修改时间',
	constraint unique_idx_date_city_id
		unique (date, city_id) comment '唯一索引'
)
comment '城市返现金额';

create table if not exists coupon_send_log
(
	id int unsigned auto_increment comment '主键'
		primary key,
	business_type tinyint unsigned default 0 not null comment '业务类型',
	activity_code varchar(40) default '' not null comment '红包活动唯一标示',
	send_identity_id varchar(36) default '' not null comment '发放人身份标示',
	receive_member_id bigint(19) unsigned default 0 not null comment '卡券接收人会员ID',
	send_identity_type tinyint unsigned default 0 not null comment '发放人身份类型',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	coupon_codes varchar(420) default '' not null comment '卡券CODE集合'
)
comment '员工发红包日志';

create table if not exists ctrip_cash_info
(
	id bigint unsigned auto_increment comment '主键id'
		primary key,
	member_id bigint unsigned default 0 not null comment '途家会员id',
	channel_id int unsigned default 0 not null comment '渠道id:标记来源',
	ctrip_order_no varchar(64) default '' not null comment '携程订单号',
	serial_no varchar(128) default '' not null comment '任务流水号',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后修改时间',
	constraint uniq_idx_member_id_channel_id
		unique (member_id, channel_id) comment '唯一索引'
)
comment '携程火车票返现请求信息';

create table if not exists ctrip_order_info
(
	id bigint unsigned auto_increment comment '主键id'
		primary key,
	member_id bigint unsigned default 0 not null comment '途家会员id',
	channel_id int unsigned default 0 not null comment '渠道id:标记来源',
	ctrip_order_no varchar(64) default '' not null comment '携程订单号',
	serial_no varchar(128) default '' not null comment '任务流水号',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后修改时间',
	constraint uniq_idx_member_id_channel_id
		unique (member_id, channel_id) comment '唯一索引'
)
comment '携程火车票返现请求信息';

create table if not exists dynamic_rule
(
	id int unsigned auto_increment comment '主键'
		primary key,
	rule_type tinyint unsigned not null comment '业务类型 策略红包1',
	sub_type varchar(13) not null comment '业务内的细化类型 策略红包为城市ID',
	rule_value varchar(130) default '' not null comment '规则值 根据业务类型动态变化结构',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
comment '动态规则表';

create table if not exists file_give_promo
(
	id int unsigned auto_increment comment 'id'
		primary key,
	redpacket_config_id int unsigned default 0 not null comment '红包活动Id',
	url varchar(255) not null comment '上传文件url',
	status tinyint unsigned default 0 not null comment '文件处理状态  0:初始化状态   1：上传完成   3：处理中  4:处理完成',
	operator_info varchar(300) not null comment 'json格式的用户操作信息',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	grant_type tinyint unsigned default 0 not null comment '发放类型(0手机号,1会员ID)',
	update_time datetime default CURRENT_TIMESTAMP not null comment '更新时间',
	file_type tinyint unsigned default 0 not null comment '文件类型，区分途游卡、红包等模板',
	activity_code varchar(20) default '' not null comment '红包活动Code、途游卡批次号'
)
comment '上传发放红包的excel文件表';

create table if not exists help_activity
(
	id bigint unsigned auto_increment comment '主键'
		primary key,
	member_id bigint default 0 not null comment '活动发起人ID',
	activity_code varchar(38) default '' not null comment '分享活动唯一标识',
	help_num int unsigned default 0 not null comment '助力人数/参团人数',
	help_activity_sub varchar(600) default '' not null comment '助力活动表附属属性',
	end_cause tinyint unsigned default 0 not null comment '活动结束原因/拼团状态 EnumActivityEndCause',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	end_time datetime default CURRENT_TIMESTAMP not null comment '活动结束时间',
	update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
	now_level tinyint default -1 null comment '助力红包当前等级',
	next_level tinyint default -1 null comment '助力红包下一个等级(-1表示没有下一个等级)',
	activity_type tinyint unsigned default 1 not null comment '活动类型 EnumShareActivityType',
	enum_party_type tinyint unsigned default 0 not null comment '平台类型 0途家 1去哪儿 2携程',
	stage_code varchar(38) default '' not null comment '场次code',
	status tinyint(3) default 0 not null comment '活动状态',
	biz_unique varchar(50) default '' not null comment '业务唯一值',
	constraint uniq_activity_code
		unique (activity_code) comment '分享活动唯一索引'
)
comment '分享活动表';

create index idx_cause_ctime
	on help_activity (end_cause, create_time)
	comment 'end_cause create_time复合索引';

create index idx_member_id
	on help_activity (member_id)
	comment '会员ID索引';

create table if not exists help_no_activity_order
(
	id bigint unsigned auto_increment comment '主键'
		primary key,
	order_no bigint unsigned default 0 not null comment '订单Num',
	order_date datetime default CURRENT_TIMESTAMP not null comment '下单时间',
	order_id bigint unsigned default 0 not null comment '订单id',
	activity_start_time datetime default CURRENT_TIMESTAMP not null comment '活动开始时间',
	bargain_amount decimal(10,2) unsigned default 0.00 not null comment '砍价金额',
	member_id bigint unsigned default 0 not null comment '会员id',
	constraint uniq_order_no
		unique (order_no) comment 'order_no 唯一索引'
)
comment '没有关联活动的新订单';

create table if not exists help_record
(
	id bigint unsigned auto_increment comment '主键'
		primary key,
	member_id bigint default 0 not null comment '活动参与人ID',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	activity_code varchar(38) default '' not null comment '分享活动表ID',
	help_record_sub varchar(600) default '' null comment '助力记录表附属属性',
	update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
	activity_type tinyint unsigned default 1 not null comment '活动类型 EnumShareActivityType',
	customer_active_type tinyint unsigned default 0 not null comment '用户活跃类型 EnumCustomerActiveType',
	enum_party_type tinyint unsigned default 0 not null comment '平台类型 0途家 1去哪儿 2携程',
	ranking smallint unsigned default 0 not null comment '活动参与顺序排名',
	constraint uniq_member_activity
		unique (member_id, activity_code) comment '唯一索引'
)
comment '助力记录表';

create index idx_help_activity_code
	on help_record (activity_code)
	comment '参与记录 活动ID 索引';

create index idx_member_id
	on help_record (member_id);

create table if not exists inventorychangeqqueue
(
	InventoryChangeQueueID bigint unsigned auto_increment comment 'id'
		primary key,
	promoID bigint unsigned default 0 not null comment '优惠ID',
	activityChannelID int(11) unsigned default 0 not null comment '渠道ID',
	activity_type tinyint unsigned default 0 not null comment '活动ID',
	channel_resource_used varchar(500) default '' null comment '活动已派发的资源统计信息 json串',
	pool_resource_used varchar(500) default '' null comment '库存池资源已消耗信息',
	flow_status tinyint unsigned default 0 not null comment '导致库存变动的优惠流转状态',
	change_status tinyint unsigned default 0 not null comment '本条数据的队列处理状态 0未处理 1正在处理 2已处理',
	create_time datetime default '1970-01-01 09:00:00' not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	enumProviderType tinyint unsigned default 1 not null comment '承担方类型',
	enumPromoPartyType tinyint unsigned default 0 not null comment '标识红包是哪类的,0途家，1去哪儿，2携程，flag值'
)
comment '库存变动队列';

create index idx_change_status_create_time
	on inventorychangeqqueue (change_status, create_time);

create index idx_promo_id
	on inventorychangeqqueue (promoID);

create table if not exists inventorypool
(
	InventoryPoolID int(11) unsigned auto_increment comment 'id'
		primary key,
	pool_name varchar(50) default '' null comment '库存池名称',
	activity_type tinyint unsigned default 0 not null comment '活动ID （活动折扣类型：1直减，2满减，3循环满减，4阶梯）',
	inventory_pool_resource varchar(500) default '' null comment '库存池信息',
	inventory_pool_resource_used varchar(500) default '' null comment '库存池资源已消耗信息',
	status tinyint unsigned default 0 not null comment '是否有效0-有效 1-无效 2-删除',
	creator varchar(30) default '' null comment '创建者',
	create_time datetime default '1970-01-01 09:00:00' not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
comment '库存池（可以是钱，也可以是数量）';

create table if not exists keypacket
(
	packetID bigint auto_increment comment '红包主键id'
		primary key,
	active_id int(11) unsigned default 0 not null comment '红包活动id',
	packet_number varchar(50) default '' not null comment '红包编码',
	packet_password varchar(50) default '' not null comment '红包密码',
	redeem_code varchar(50) default '' not null comment '红包兑换码',
	used_userid int(20) unsigned default 0 null comment '绑定该红包用户id ',
	packet_status tinyint unsigned default 0 not null comment '0：可绑定   1：不可绑定  ',
	activity_code varchar(40) default '' not null comment '红包活动code',
	constraint idx_uniq_packet_number
		unique (packet_number),
	constraint idx_uniq_redeem_code
		unique (redeem_code),
	constraint uniq_packet_number
		unique (packet_number),
	constraint uniq_redeem_code
		unique (redeem_code)
)
comment '红包卡密表';

create table if not exists keypackettask
(
	packet_id bigint auto_increment comment '主键id'
		primary key,
	active_id int(11) unsigned default 0 not null comment '活动id',
	real_packet int default 0 not null comment '实发红包数',
	should_packet int default 0 not null comment '应发红包数',
	task_status tinyint(3) default 0 not null comment '0: 未完成  1: 已完成',
	activity_code varchar(40) default '' not null comment '红包活动code'
)
comment '红包卡密Task表';

create table if not exists merchant_coupon
(
	id int unsigned auto_increment comment '主键'
		primary key,
	title varchar(20) default '' not null comment '券名称',
	coupon_code varchar(40) default '' not null comment '卡券CODE',
	business_id bigint unsigned default 0 not null comment 'B端唯一标识(门店ID)',
	strategy_id int unsigned default 0 not null comment '策略ID',
	activity_code varchar(40) default '' not null comment '活动CODE',
	status tinyint unsigned default 0 not null comment '状态',
	business_flag tinyint unsigned default 0 not null comment '业务类型',
	preferential_type tinyint unsigned default 0 not null comment '优惠类型',
	from_time datetime default CURRENT_TIMESTAMP not null comment '开始有效时间',
	to_time datetime default CURRENT_TIMESTAMP not null comment '结束有效时间',
	coupon_sub varchar(330) default '{}' not null comment '扩展属性',
	preferential_strategy varchar(330) default '{}' not null comment '优惠策略',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	constraint uniq_coupon_code
		unique (coupon_code) comment '卡券编号唯一索引'
)
comment '商户卡券表';

create index idx_business_id_status
	on merchant_coupon (business_id, status);

create table if not exists merchant_coupon_log
(
	id int unsigned auto_increment comment '主键'
		primary key,
	serial_num varchar(50) default '' not null comment '流水号',
	refer_code varchar(50) default '' not null comment '关联码 例: 订单号、发放方流水号',
	coupon_code varchar(40) default '' not null comment '卡券CODE',
	business_id bigint unsigned default 0 not null comment 'B端唯一标识(门店ID)',
	from_status tinyint unsigned default 0 not null comment '来源状态',
	to_status tinyint unsigned default 0 not null comment '目标状态',
	preferential_type tinyint unsigned default 0 not null comment '优惠类型',
	coupon_log_sub varchar(255) default '{}' not null comment '扩展属性',
	coupon_snapshot varchar(255) default '{}' not null comment '变动快照',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	constraint uniq_serial_num
		unique (serial_num) comment '变动流水号唯一索引'
)
comment '商户卡券日志表';

create index idx_refer_code_to_status
	on merchant_coupon_log (refer_code, to_status)
	comment '发放人查询索引';

create table if not exists message_timeout_log
(
	message_id varchar(32) default '' not null comment 'mq msgid'
		primary key,
	topic varchar(32) null comment 'topic',
	tags varchar(16) null comment 'tag',
	body varchar(256) null comment 'mq 消息体',
	bean_name varchar(32) null comment 'spring bean name',
	method_name varchar(32) null comment 'bean#method name',
	add_time datetime null comment '添加时间',
	update_time datetime null comment '更新时间',
	status tinyint(2) null comment '状态（0 未处理；1 处理成功；2 处理失败）'
)
comment 'mq消息超时日志表';

create index idx_addtime_status
	on message_timeout_log (add_time, status)
	comment '根据add_time和status建立联合索引';

create table if not exists mystery_coupon_unit
(
	id int(11) unsigned auto_increment comment 'id'
		primary key,
	user_id bigint default 0 not null comment '用户Id',
	order_no bigint unsigned default 0 not null comment '盲盒订单号',
	activity_channel_id varchar(40) default '' not null comment '卡券id',
	promo_code varchar(50) default '' null comment '卡券包编号  防止被遍历',
	unit_id bigint unsigned default 0 not null comment '解锁的房屋',
	unit_status tinyint(3) default 0 null comment '房屋状态，0待兑换，1 已兑换，2 已失效',
	unit_seq int default 0 null comment '房屋顺序，第几个房屋',
	invalid_reason varchar(50) default '' null comment '失效原因',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
comment '盲盒券房屋映射表';

create table if not exists mystery_order_unit
(
	id int(11) unsigned auto_increment comment 'id'
		primary key,
	order_no bigint unsigned default 0 not null comment '盲盒订单号',
	user_id bigint default 0 not null comment '用户Id',
	activity_code varchar(40) default '' not null comment '活动配置编码',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	unit_id varchar(100) default '' not null comment '房屋id列表逗号分隔',
	user_order bigint default 0 not null,
	constraint uniq_order
		unique (order_no)
)
comment '盲盒订单房屋表';

create table if not exists mystery_unit_sign
(
	id int(11) unsigned auto_increment comment 'id'
		primary key,
	activity_code varchar(40) default '' not null comment '活动配置编码',
	unit_id bigint default 0 not null comment '房屋Id',
	unit_stock int(11) unsigned default 0 not null comment '房屋库存',
	start_date datetime default '1990-01-01 00:00:00' not null comment '入住开始日期',
	end_date datetime default '1990-01-01 00:00:00' not null comment '离店结束日期',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	constraint uniq_activity_unit_Id
		unique (activity_code, unit_id)
)
comment '盲盒房东报名表';

create table if not exists new_user_channel
(
	id int auto_increment comment '主键id'
		primary key,
	channel_id int(11) unsigned default 0 not null comment '活动渠道id 对应ActivityChannel表的ID',
	reg_channel_code varchar(100) default '' not null comment '用户注册渠道码',
	status tinyint unsigned default 0 not null comment '状态 0有效 1无效',
	from_date datetime default '1970-01-01 09:00:00' not null comment '活动开始日期',
	to_date datetime default '1970-01-01 09:00:00' not null comment '活动结束日期',
	valid_days int(11) unsigned default 0 not null comment '活动有效天数',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	channel_name varchar(100) default '' not null comment '用户注册渠道红包活动名称',
	constraint uniq_channel_id
		unique (channel_id)
)
comment '新用户按渠道发放红包的渠道';

create index idx_reg_channel_code_status
	on new_user_channel (reg_channel_code, status);

create table if not exists new_user_channel_code
(
	id int auto_increment comment '主键id'
		primary key,
	reg_channel_code varchar(100) default '' not null comment '用户注册渠道码',
	status tinyint(3) default 0 not null comment '状态 0有效 1无效',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	constraint uniq_reg_channel_code
		unique (reg_channel_code)
)
comment '新用户注册渠道码表';

create table if not exists new_user_channel_promo
(
	id int auto_increment comment '主键id'
		primary key,
	user_id int(11) unsigned default 0 not null comment '用户id',
	reg_channel_code varchar(100) default '' not null comment '用户注册渠道码',
	process_status tinyint unsigned default 0 not null comment '处理状态 0 未处理 1 已处理成功 2 处理异常',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	constraint uniq_user_id
		unique (user_id)
)
comment '新用户按渠道发放的红包';

create index idx_process_status_create_time
	on new_user_channel_promo (process_status, create_time);

create table if not exists notify_log
(
	id bigint auto_increment comment '主键ID 自增'
		primary key,
	message_id varchar(32) null comment '消息唯一标识',
	notify_type tinyint(2) null comment '通知类型(微信小程序、APP推送)',
	add_time datetime null comment '添加时间',
	constraint uniq_MID_NTF
		unique (message_id, notify_type)
)
comment '通知发送日志表';

create table if not exists prepay_batch
(
	id int unsigned auto_increment comment '主键'
		primary key,
	title varchar(200) default '' not null comment '批次名',
	batch_no varchar(40) default '' not null comment '途游卡批次号',
	card_type tinyint unsigned default 0 not null comment '卡类型',
	applicant varchar(50) default '' not null comment '申请人',
	par_value decimal(10,2) default 0.00 not null comment '面值',
	price decimal(10,2) default 0.00 not null comment '售价',
	status tinyint unsigned default 0 not null comment '途游卡批次状态',
	prepay_batch_sub varchar(333) default '{}' not null comment '途游卡批次扩展属性',
	effective_strategy varchar(200) default '{}' not null comment '途游卡生效策略',
	operater varchar(50) default '' not null comment '操作人',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	remark varchar(100) default '' not null comment '备注',
	constraint uniq_batch_no
		unique (batch_no)
)
comment '途游卡批次';

create table if not exists prepay_card
(
	id int unsigned auto_increment comment '主键'
		primary key,
	card_no varchar(20) default '' not null comment '途游卡号',
	password varchar(50) default '' not null comment '途游卡密码',
	member_id bigint unsigned default 0 not null comment '会员ID',
	from_date datetime default CURRENT_TIMESTAMP not null comment '有效开始时间',
	to_date datetime default CURRENT_TIMESTAMP not null comment '有效结束时间',
	batch_no varchar(40) default '' not null comment '批次号',
	par_value decimal(10,2) default 0.00 not null comment '初始金额',
	leftover_money decimal(10,2) default 0.00 not null comment '剩余金额',
	bind_time datetime default '1990-01-01 00:00:00' not null comment '绑定时间',
	status tinyint unsigned default 0 not null comment '途游卡状态',
	prepay_card_sub varchar(100) default '{}' not null comment '途游卡子属性',
	operator varchar(20) default '' not null comment '采编作废时有值',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	remark varchar(100) default '' not null comment '备注',
	constraint uniq_card_no
		unique (card_no)
)
comment '途游卡';

create index idx_member_id
	on prepay_card (member_id);

create table if not exists prepay_record
(
	id int unsigned auto_increment comment '主键'
		primary key,
	card_no varchar(20) default '' not null comment '途游卡号',
	member_id bigint unsigned default 0 not null comment '会员ID',
	serial_no varchar(50) default '' not null comment '流水号',
	refer_code varchar(20) default '' not null comment '关联码',
	money decimal(10,2) default 0.00 not null comment '变动金额',
	action tinyint unsigned default 0 not null comment '动作',
	prepay_record_sub varchar(1000) default '{}' not null comment '扩展字段',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	remark varchar(100) default '' not null comment '备注',
	constraint uniq_card_serial
		unique (card_no, serial_no)
)
comment '途游卡日志表';

create table if not exists promo_1
(
	PromoID bigint unsigned auto_increment comment 'id'
		primary key,
	ActivityChannelID int(11) unsigned default 0 not null comment '渠道名(关联ActivityChannel表的ActivityChannelID)',
	promo_code varchar(50) default '' null comment '卡券包编号  防止被遍历',
	ctrl_code varchar(50) default '' null comment '卡券包唯一性控制编码（userId_activityChannelID_periodkey）',
	user_id int(20) unsigned default 0 not null comment '用户ID',
	from_user_id int(20) unsigned default 0 not null comment '来源用户ID',
	activity_type tinyint unsigned default 0 not null comment '活动ID',
	title varchar(50) default '' null comment '展示title',
	flow_status tinyint unsigned default 0 not null comment '卡券包状态com.tujia.trading.promo.enums.EnumPromoFlowStatus',
	remind_status tinyint unsigned default 0 not null comment '卡券包提醒状态com.tujia.trading.promo.enums.EnumPromoRemindStatus',
	from_time datetime default '1970-01-01 09:00:00' not null comment '开始有效时间',
	to_time datetime default '1970-01-01 09:00:00' not null comment '结束有效时间',
	remark varchar(50) default '' null comment '附加说明',
	promo_use_strategy varchar(500) default '' null comment '一条促销卡券包的使用策略',
	create_time datetime default '1970-01-01 09:00:00' not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	enumProviderType tinyint unsigned default 1 not null comment '承担方类型',
	enumPromoPartyType tinyint unsigned default 0 not null comment '标识红包是哪类的,0途家，1去哪儿，2携程，flag值',
	activity_code varchar(40) default '' not null comment '活动策略Code(关联ascend_path)',
	constraint uniq_ctrl_code
		unique (ctrl_code),
	constraint uniq_promo_code
		unique (promo_code)
)
comment '促销卡券包记录表(根据user_id分表)用户领取红包记录';

create index idx_to_time_flow_status_remind_status
	on promo_1 (to_time, flow_status, remind_status);

create index idx_update_time
	on promo_1 (update_time);

create index idx_user_id_ActivityChannelID_create_time
	on promo_1 (user_id, ActivityChannelID, create_time);

create index idx_user_id_flow_status_create_time
	on promo_1 (user_id, flow_status, create_time);

create table if not exists promoactivityconfig
(
	PromoActivityConfigID int(11) unsigned auto_increment comment '主键ID，递增'
		primary key,
	activity_id int(11) unsigned default 0 not null comment '红包活动ID，外键',
	introduction varchar(200) default '' null comment '红包活动描述',
	activity_href varchar(100) default '' null comment '红包活动项链接地址',
	status tinyint unsigned default 0 not null comment '状态，1：显示，2：隐藏',
	creator varchar(30) default '' null comment '创建者',
	last_operator varchar(30) default '' null comment '最后的操作人',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null comment '最后更新时间'
)
comment '红包活动广告配置表';

create table if not exists promooplog
(
	PromoOpLogID bigint unsigned auto_increment comment 'id'
		primary key,
	operator varchar(30) default '' null comment '操作人',
	system_code tinyint unsigned default 0 not null comment '系统代码',
	system_name varchar(30) default '' null comment '系统名称',
	log_title varchar(30) default '' null comment '日志title',
	log_content text null comment '日志',
	op_type tinyint unsigned default 0 not null comment '操作类型 com.tujia.trading.promo.enums.EnumOpType',
	op_biz_code varchar(30) default '' null comment '操作业务的代号 com.tujia.trading.promo.enums.EnumOpBizCode',
	log_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '日志记录时间',
	data_id bigint unsigned default 0 not null comment '数据id'
)
comment '促销系统操作日志';

create index idx_log_time
	on promooplog (log_time);

create table if not exists promouselog
(
	PromoUseLogID bigint unsigned auto_increment comment 'id'
		primary key,
	promo_code varchar(50) default '' null comment '卡券包编号  防止被遍历',
	serial_no varchar(50) default '' null comment '卡券包使用流水号  防止重复提交',
	activity_type tinyint unsigned default 0 not null comment '活动ID',
	user_id bigint unsigned default 0 not null comment '用户ID',
	flow_status_from tinyint unsigned default 0 not null comment '卡券包状态起始状态com.tujia.trading.promo.enums.EnumPromoFlowStatus',
	flow_status_to tinyint unsigned default 0 not null comment '卡券包状态结束状态com.tujia.trading.promo.enums.EnumPromoFlowStatus',
	action tinyint unsigned default 0 not null comment '请求动作~使用 退回 作废等 com.tujia.trading.promo.enums.EnumPromoUseAction',
	order_num varchar(30) default '' null comment '关联订单号',
	promo_use_strategy varchar(500) default '' null comment '一条促销卡券包的使用策略',
	log_capture varchar(500) default '' null comment '一条促销卡券包的使用快照',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	remark varchar(50) default '' not null comment '附加说明',
	constraint uniq_serial_no
		unique (serial_no)
)
comment '促销卡券包使用记录表';

create index idx_order_num
	on promouselog (order_num);

create index idx_promo_code
	on promouselog (promo_code);

create index idx_user_id_action_update_time
	on promouselog (user_id, action, update_time);

create table if not exists rebate_integral_record
(
	id int unsigned auto_increment comment 'id'
		primary key,
	member_id bigint unsigned default 0 not null comment '途家用户id',
	refer_code varchar(36) default '' not null comment '关联码',
	flag int unsigned default 0 not null comment '记录标记',
	rebate_integral_record_sub varchar(700) default '{}' not null comment '附属属性',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	constraint uniq_refer_code
		unique (refer_code) comment '关联码唯一索引'
)
comment '订单离店返积分记录表';

create table if not exists redpacket_sign_up
(
	id bigint unsigned auto_increment comment 'id'
		primary key,
	activity_code varchar(50) default '' not null comment '关联的红包活动ID',
	hotel_id bigint unsigned default 0 not null comment '门店Id',
	unit_id bigint unsigned default 0 not null comment '房屋Id',
	sign_up_dimension tinyint(3) default 1 not null comment '报名维度（1门店 2房屋）',
	unit_name varchar(100) default '' not null comment '房屋名称',
	hotel_name varchar(100) default '' not null comment '门店名称',
	province_name varchar(100) default '' not null comment '城市上级区域名称',
	city_id int default 0 not null comment '城市ID',
	city_name varchar(60) default '' not null comment '城市名称',
	audit_status tinyint(3) default -1 not null comment '审核状态EnumAuditStatus',
	last_operator varchar(50) default '' not null comment '最后操作人',
	rule_detail varchar(2000) default '{}' not null comment '报名规则',
	give_up_reason varchar(2000) default '{}' not null comment '退出报名原因',
	create_time datetime default '1970-01-01 09:00:00' not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	constraint uniq_hotelid_activitycode_unitid
		unique (hotel_id, activity_code, unit_id)
)
comment '红包报名表';

create index idx_activitycode
	on redpacket_sign_up (activity_code);

create index idx_unitid_hotelid
	on redpacket_sign_up (unit_id, hotel_id);

create table if not exists redpacket_sign_up_operate_log
(
	id bigint unsigned auto_increment comment 'id'
		primary key,
	activity_code varchar(50) default '0' not null comment '关联的活动ID',
	hotel_id bigint unsigned default 0 not null comment '门店Id',
	unit_id bigint unsigned default 0 not null comment '关联的房屋ID',
	operate_type int default 0 not null comment '新增枚举值',
	operate_content varchar(3000) default '' not null comment '操作内容',
	operate_date_time datetime default '1970-01-01 09:00:00' not null comment '操作时间',
	operator_name varchar(50) default '' not null comment '操作人'
)
comment '红包报名日志表';

create index idx_hotelid_activitycode_unitid
	on redpacket_sign_up_operate_log (hotel_id, activity_code, unit_id);

create index idx_hotelid_unitid
	on redpacket_sign_up_operate_log (hotel_id, unit_id);

create table if not exists redpacket_strategy_unit
(
	id bigint unsigned auto_increment comment 'id'
		primary key,
	strategy_id bigint unsigned default 0 not null comment '策略Id',
	hotel_id bigint unsigned default 0 not null comment '门店Id',
	unit_id bigint unsigned default 0 not null comment '房屋Id',
	create_time datetime default '1970-01-01 09:00:00' not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	constraint uniq_hotelid_unitid_strategyid
		unique (hotel_id, unit_id, strategy_id)
)
comment '智能红包策略-适用房屋';

create index idx_unit_id
	on redpacket_strategy_unit (unit_id);

create table if not exists thirdpartyinvokeinfo
(
	ThirdPartyInvokeInfoId bigint unsigned auto_increment comment 'id'
		primary key,
	ActivityChannelId int(11) unsigned default 0 not null comment '活动渠道Id',
	activityChannel_name varchar(50) default '' null comment '活动渠道名称',
	thirdParty_name varchar(50) default '' null comment '第三方用户名称',
	mobile varchar(20) default '' null comment '手机号',
	OpenId varchar(50) default '' null comment 'openId',
	ThirdPartyUserId varchar(50) default '' null comment '第三方的用户Id',
	status tinyint unsigned default 0 not null comment '红包发放状态0-失败 1-成功 2-删除',
	remark varchar(200) default '' null comment '备注',
	create_time datetime default '1970-01-01 09:00:00' not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
comment '调用红包接口的第三方信息';

create index idx_channelId_status_create_time
	on thirdpartyinvokeinfo (create_time, ActivityChannelId, status);

create index idx_mobile
	on thirdpartyinvokeinfo (mobile);

create table if not exists usertag
(
	UserTagID bigint unsigned auto_increment comment '主键ID，递增'
		primary key,
	user_id bigint unsigned default 0 not null comment '用户ID',
	batch_no tinyint unsigned default 0 not null comment '批次号，比如每周同步一次，则批次号为每周在52周的序号',
	tag_type tinyint unsigned default 0 not null comment '用户标签类型，比如用户购买行为标签',
	tag_info varchar(500) default '' null comment '用户标签信息',
	create_time datetime default '1970-01-01 09:00:00' not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
	constraint uniq_user_id_tag_type
		unique (user_id, tag_type)
)
comment '用户标签';

create table if not exists wx_drainage_detail
(
	id bigint unsigned auto_increment comment '主键id'
		primary key,
	member_id bigint unsigned not null comment '途家会员id',
	promo_code varchar(50) default '' null comment '卡券包编号  防止被遍历',
	activity_channel_id int(11) unsigned default 0 not null comment '红包id(关联ActivityChannel表的ActivityChannelID)',
	activity_code varchar(40) default '' not null comment '活动策略Code(关联ascend_path)',
	receive int default 0 not null comment '领取状态:1成功，2失败',
	from_time datetime default CURRENT_TIMESTAMP not null comment '有效期开始日期',
	to_time datetime default CURRENT_TIMESTAMP not null comment '有效期结束日期',
	create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
	update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '最后修改时间',
	constraint uniq_idx_promo_code
		unique (promo_code) comment '唯一索引'
)
comment '微信引流红包领取详情';

create index idx_activity_channel_id_activity_code
	on wx_drainage_detail (activity_channel_id, activity_code);

create index idx_member_id
	on wx_drainage_detail (member_id);

