package com.heredi.nowait.domain.auth.port;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthRepository {

    String generateToken(Long userId, String username) throws NoSuchAlgorithmException, InvalidKeySpecException;

    String generateRefreshToken() throws NoSuchAlgorithmException, InvalidKeySpecException ;

    boolean isExpired(String token) throws NoSuchAlgorithmException, InvalidKeySpecException ;

    boolean validateRefreshToken(String token, String refreshUUID) throws NoSuchAlgorithmException, InvalidKeySpecException ;

    String extractTokenType(String token) throws NoSuchAlgorithmException, InvalidKeySpecException ;

    String extractUsername(String token) throws NoSuchAlgorithmException, InvalidKeySpecException ;

    Long extractUserId(String token) throws NoSuchAlgorithmException, InvalidKeySpecException ;

    String extractRandomUUID(String token) throws NoSuchAlgorithmException, InvalidKeySpecException ;
}
