package com.silas.demo.tdengine;

import com.dream.boot.bean.ConfigurationBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;

/**
 * @author Klaus
 */

@SpringBootApplication
public class TDengineApplication {


    public static void main(String[] args) {
        SpringApplication.run(TDengineApplication.class, args);
    }

    /**
     * 配置扫描的table和mapper路径
     *
     * @return
     */
    @Bean
    public ConfigurationBean configurationBean() {
        String packageName = this.getClass().getPackage().getName();
        List<String> pathList= Collections.singletonList(packageName);
        return new ConfigurationBean(pathList, pathList);
    }
}
