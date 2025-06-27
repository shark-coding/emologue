package com.project.emologue.service;

import com.project.emologue.model.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret-key}") String key) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    // 인증된 사용자(UserDetails)를 바탕으로 토큰 생성
    public String generateAccessToken(UserDetails userDetails) {
        var role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");
        return generateToken(userDetails.getUsername(), role);
    }

    // 토큰에서 사용자 식별자(subject)를 추출
    public String getUsername(String accessToken) {
        return getSubject(accessToken);
    }

    public String getRole(String accessToken) {
       try {
           return Jwts.parser()
                   .verifyWith(key)
                   .build()
                   .parseSignedClaims(accessToken)
                   .getPayload()
                   .get("role", String.class);
       } catch (JwtException exception) {
           logger.error("JwtException", exception);
           throw exception;
       }
    }

    // 토큰 생성
    private String generateToken(String subject, String role) {
        var now = new Date();
        var exp = new Date(now.getTime() + (1000 * 60 * 60 * 3));
        return Jwts.builder().subject(subject)
                .claim("role", role)
                .signWith(key)
                .issuedAt(now)
                .expiration(exp)
                .compact();
    }

    // 토큰 파싱 및 검증
    private String getSubject(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (JwtException exception) {
            logger.error("JwtException", exception);
            throw exception;
        }
    }

}
