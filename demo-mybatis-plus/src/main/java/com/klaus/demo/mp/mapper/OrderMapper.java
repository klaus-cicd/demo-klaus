package com.klaus.demo.mp.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klaus.demo.mp.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
