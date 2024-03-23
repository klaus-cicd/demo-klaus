package com.silas.demo.easyexcel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silas.demo.easyexcel.entity.SailingRecord;
import com.silas.demo.easyexcel.mapper.SailingRecordMapper;
import com.silas.demo.easyexcel.service.SailingRecordService;
import org.springframework.stereotype.Service;

/**
 * @author Klaus
 */
@Service
public class SailingRecordServiceImpl extends ServiceImpl<SailingRecordMapper, SailingRecord> implements SailingRecordService {


    @Override
    public void getById() {

    }
}
