package com.silas.demo.helloworld.interceptor;

import com.silas.demo.helloworld.exception.DDSException;
import com.silas.demo.helloworld.exception.DDSRCode;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Klaus
 */

public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Map<String, Object> map = new HashMap<>(2);
        // map.put("code", 40000);
        // map.put("msg", StrUtil.format("Unable to connect to datasource '{}'!", 666));
        // response.getWriter().write(JSON.toJSONString(map));
        // return false;
        throw new DDSException(DDSRCode.ADD_FAIL);
    }
}
