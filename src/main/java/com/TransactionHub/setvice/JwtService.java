package com.TransactionHub.setvice;

import com.TransactionHub.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey secretKey;

    public JwtService() {
        this.secretKey = Jwts.SIG.HS256.key().build();
    }

    public String generateToken(UserDetails userDetails) {
        final LocalDateTime now = LocalDateTime.now();
        final Date expiration = Date.from(now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException expEx) {

        } catch (UnsupportedJwtException unsEx) {

        } catch (MalformedJwtException mjEx) {

        } catch (Exception e) {

        }
        return false;
    }

}
