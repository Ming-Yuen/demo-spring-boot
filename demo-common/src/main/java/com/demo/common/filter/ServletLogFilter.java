package com.demo.common.filter;

import com.demo.common.servlet.ServletResponseCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ServletLogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURL() + (StringUtils.isNotBlank(request.getQueryString()) ? "?" + request.getQueryString() : "");
        log.info("IP : {}, port : {}, method : {}, url : {}", getRequestIP(request), request.getRemotePort(), request.getMethod().toLowerCase(), path);

        String body = request.getReader().lines().collect(Collectors.joining());
        if (StringUtils.isNotBlank(body)) {
            log.info("body : {}", body);
        }
        filterChain.doFilter(request, response);

        String responseBody = new String(((ServletResponseCache)response).toByteArray());
        if(StringUtils.isNotBlank(responseBody)) {
            log.info("response : {}", responseBody);
        }else{
            log.info("No response content");
        }
    }

    public String getRequestIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
