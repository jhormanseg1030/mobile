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
        // Usar una clave fija pero segura - puedes cambiar esta cadena por cualquier string de al menos 32 caracteres
        String fixedSecret = "MyVerySecureFixedSecretKeyThatIsAtLeast256BitsLongForJWTTokenGeneration12345678";
        return Keys.hmacShaKeyFor(fixedSecret.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpiration);

        return Jwts.builder()
        .subject(username)
        .issuedAt(now)
        .expiration(expirationDate)
        .signWith(getSecretKey())
        .compact();
    }

    public String getUsernameFromToken(String token){
         
         Claims claims = Jwts.parser()
         .verifyWith(getSecretKey())
         .build()
         .parseSignedClaims(token)
         .getPayload();
         return claims.getSubject();
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(authToken);
            return true;
        } catch (SecurityException ex) {
            System.err.println("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.err.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.err.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.err.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.err.println("JWT claims string is empty");
        } catch (JwtException ex) {
            System.err.println("JWT token validation error: " + ex.getMessage());
        }
        return false;
    }
}