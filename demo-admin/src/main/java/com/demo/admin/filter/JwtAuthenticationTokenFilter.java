package com.demo.admin.filter;

import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dto.AdminUserDetails;
import com.demo.admin.service.UserService;
import com.demo.common.entity.UserInfo;
import com.demo.common.util.UserContextHolder;
import com.demo.admin.util.JwtManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtManager jwt;
    private final static String tokenHead = "Bearer ";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());// The part after "Bearer "
            try{
                String username = jwt.getUserNameFromToken(authToken);
                log.info("checking username:{}", username);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    AdminUserDetails userDetails = (AdminUserDetails) loadUserByUsername(username);
                    if (jwt.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        UserContextHolder.setUser(userDetails.getAdmin());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }catch (Exception e){
                log.error("JWT token error : " + e.getMessage(), e);
            }finally {
                UserContextHolder.clear();
            }
        }
        chain.doFilter(request, response);
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo umsAdminList = userService.findByUserName(username);
        if (umsAdminList != null) {
            return new AdminUserDetails(umsAdminList);
        }
        throw new UsernameNotFoundException("User name or password incorrect");
    }
}
