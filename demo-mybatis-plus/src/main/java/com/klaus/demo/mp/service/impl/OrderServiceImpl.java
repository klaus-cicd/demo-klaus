package com.klaus.demo.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.klaus.demo.mp.entity.Order;
import com.klaus.demo.mp.mapper.OrderMapper;
import com.klaus.demo.mp.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {


    public void test() {

    }
}
