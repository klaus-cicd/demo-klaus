package com.silas.dsdemo.interceptor;

import com.fd.dds.DynamicDataSourceManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class DSInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantId = request.getParameter("tenantId");
        if (StringUtils.hasText(tenantId)) {
            DynamicDataSourceManager.switchTo(tenantId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        DynamicDataSourceManager.clearCurrentDataSourceKey();
    }
}
