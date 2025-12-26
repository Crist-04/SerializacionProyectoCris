package com.digis01.CAlvarezProgramacionNCapasOctubre2025.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "password123456789012345678901234";
    private static final long EXPIRATION_TIME = 86400000;
    private static final long VERIFICATION_EXPIRATION_TIME = 172800000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username, int idUsuario) {
        return Jwts.builder()
                .claim("idUsuario", idUsuario)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateVerificationToken(String email, int idUsuario) {
        return Jwts.builder()
                .claim("idUsuario", idUsuario)
                .claim("email", email)
                .claim("type", "verification")
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + VERIFICATION_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String username) {
        try {
            final String tokenUsername = extractUsername(token);
            return (tokenUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateVerificationToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String tokenType = claims.get("type", String.class);
            return "verification".equals(tokenType) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Integer extractIdUsuario(String token) {
        return extractAllClaims(token).get("idUsuario", Integer.class);
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
