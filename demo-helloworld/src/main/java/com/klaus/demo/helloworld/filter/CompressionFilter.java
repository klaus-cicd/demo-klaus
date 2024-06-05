package com.klaus.demo.helloworld.filter;

import com.klaus.demo.helloworld.util.GzipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 压缩过滤器
 *
 * @author klaus
 * @date 2024/01/03
 */
@Component
@Slf4j
public class CompressionFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String encoding = request.getHeader("Content-Encoding");
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            // 解压缩请求体
            ServletInputStream inputStream = request.getInputStream();
            byte[] compressedBytes = toByteArray(inputStream);
            byte[] decompressedBytes = GzipUtil.decompress(compressedBytes);
            log.info(new String(decompressedBytes));
        }
        filterChain.doFilter(request, response);
    }

    public static byte[] toByteArray(ServletInputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }


}