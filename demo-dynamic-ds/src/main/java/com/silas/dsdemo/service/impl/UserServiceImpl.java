package com.silas.dsdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silas.dsdemo.entity.User;
import com.silas.dsdemo.mapper.UserMapper;
import com.silas.dsdemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
