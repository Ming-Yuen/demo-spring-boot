package com.demo.admin.security.filter;

import com.demo.admin.entity.UserInfo;
import com.demo.admin.security.JwtManager;
import com.demo.admin.service.UserService;
import com.demo.admin.security.AdminUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private UserService userService;
    private JwtManager jwt;
    private final static String tokenHead = "Bearer ";
    public JwtAuthenticationTokenFilter(JwtManager jwt){
        this.jwt = jwt;
    }
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
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }catch (Exception e){
                log.error("JWT token error : " + e.getMessage(), e);
            }
        }
        chain.doFilter(request, response);
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserInfo> umsAdminList = userService.findByUserName(UserInfo.class, username);
        if (!umsAdminList.isEmpty()) {
            return new AdminUserDetails(umsAdminList.get(0));
        }
        throw new UsernameNotFoundException("User name or password incorrect");
    }
}
