package com.demo.security.service;


import com.demo.security.entity.User;

/**
 * @author Klaus
 */
public interface LoginService {

    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return Token
     */
    String login(User user);

    /**
     * 退出登录
     */
    void logout();
}
