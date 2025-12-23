package com.Security.Security.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;

@Service
public class JWTService {


    private static final String SECRET_KEY =
            "minha-chave-super-secreta-com-pelo-menos-32-caracteres";

    // ⏱ 1 hora
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;


    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }


    public String generateToken(String email, Set<String> roles) {
        return Jwts.builder()
                .setSubject(email) //  subject = identidade do usuário
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) { // extrai email do token
        return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey()) // ✅ NÃO verifyWith
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }


    public boolean isTokenValid(String token, String userEmail) { // validar token
        final String username = extractUsername(token);
        return username.equals(userEmail) && !isTokenExpired(token);
    }

}
