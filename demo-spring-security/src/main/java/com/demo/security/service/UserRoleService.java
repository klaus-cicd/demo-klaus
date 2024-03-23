package com.demo.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.security.entity.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据用户ID获取对应角色信息
     *
     * @param id 用户ID
     */
    List<UserRole> getByUserId(Long id);
}
