package com.demo.security.config;

import com.demo.security.filter.JwtAuthenticationTokenFilter;
import com.demo.security.filter.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 通过继承WebSecurityConfigurerAdapter配置SpringSecurity
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启接口访问权限限制, 还需在具体接口上增加@PreAuthorize注解
public class SecurityConfigV1 extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint securityAuthenticationEntryPointHandler;
    @Autowired
    private AccessDeniedHandler securityAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 前后端分离项目不存在CSRF攻击, 关闭即可
                .csrf().disable()
                // 不通过Session获取SecurityContext(默认是使用Session获取, 项目中已使用Redis存储Token)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().authorizeRequests()
                // 配置允许匿名访问的接口, 如登录接口
                .antMatchers(HttpMethod.POST, "/login", "/user").anonymous()
                // 除上面配置的匿名接口外, 所有接口都需要鉴权认证
                .anyRequest().authenticated()

                .and()
                // 认证异常处理器
                .exceptionHandling().authenticationEntryPoint(securityAuthenticationEntryPointHandler)
                // 授权异常处理器
                .accessDeniedHandler(securityAccessDeniedHandler)

                .and()
                // 将JWT验证过滤器添加, 并配置在UsernamePasswordAuthenticationFilter之前
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
