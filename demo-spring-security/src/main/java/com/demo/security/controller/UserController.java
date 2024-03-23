package com.demo.security.controller;

import com.demo.security.service.UserService;
import com.demo.security.vo.UserVO;
import com.fd.web.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Result<Void> register(@RequestBody @Validated UserVO userVO) {
        userService.addUser(userVO);
        return Result.ok();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("T(com.demo.security.enums.EnumRole).ADMIN.type()")
    public Result<Void> delUser(@PathVariable long id) {
        return Result.ok();
    }


}
