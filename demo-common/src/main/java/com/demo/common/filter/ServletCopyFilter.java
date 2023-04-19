package com.demo.common.filter;

import com.demo.common.servlet.ServletRequestCache;
import com.demo.common.servlet.ServletResponseCache;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Component
@Order(0)
public class ServletCopyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new ServletRequestCache((HttpServletRequest)request), new ServletResponseCache((HttpServletResponse)response));
    }


}
