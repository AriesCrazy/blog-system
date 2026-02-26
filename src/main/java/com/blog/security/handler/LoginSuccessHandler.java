package com.blog.security.handler;

import com.blog.common.result.Result;
import com.blog.security.SecurityUser;
import com.blog.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        // 生成JWT Token
        String token = jwtUtil.generateToken(securityUser.getUsername(), securityUser.getUserId());

        log.info("用户 {} 登录成功", securityUser.getUsername());

        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", securityUser.getUserId());
        data.put("username", securityUser.getUsername());

        Result<Map<String, Object>> result = Result.success("登录成功", data);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
