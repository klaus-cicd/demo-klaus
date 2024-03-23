package com.demo.security.service.impl;

import com.demo.security.constants.RedisKey;
import com.demo.security.entity.LoginUser;
import com.demo.security.entity.User;
import com.demo.security.service.LoginService;
import com.fd.auth.constant.AuthConstant;
import com.fd.auth.util.JwtUtil;
import com.klaus.fd.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Klaus
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("username or password is incorrect!");
        }

        // 使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        Map<String, Object> map = new HashMap<>(1);
        map.put(AuthConstant.USER_NAME, loginUser.getUsername());
        map.put(AuthConstant.USER_ID, loginUser.getUser().getId());
        long ttlMillis = 3600000;
        String jwt = JwtUtil.createToken(map);
        // authenticate存入redis
        RedisCache.set(String.format(RedisKey.JWT_TOKEN, userId), loginUser.getUsername(), Duration.ofMillis(ttlMillis * 2));

        // 把token响应给前端
        return jwt;
    }

    @Override
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        RedisCache.del(String.format(RedisKey.JWT_TOKEN, userid));
    }
}
