package com.demo.admin.security.filter;

import com.demo.admin.entity.UserInfo;
import com.demo.admin.security.JwtUtil;
import com.demo.admin.security.AdminUserDetails;
import com.demo.admin.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private JwtUtil jwt;
    private UserService userService;
    private final static String tokenHead = "Bearer ";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {System.out.println("test1");
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());// The part after "Bearer "
            try{
                String username = jwt.getUserNameFromToken(authToken);
                log.info("checking username:{}", username);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    List<UserInfo> umsAdminList = userService.findByUserName(UserInfo.class, username);
                    if (umsAdminList.isEmpty()) {
                        throw new UsernameNotFoundException("User name or password incorrect");
                    }
                    AdminUserDetails userDetails = new AdminUserDetails(umsAdminList.get(0));;
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
}
