package com.demo.security.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class UsernamePasswordAuthenticationFilter extends org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            try (BufferedReader reader = request.getReader()) {
                String readerStr = "";
                String line;
                while ((line = reader.readLine()) != null) {
                    readerStr = readerStr.concat(line);
                }

                JSONObject jsonObject = JSON.parseObject(readerStr);
                String username = jsonObject.getString("username");
                String password = jsonObject.getString("password");
                username = username == null ? "" : username.trim();
                password = password == null ? "" : password;

                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                super.setDetails(request, authRequest);
                return super.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                throw new AuthenticationServiceException("AttemptAuthentication error");
            }
        } else {
            // 兼容原来的方式
            return super.attemptAuthentication(request, response);
        }
    }
}
