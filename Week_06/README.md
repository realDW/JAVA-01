## 基于电商场景的表结构设计

```sql



create table tb_user (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `creater_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
  `updater_id` bigint(20) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT NOW() COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `usename` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `gender` bit(1) NOT NULL DEFAULT b'1' COMMENT '性别',
  `mobile` varchar(11) NOT NULL DEFAULT '' COMMENT '用户手机号码',
  `addr` varchar(255) NOT NULL DEFAULT '' COMMENT '用户地址表',
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

create table tb_category (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `creater_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
  `updater_id` bigint(20) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT NOW() COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `cate_name` varchar(255) NOT NULL DEFAULT '' COMMENT '类别名',
  `cate_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '上级目录',
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='类目表';

create table tb_good (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `creater_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
  `updater_id` bigint(20) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT NOW() COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `good_name` varchar(255) NOT NULL DEFAULT '' COMMENT '商品名称',
  `good_desc` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `purchase_price` decimal(19, 2) NOT NULL DEFAULT '0.00' COMMENT '进价',
  `sell_price` decimal(19, 2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `good_img` varchar(255) NOT NULL DEFAULT '' COMMENT '商品图片',
  `good_status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0 下架 1 上架',
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';


create table tb_stock (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `creater_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
  `updater_id` bigint(20) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT NOW() COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `good_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品ID',
  `good_total` int(11) NOT NULL DEFAULT '0' COMMENT '总数',
  `good_left` int(11) NOT NULL DEFAULT '0' COMMENT '剩余',
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';


create table tb_order (
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
  `order_code` varchar(16) NOT NULL DEFAULT '' COMMENT '订单号',
  `order_time` datetime NOT NULL DEFAULT NOW() COMMENT '下单时间',
  `good_status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0 开启 1 关闭',
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

create table tb_express (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `creater_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT NOW() COMMENT '创建时间',
  `updater_id` bigint(20) DEFAULT '0' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT NOW() COMMENT '更新时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单ID',
  `from_addr` varchar(255) NOT NULL DEFAULT '' COMMENT '寄出地址',
  `to_addr` varchar(255) NOT NULL DEFAULT '' COMMENT '目标地址',
  `express_code` varchar(16) NOT NULL DEFAULT '' COMMENT '物流号',
  `express_company` varchar(255) NOT NULL DEFAULT '' COMMENT '物流公司',
  `express_status` tinyint NOT NULL DEFAULT '0' COMMENT '状态 0 未发出，1 途中，2已送达',
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流表';

```