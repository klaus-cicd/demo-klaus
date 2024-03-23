package com.demo.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demo.security.entity.LoginUser;
import com.demo.security.entity.User;
import com.demo.security.entity.UserRole;
import com.demo.security.mapper.UserMapper;
import com.demo.security.service.UserRoleService;
import com.demo.security.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author Klaus
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;
    private final UserRoleService userRoleService;

    @Autowired
    public UserDetailsServiceImpl(UserMapper userMapper, UserRoleService userRoleService) {
        this.userMapper = userMapper;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户信息
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));

        // 如果查询不到数据就通过抛出异常来给出提示
        if (Objects.isNull(user)) {
            log.error("Couldn't find this user, username={}", username);
            throw new RuntimeException("Couldn't find this user!");
        }
        TokenUtils.setUserId(String.valueOf(user.getId()));
        TokenUtils.setUsername(username);

        // 根据用户查询权限信息
        List<UserRole> userRoles = userRoleService.getByUserId(user.getId());

        // 封装成UserDetails对象返回
        return new LoginUser(user, userRoles.stream().map(UserRole::getRoleType).collect(Collectors.toList()));
    }
}