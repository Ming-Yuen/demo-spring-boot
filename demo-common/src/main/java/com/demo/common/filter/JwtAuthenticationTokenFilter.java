package com.demo.common.filter;

import com.demo.common.dto.ApiResponse;
import com.demo.common.security.AdminUserDetails;
import com.demo.common.security.JwtUtil;
import com.demo.common.util.Json;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private JwtUtil jwt;
//    private UserService userService;
    private final static String tokenHead = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try{
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith(tokenHead)) {
                String authToken = authHeader.substring(tokenHead.length());// The part after "Bearer "
                String username = jwt.getUserNameFromToken(authToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                    Users umsAdmin = userService.findByFirstUserName(username);
//                    if (umsAdmin == null) {
//                        throw new UsernameNotFoundException("User name or password incorrect");
//                    }
                    AdminUserDetails userDetails = new AdminUserDetails(username, null);;
                    if (jwt.validateToken(authToken)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        authentication.getName();
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                    }
                }
            }
            chain.doFilter(request, response);
        }catch (ExpiredJwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(Json.objectMapper.writeValueAsString(new ApiResponse().setError("Token expired")));
        }catch(UsernameNotFoundException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(Json.objectMapper.writeValueAsString(new ApiResponse().setError(e.getMessage())));
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
