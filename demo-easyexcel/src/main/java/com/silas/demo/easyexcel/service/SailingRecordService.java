package com.silas.demo.easyexcel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silas.demo.easyexcel.entity.SailingRecord;

/**
 * @author Klaus
 */
public interface SailingRecordService extends IService<SailingRecord> {

    void getById();
}
