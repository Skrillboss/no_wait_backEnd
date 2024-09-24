package com.heredi.nowait.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtProvider {

    private final String SECRET_KEY = "HolaMundo_07."; // Cambia esto por una clave más segura
    private final String SALT = "valorAleatorio"; // Este salt debe ser único y aleatorio

    public String generateToken(Long userId, String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String, Object> claims = new HashMap<>();
        long EXPIRATION_TIME = 86400000; // 1 día en milisegundos
        return Jwts.builder()
                .setClaims(claims)
                .claim("userName", username)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKeyFromPassword(SECRET_KEY, SALT), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Date now = new Date();
        long EXPIRATION_TIME = 2592000000L; // 30 días en milisegundos
        Date validity = new Date(now.getTime() + EXPIRATION_TIME);
        UUID newRefreshToken = UUID.randomUUID();

        return Jwts.builder()
                .claim("randomUUID", newRefreshToken)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getKeyFromPassword(SECRET_KEY, SALT), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractAllClaims(token).get("userName", String.class);
    }

    public String extractRandomUUID(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractAllClaims(token).get("randomUUID", String.class);
    }

    private Claims extractAllClaims(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts.parserBuilder()
                .setSigningKey(getKeyFromPassword(SECRET_KEY, SALT)) // Ahora usamos HMAC adecuado
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        // Genera una clave de 256 bits (32 bytes)
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "HmacSHA256"); // Clave HMAC
    }
}
