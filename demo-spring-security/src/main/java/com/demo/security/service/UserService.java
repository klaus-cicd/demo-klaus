package com.demo.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.security.entity.User;
import com.demo.security.vo.UserVO;

public interface UserService extends IService<User> {


    void addUser(UserVO userVO);

}
