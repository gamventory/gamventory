package com.gamventory.config;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
* 인증 되지 않는 사용가 인증이 필요한 페이지에 접근할 때 사용되는 클래스
*/

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

//            response.sendRedirect("/members/login");
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//            logger.error("Unauthorized error. Reason: " + authException.getMessage());

        // Logging additional request details
        logger.error("Request URL: " + request.getRequestURL());
        logger.error("HTTP Method: " + request.getMethod());
        logger.error("Header : "+ Collections.list(request.getHeaderNames()).stream()
                .map(headerName -> headerName + ": " + Collections.list(request.getHeaders(headerName)))
                .collect(Collectors.joining(", ")));
        logger.error("Request Parameters: " + request.getParameterMap());
        logger.error("Client IP: " + request.getRemoteAddr());

        // Original error logging
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        logger.error("Unauthorized error. Reason: " + authException.getMessage());

        }
    
}
