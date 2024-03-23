package com.demo.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.security.entity.UserRole;
import com.demo.security.mapper.UserRoleMapper;
import com.demo.security.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {


    @Override
    public List<UserRole> getByUserId(Long id) {
        return getBaseMapper().selectList(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
    }
}
