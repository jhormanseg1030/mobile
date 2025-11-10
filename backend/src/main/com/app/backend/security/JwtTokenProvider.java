package com.app.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
        .subject(username)
        .issuedAt(now)
        .expiretion(expiryDate)
        .signWith(getSigningKey())
        .compact();
    }

    public String getUsernameFromToken(String token){
         
         Claims claims = Jwts.parser()
         .verifyWith(getSigningKey())
         .build()
         .parseSignedClaims(token)
         .getPayload();
         return claims.getSubject();
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(authToken);
            return true;
        } catch (jwtExpiration | IllegalArgumentException e){
            return false;
        }
    }
}