package com.klaus.demo;

import com.fd.dds.DynamicDataSource;
import com.fd.dds.DynamicDataSourceManager;
import com.fd.dds.annotation.EnableDynamicDataSource;
import com.klaus.fd.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

/**
 * @author Klaus
 */
@Slf4j
@SpringBootApplication
@EnableDynamicDataSource
public class DemoFlywaydbApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoFlywaydbApplication.class, args);

        Flyway flyway = Flyway.configure()
                .dataSource(BeanUtil.getBean(DynamicDataSource.class))
                .locations("classpath:db/migration")
                .validateOnMigrate(false)
                .load();

        DataSource defaultMySqlDataSource = BeanUtil.getBean("defaultMySqlDataSource");
        DynamicDataSourceManager.switchToDefaultMySql();
        // 执行迁移脚本
        flyway.migrate();
    }
}