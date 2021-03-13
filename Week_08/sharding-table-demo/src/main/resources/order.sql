create table tb_order_0 (
 `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
 `creater_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
 `create_time` datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
 `updater_id` bigint(20) DEFAULT '0' COMMENT '更新人',
 `update_time` datetime NOT NULL DEFAULT NOW() COMMENT '更新时间',
 `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
 `good_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品ID',
 `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户Id',
 `express_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '物流信息Id',
 `actual_price` decimal(19, 2) NOT NULL DEFAULT '0.00' COMMENT '实付',
 `order_code` varchar(32) NOT NULL DEFAULT '' COMMENT '订单号',
 `order_time` datetime NOT NULL DEFAULT NOW() COMMENT '下单时间',
 `good_status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0 开启 1 关闭',
 PRIMARY KEY(id)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

create table tb_order_1 like tb_order_0;
create table tb_order_2 like tb_order_0;
create table tb_order_3 like tb_order_0;
create table tb_order_4 like tb_order_0;
create table tb_order_5 like tb_order_0;
create table tb_order_7 like tb_order_0;
create table tb_order_6 like tb_order_0;
create table tb_order_8 like tb_order_0;
create table tb_order_9 like tb_order_0;
create table tb_order_10 like tb_order_0;
create table tb_order_11 like tb_order_0;
create table tb_order_12 like tb_order_0;
create table tb_order_13 like tb_order_0;
create table tb_order_14 like tb_order_0;
create table tb_order_15 like tb_order_0;
create table tb_order_16 like tb_order_0;
create table tb_order_17 like tb_order_0;


truncate table tb_order_0;
truncate table tb_order_1;
truncate table tb_order_2;
truncate table tb_order_3;
truncate table tb_order_4;
truncate table tb_order_5;
truncate table tb_order_7;
truncate table tb_order_6;
truncate table tb_order_8;
truncate table tb_order_9;
truncate table tb_order_10;
truncate table tb_order_11;
truncate table tb_order_12;
truncate table tb_order_13;
truncate table tb_order_14;
truncate table tb_order_15;
truncate table tb_order_16;
truncate table tb_order_17;
