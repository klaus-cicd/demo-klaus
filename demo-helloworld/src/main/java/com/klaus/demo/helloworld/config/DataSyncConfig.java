package com.klaus.demo.helloworld.config;

import lombok.Builder;
import lombok.Data;

/**
 * @author Silas
 */
@Data
@Builder
public class DataSyncConfig {

    private String tableName;
    private String pullCron;
    private String pushCron;
    private String uriPrefix;

}
