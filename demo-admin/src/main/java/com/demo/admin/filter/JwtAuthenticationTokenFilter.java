package com.demo.admin.filter;

import com.demo.admin.entity.Users;
import com.demo.admin.security.AdminUserDetails;
import com.demo.admin.security.JwtUtil;
import com.demo.admin.service.UserService;
import com.demo.common.dto.ApiResponse;
import com.demo.common.util.Json;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private JwtUtil jwt;
    private UserService userService;
    private final static String tokenHead = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try{
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith(tokenHead)) {
                String authToken = authHeader.substring(tokenHead.length());// The part after "Bearer "
                String username = jwt.getUserNameFromToken(authToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Users umsAdmin = userService.findByFirstUserName(username);
                    if (umsAdmin == null) {
                        throw new UsernameNotFoundException("User name or password incorrect");
                    }
                    AdminUserDetails userDetails = new AdminUserDetails(umsAdmin);;
                    if (jwt.validateToken(authToken)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }
            }
            chain.doFilter(request, response);
        }catch(UsernameNotFoundException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(Json.objectMapper.writeValueAsString(new ApiResponse().setError(e.getMessage())));
        }
    }
}
