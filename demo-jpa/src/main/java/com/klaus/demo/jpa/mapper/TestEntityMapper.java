package com.klaus.demo.jpa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.klaus.demo.jpa.entity.TestEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Klaus
 */
@Mapper
public interface TestEntityMapper extends BaseMapper<TestEntity> {
}
