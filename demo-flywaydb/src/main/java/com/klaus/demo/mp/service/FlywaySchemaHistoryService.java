package com.klaus.demo.mp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.klaus.demo.mp.entity.FlywaySchemaHistory;

/**
 * @author Klaus
 */
public interface FlywaySchemaHistoryService extends IService<FlywaySchemaHistory> {

    /**
     * 获取最新版本
     *
     * @return {@link String}
     */
    String getLastVersion();
}
