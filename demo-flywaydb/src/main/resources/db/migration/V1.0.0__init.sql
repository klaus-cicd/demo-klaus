-- 创建用户表
CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`           bigint      not null auto_increment comment 'ID',
    `name`         varchar(20) not null default '' comment '姓名',
    `age`          int         not null default 0 comment '年龄',
    `username`     varchar(20) not null default '' comment '用户名',
    `passwd`       varchar(20) not null default '' comment '密码',
    `birthday`     date comment '生日',
    `gmt_create`   datetime    not null default now() comment '创建时间(UTC)',
    `gmt_modified` datetime    not null default now() comment '修改时间((UTC)',
    primary key (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户表';

-- 插入初始化数据
INSERT INTO `t_user` (`name`, `age`, `username`, `passwd`)
values ('Admin', 18, 'admin', 'admin'),
       ('Test', 16, 'test', 'test');