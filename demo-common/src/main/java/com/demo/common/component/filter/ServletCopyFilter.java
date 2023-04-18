package com.demo.common.component.filter;

import javax.servlet.*;
import java.io.IOException;

public class ServletCopyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
