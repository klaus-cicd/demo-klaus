package com.klaus.demo.mp.service.impl;


import com.klaus.demo.mp.entity.Order;
import com.klaus.demo.mp.mapper.OrderMapper;
import com.klaus.demo.mp.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;
    private long id;

    /**
     * 测试插入数据时创建时间自动更新
     *
     * @throws InterruptedException
     */
    @Test
    void testInsertFill() throws InterruptedException {
        Order order = new Order();
        order.setCode("test_auto_fill");
        orderService.save(order);
        id = order.getId();

        Thread.sleep(1000);
        testUpdateFill();
    }

    @Test
    void testInsertFillV2() throws InterruptedException {
        Order order = new Order();
        order.setCode("test_auto_fill");
        orderMapper.insert(order);
        id = order.getId();
        Thread.sleep(1000);
        testUpdateFill();
    }


    @Test
    void testUpdateFill() {
        Order order = new Order();
        order.setId(id);
        order.setCode("test_auto_fill2");

        orderMapper.updateById(order);
    }


}