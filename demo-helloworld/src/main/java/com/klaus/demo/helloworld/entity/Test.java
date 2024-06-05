package com.klaus.demo.helloworld.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.klaus.demo.helloworld.anno.Sync;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Klaus
 */
@Data
@Sync
public class Test {
    @JsonDeserialize(using = DateDeserializers.TimestampDeserializer.class)
    private Timestamp ts;
}
