package com.demo.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.security.entity.User;
import com.demo.security.mapper.UserMapper;
import com.demo.security.service.UserService;
import com.demo.security.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void addUser(UserVO userVO) {
        // 检查是否用户名重复
        if (count(Wrappers.<User>lambdaQuery().eq(User::getUsername, userVO.getUsername())) > 0) {
            throw new RuntimeException("用户名已存在");
        }

        User user = buildInitUser(userVO);
        if (!save(user)) {
            throw new RuntimeException("注册用户失败");
        }

        // TODO 用户角色信息
    }

    private User buildInitUser(UserVO userVO) {
        User user = new User();
        user.setUsername(userVO.getUsername());
        user.setPassword(passwordEncoder.encode(userVO.getPwd()));
        user.setNickname(StringUtils.hasText(userVO.getNickname()) ? userVO.getNickname() : "请修改昵称");
        return user;
    }
}
