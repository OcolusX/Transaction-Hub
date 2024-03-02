package com.TransactionHub.service.jwt;

import com.TransactionHub.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    public JwtService(
            @Value("${jwt.secret.key.access}") String accessSecretKey,
            @Value("${jwt.secret.key.refresh}") String refreshSecretKey) {
        this.accessSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretKey));
        this.refreshSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretKey));
    }

    public String generateAccessToken(UserDetails userDetails) {
        final LocalDateTime now = LocalDateTime.now();
        final Date expiration = Date.from(now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant());

        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("fullName", customUserDetails.getFullName());
            claims.put("roles", customUserDetails.getRoles());
        }
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(accessSecretKey)
                .claims(claims)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        final LocalDateTime now = LocalDateTime.now();
        final Date expiration = Date.from(now.plusDays(1).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(refreshSecretKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessSecretKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshSecretKey);
    }

    private boolean validateToken(String token, SecretKey secret) {
        Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token);
        return true;
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, accessSecretKey);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, refreshSecretKey);
    }

    private Claims getClaims(String token, SecretKey secret) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
