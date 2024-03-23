package com.silas.demo.tdengine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silas.demo.tdengine.entity.Test;

import java.util.List;

/**
 * @author Klaus
 */
public interface TestService extends IService<Test> {


    List<Test> pageList(int page, long size);
}
