package com.heredi.nowait.infrastructure.auth.jwt;

import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.domain.auth.port.AuthRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
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
public class AuthJwtImpl implements AuthRepository {

    private final String SECRET_KEY = "HolaMundo_07."; // Cambia esto por una clave más segura
    private final String SALT = "valorAleatorio"; // Este salt debe ser único y aleatorio

    @Override
    public String generateToken(Long userId, String username) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String, Object> claims = new HashMap<>();
        //TODO: AUMENTAR EL TIEMPO DE SESION DEL JWT
        long EXPIRATION_TIME = 1 * 60 * 1000; // 1 minutos en milisegundos
        return Jwts.builder()
                .setClaims(claims)
                .claim("token_type", "ACCESS")
                .claim("userName", username)
                .claim("userId", userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKeyFromPassword(SECRET_KEY, SALT), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Date now = new Date();
        long EXPIRATION_TIME = 2592000000L; // 30 días en milisegundos
        Date validity = new Date(now.getTime() + EXPIRATION_TIME);
        UUID newRefreshToken = UUID.randomUUID();
        return Jwts.builder()
                .claim("token_type", "REFRESH")
                .claim("randomUUID", newRefreshToken)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getKeyFromPassword(SECRET_KEY, SALT), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractAllClaims(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            // Intentamos extraer las claims normalmente
            return Jwts.parserBuilder()
                    .setSigningKey(getKeyFromPassword(SECRET_KEY, SALT))
                    .build()
                    .parseClaimsJws(token)  // Esto es lo que verifica la firma y la expiración
                    .getBody();
        } catch (ExpiredJwtException e) {
            //TODO: mejorar por temas de seguridad.
            // Si el token ha expirado, aún podemos obtener las claims desde e.getClaims()
            return e.getClaims();  // Retornamos las claims incluso si está expirado
        } catch (SignatureException e) {
            throw new AppException(
                    AppErrorCode.INVALID_TOKEN_SIGNATURE,
                    "extractAllClaims",
                    "token: " + token,
                    HttpStatus.UNAUTHORIZED);
        }
    }

    public boolean validateRefreshToken(String refreshToken, String refreshUUID) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String extractedUUID = extractRandomUUID(refreshToken);
        return (extractedUUID.equals(refreshUUID) && !isExpired(refreshToken));
    }

    @Override
    public String extractTokenType(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractAllClaims(token).get("token_type", String.class);
    }

    @Override
    public String extractUsername(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractAllClaims(token).get("userName", String.class);
    }

    @Override
    public Long extractUserId(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractAllClaims(token).get("userId", Long.class);
    }

    @Override
    public String extractRandomUUID(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractAllClaims(token).get("randomUUID", String.class);
    }

    public boolean isExpired(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try{
            return extractAllClaims(token).getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            return false;
        }
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
