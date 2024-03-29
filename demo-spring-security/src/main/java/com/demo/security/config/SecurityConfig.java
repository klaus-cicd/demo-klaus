package com.demo.security.config;

import com.demo.security.filter.JwtAuthenticationTokenFilter;
import com.demo.security.handler.SecurityAccessDeniedHandler;
import com.demo.security.handler.SecurityAuthenticationEntryPointHandler;
import com.demo.security.handler.SecurityFailureHandler;
import com.demo.security.handler.SecuritySuccessHandler;
import com.demo.security.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/**
 * 通过@EnableWebSecurity的形式配置SpringSecurity
 *
 * @author Klaus
 */
// @EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
        usernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager);
        usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(new SecuritySuccessHandler());
        usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(new SecurityFailureHandler());

        return usernamePasswordAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter) throws Exception {
        return httpSecurity
                // formLogin()会创建默认的UsernamePasswordAuthenticationFilter, 使用默认的UsernamePasswordAuthenticationFilter时需要配置认证成功处理器
                .formLogin()
                .and()
                // 添加一个前置自定义的过滤器(使用addFilterAt(A, B) 添加的过滤器A的Order值和B一样, 但实际上A会先执行(不是替换!!!);使用addFilterBefore(A,B)添加的过滤器A的Order为B的Order值-1), 用来处理Token
                .addFilterAt(usernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 关闭csrf(已使用JWT作为Token, 不存在CSRF攻击问题, 且必须关闭(在自定义的jwtFilter中未返回给前端csrf_token, 所以无法做csrf验证, 需要默认登录过滤器(UsernamePasswordAuthenticationToken)))
                .csrf().disable()
                // 基于Token, 不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 异常处理
                .exceptionHandling(
                        exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                                // 认证失败异常处理
                                .authenticationEntryPoint(new SecurityAuthenticationEntryPointHandler())
                                // 授权失败异常处理
                                .accessDeniedHandler(new SecurityAccessDeniedHandler())
                )
                // 对于登录接口 允许匿名访问
                .authorizeRequests(auth -> auth.antMatchers("/login").anonymous()
                        // 除上面外的所有请求全部需要鉴权认证
                        .anyRequest().authenticated()
                )
                .cors()
                .and()
                .userDetailsService(userDetailsServiceImpl)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 跨域配置
     * 跨域配置有很多种, 采用注入的方式配置会在Filter层进行跨域配置; 如果是继承WebMvcConfigurer重写addCorsMappings方式配置, 其实是在Interceptor生效, 相对比较低效
     * 在SpringSecurity项目中, 推荐使用注入(CorsFilter或CorsConfigurationSource) + HttpSecurity.cors (只需要在Filter过滤)
     * 不推荐HttpSecurity.cors + WebMvcConfigurer.addCorsMappings, 这种方式会导致跨域请求分别在 Filter 和 Interceptor 层各经历一次 CORS 验证
     * <a href="https://juejin.cn/post/7125999813664964615">详细说明见文章</a>
     *
     * @return CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // applyPermitDefaultValues() 表示采用默认配置(全部允许)
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}