package com.klaus.demo.helloworld.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.klaus.demo.comm.entity.BaseEntity;
import com.klaus.demo.helloworld.anno.DataSync;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * @author Klaus
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DataSync(pullCron = "0 0 1 * * ?")
public class Test extends BaseEntity {
    @JsonDeserialize(using = DateDeserializers.TimestampDeserializer.class)
    private Timestamp ts;

    private String name;
}
