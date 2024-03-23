package com.silas.demo.tdengine.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author Klaus
 */
// @Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 动态表名插件
        DynamicTableNameInnerInterceptor dynamicTabNameInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTabNameInterceptor.setTableNameHandler(new DaysTableNameParser());
        interceptor.addInnerInterceptor(dynamicTabNameInterceptor);
        return interceptor;
    }
}
