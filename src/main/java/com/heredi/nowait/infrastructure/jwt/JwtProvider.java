package com.heredi.nowait.infrastructure.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    private final SecretKey key;
    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS512; // Puedes cambiar a HS256 o HS384 si lo prefieres

    // Constructor para generar la clave secreta
    public JwtProvider() {
        this.key = Keys.secretKeyFor(algorithm); // Genera una clave segura para el algoritmo HMAC seleccionado
    }

    // Método para generar un token JWT
    public String generateToken(String userId, String nickName) {
        Date now = new Date();
        long oneHourValidation = 15 * 60 * 1000;
        Date validity = new Date(now.getTime() + oneHourValidation);

        return Jwts.builder()
                .claim("userId", userId)
                .claim("nickName", nickName)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, algorithm)
                .compact();
    }

    public String generateRefreshToken() {
        Date now = new Date();
        long thirtyDaysValidation = 2592000000L;
        Date validity = new Date(now.getTime() + thirtyDaysValidation);
        UUID newRefreshToken = UUID.randomUUID();

        return Jwts.builder()
                .claim("refreshToken", newRefreshToken)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, algorithm)
                .compact();
    }

    // Método para verificar y obtener los claims de un token JWT
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // El "subject" contiene el username en nuestro caso
    }

    // Método para validar el token
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
