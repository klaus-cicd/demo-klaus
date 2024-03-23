package com.klaus.demo.jpa.entity;

import com.klaus.demo.jpa.enmus.MoneyTypeEnum;
import lombok.Data;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Klaus
 */
@Data
@Table(appliesTo = "klaus_test", comment = "jpa自动生成的表, 表注释测试")
public class TestEntity {

    @Id
    @Column(columnDefinition = "id字段注释")
    private Long id;

    @Column(unique = true)
    private String name;

    private Boolean isNew;

    @Column(nullable = false)
    private LocalDateTime createTime;
    private MoneyTypeEnum moneyTypeEnum;
}
