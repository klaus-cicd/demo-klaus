package com.demo.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Klaus
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAnyAuthority(T(com.demo.security.enums.EnumRole).ADMIN.type())")
    public String hello() {
        return "hello";
    }
}