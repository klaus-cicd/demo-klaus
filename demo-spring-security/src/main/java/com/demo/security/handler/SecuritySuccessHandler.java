package com.demo.security.handler;

import com.alibaba.fastjson2.JSON;
import com.demo.security.constants.RedisKey;
import com.demo.security.utils.TokenUtils;
import com.fd.auth.constant.AuthConstant;
import com.fd.auth.util.JwtUtil;
import com.fd.web.util.ServletUtil;
import com.klaus.fd.util.RedisCache;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Klaus
 */
public class SecuritySuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 使用userid生成token
        String userId = TokenUtils.getUserId();
        Map<String, Object> map = new HashMap<>(1);
        map.put("username", TokenUtils.getUsername());
        map.put(AuthConstant.USER_ID, TokenUtils.getUserId());
        long ttlMillis = 3600000;

        String jwt = JwtUtil.createToken(map);
        // authenticate存入redis
        RedisCache.set(String.format(RedisKey.JWT_TOKEN, userId), TokenUtils.getUsername(), Duration.ofMillis( ttlMillis * 2));
        // 把token响应给前端
        Map<String, String> responseMap = new HashMap<>(1);
        responseMap.put("token", jwt);
        ServletUtil.renderString(response, JSON.toJSONString(responseMap));
    }
}
