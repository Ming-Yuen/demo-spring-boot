package com.demo.admin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
@NoArgsConstructor
public class JwtUtil {

    private String secret;
    private Long expiration;
    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration){
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(String username, String password) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("sub", username);
        map.put("created", new Date());

        return Jwts.builder()
                .setId(username)
                .setClaims(map)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        boolean userValidate = username.equals(userDetails.getUsername());
        if(!userValidate){
            log.trace("Incorrect username");
            return false;
        }
        boolean expired = isTokenExpired(token);
        if(expired){
            log.trace("Token expired");
            return false;
        }
        return true;
    }

    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
}
