package com.klaus.demo.mp.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.klaus.demo.mp.entity.FlywaySchemaHistory;
import com.klaus.demo.mp.mapper.FlyWaySchemaHistoryMapper;
import com.klaus.demo.mp.service.FlywaySchemaHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Klaus
 */
@Service
public class FlywaySchemaHistoryServiceImpl extends ServiceImpl<FlyWaySchemaHistoryMapper, FlywaySchemaHistory> implements FlywaySchemaHistoryService {


    @Override
    public String getLastVersion() {
        List<FlywaySchemaHistory> list = list(Wrappers.<FlywaySchemaHistory>lambdaQuery().orderByDesc(FlywaySchemaHistory::getInstalledRank).last("LIMIT 1"));
        return null;
    }
}
