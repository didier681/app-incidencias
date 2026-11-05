package com.didier.incidencias_backend.infrastructure.security;

import com.didier.incidencias_backend.domain.port.out.TokenGeneratorPort;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil implements TokenGeneratorPort {

    private final SecretKey key = Keys.hmacShaKeyFor(
            "clave-secreta-super-larga-para-jwt-tcc-incidencias-2026".getBytes()
    );

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 horas

    @Override
    public String generarToken(String correo, String rol) {
        return Jwts.builder()
                .subject(correo)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extraerCorreo(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}