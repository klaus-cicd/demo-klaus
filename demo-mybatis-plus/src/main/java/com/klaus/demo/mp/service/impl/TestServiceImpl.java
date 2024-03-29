package com.klaus.demo.mp.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.klaus.demo.comm.entity.Test;
import com.klaus.demo.mp.mapper.TestMapper;
import com.klaus.demo.mp.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author Klaus
 */
@Service
public class TestServiceImpl extends MPJBaseServiceImpl<TestMapper, Test> implements TestService {
}
