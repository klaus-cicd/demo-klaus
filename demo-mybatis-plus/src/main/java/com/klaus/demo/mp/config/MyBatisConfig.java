package com.klaus.demo.mp.config;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

/**
 * MyBatis配置自动填充类
 *
 * @author Klaus
 */
public class MyBatisConfig implements MetaObjectHandler {


    /**
     * MP分页拦截器
     *
     * @return PaginationInnerInterceptor
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOptimizeJoin(true);
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);
        return paginationInnerInterceptor;
    }

    /**
     * 乐观锁
     *
     * @return OptimisticLockerInnerInterceptor
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(PaginationInnerInterceptor paginationInnerInterceptor, OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        return interceptor;
    }

    /**
     * 插入数据时自动执行
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 无值才更新, 有值时不更新
        strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 更新数据时自动执行
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // setFieldValByName方法是强制更新
        setFieldValByName("updateTime", DateUtil.now(), metaObject);
    }
}
