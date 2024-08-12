package com.demo.common.interceptorfilter;

import com.demo.common.servlet.ServletRequestCache;
import com.demo.common.servlet.ServletResponseCache;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.*;

@Component
public class ServletCopyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new ServletRequestCache((HttpServletRequest)request), new ServletResponseCache((HttpServletResponse)response));
    }
}
