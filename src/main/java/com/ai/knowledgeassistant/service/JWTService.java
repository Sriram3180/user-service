package com.ai.knowledgeassistant.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JWTService {
    private static final String SECRET_KEY =
            "1a2b3c4d5e6g7h8i9j0klmnopqrstuvw";

    public String generateToken(String email) {
        return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()
                + 1000 * 60 * 60)).signWith(getSignInKey()).toString();
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

}
