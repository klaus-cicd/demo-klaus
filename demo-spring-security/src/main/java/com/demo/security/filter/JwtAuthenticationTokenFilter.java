package com.demo.security.filter;

import com.demo.security.constants.RedisKey;
import com.demo.security.entity.LoginUser;
import com.fd.auth.constant.AuthConstant;
import com.fd.auth.util.JwtUtil;
import com.klaus.fd.util.RedisCache;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Klaus
 * 在Spring中, 建议通过继承OncePerRequestFilter来创建过滤器(只会执行一次, 避免在内部重定向时多次执行), 而非实现原生的Filter来创建过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader(AuthConstant.TOKEN);
        if (!StringUtils.hasText(token)) {
            /* 没有Token时交给这里直接放行, 交给SpringSecurity的过滤器验证
               1. 如果是白名单内的接口比如登录, SpringSecurity自带的过滤器会直接放行然后直接到达登录接口(在配置类内配置白名单);
               2. 如果接口不在白名单, 也是交给后面SpringSecurity的过滤器处理, 发现SecurityContextHolder的authentication没有值, 判断认证失败 */
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseToken(token);
            userid = JwtUtil.getUserId(claims);
        } catch (Exception e) {
            throw new AuthenticationServiceException("Illegal token!");
        }

        // 从redis中获取用户信息
        LoginUser loginUser = RedisCache.hGet(String.format(RedisKey.JWT_TOKEN, userid), LoginUser.class);
        if (Objects.isNull(loginUser)) {
            throw new AuthenticationServiceException("Please login!");
        }
        /* 存入SecurityContextHolder
           逻辑到这里表示已经认证成功, 需要使用3个参数的构造方法, 因为该构造内才会将"已认证"状态设置为true, principal -> 认证的用户信息(比如User对象), credentials无需填写, authorities -> 用户拥有的权限列表
           如果是未认证, 则使用2个入参的构造函数, principal-> 用户名, credentials -> 密码 */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}