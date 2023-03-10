package com.codestates.mainproject.group018.somojeon.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LogFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("# New Request!");
        log.info("Request adr: {}", request.getRemoteAddr());
        log.info("Request Origin header: {}", request.getHeader("Origin"));
        log.info("Request Method: {}", request.getMethod());
        log.info("Request URI: {}", request.getRequestURI());

        filterChain.doFilter(request, response);

    }
}
