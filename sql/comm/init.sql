DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test`
(
    `id`        bigint not null primary key comment 'id',
    `content`   varchar(30) default '' comment '内容',
    `time`      datetime,
    -- 枚举类型字段可以对应MySQL的字符串, 会将枚举字段名称映射到数据库
    `test_enum` varchar(30)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci comment 'demo.test';