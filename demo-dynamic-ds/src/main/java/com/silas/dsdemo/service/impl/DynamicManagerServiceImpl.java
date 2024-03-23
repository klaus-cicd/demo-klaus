package com.silas.dsdemo.service.impl;

import cn.hutool.json.JSONUtil;
import com.fd.dds.DynamicDataSourceManager;
import com.silas.dsdemo.entity.DS;
import com.silas.dsdemo.service.DynamicManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

@Service
@Slf4j
public class DynamicManagerServiceImpl implements DynamicManagerService {
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/{0}?serverTimezone=GMT%2B8&characterEncoding=utf8&useSSL=false";


    @Override
    public boolean addDynamicDS(DS ds) {
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(MessageFormat.format(URL, ds.getDb()))
                .username("root").password("123456").build();

        try (Connection connection = dataSource.getConnection()) {
            if (connection == null) {
                log.error("Failed to get connection from DataSource, {}", JSONUtil.toJsonStr(ds));
                return false;
            }
        } catch (SQLException e) {
            log.error("Failed to get connection from DataSource, {}", JSONUtil.toJsonStr(ds), e);
            return false;
        }

        DynamicDataSourceManager.addDataSource(ds.getDb(), dataSource);
        log.info("注入数据源:{}", ds.getDb());
        return true;
    }
}
