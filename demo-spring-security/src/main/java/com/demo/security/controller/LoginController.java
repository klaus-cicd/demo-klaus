package com.demo.security.controller;

import com.demo.security.entity.User;
import com.demo.security.service.LoginService;
import com.fd.web.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Klaus
 */
@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        return Result.ok(loginService.login(user));
    }


    @PostMapping("/logout")
    public Result<Void> logout() {
        loginService.logout();
        return Result.ok();
    }


}
