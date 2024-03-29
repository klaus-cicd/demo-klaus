package com.klaus.demo.i18n.controller;

import com.klaus.fd.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * I18n测试控制器
 *
 * @author klaus
 * @date 2024/03/27
 */
@Slf4j
@RestController
@RequestMapping("/api/i18n")
@RequiredArgsConstructor
public class I18nTestController {

    private final HttpServletRequest request;

    @GetMapping
    public String i18n() {
        String message1 = MessageUtil.getMessage("A00001");
        String message2 = MessageUtil.getMessage("A00002", request.getHeader("lang"));
        return message1 + ", " + message2;
    }
}

