package com.demo.security.handler;

import com.alibaba.fastjson2.JSON;
import com.fd.web.response.Result;
import com.fd.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Klaus
 */
@Slf4j
@Configuration
public class SecurityAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        Result<Void> result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "Authentication failed, " + authException.getMessage());
        String json = JSON.toJSONString(result);
        log.warn("认证失败, msg:" + json);
        ServletUtil.renderString(response, json);
    }
}
