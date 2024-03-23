package com.demo.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.security.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author Klaus
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户ID查询权限
     *
     * @param id 用户ID
     * @return 权限列表
     */
    List<String> selectPermsByUserId(Long id);
}