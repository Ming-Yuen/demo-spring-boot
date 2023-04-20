package com.demo.common.filter;

import com.demo.common.servlet.ServletResponseCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(1)
public class ServletFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURL() + (StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "");
        log.info("IP : {}, port : {}, method : {}, url : {}", getRemortIP(request), request.getRemotePort(), request.getMethod().toLowerCase(), path);
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            log.info("body : {}", request.getReader().lines().collect(Collectors.joining()));
        }
        filterChain.doFilter(request, response);


        log.info("response : {}", new String(((ServletResponseCache)response).toByteArray()));
    }

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
