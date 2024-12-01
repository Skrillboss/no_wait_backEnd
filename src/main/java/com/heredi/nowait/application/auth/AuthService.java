package com.heredi.nowait.application.auth;

import com.heredi.nowait.domain.auth.port.AuthRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @SneakyThrows
    public String generateToken(Long userId, String username) {
        return authRepository.generateToken(userId, username);
    }

    @SneakyThrows
    public String generateRefreshToken() {
        return authRepository.generateRefreshToken();
    }

    @SneakyThrows
    public boolean isExpiredToken(String token){
        return authRepository.isExpired(token);
    }

    @SneakyThrows
    public boolean validateRefreshToken(String refreshToken, String refreshUUID) {
        return authRepository.validateRefreshToken(refreshToken, refreshUUID);
    }

    @SneakyThrows
    public String extractTokenType(String token){
        return authRepository.extractTokenType(token);
    }

    @SneakyThrows
    public String extractUsername(String token) {
        return authRepository.extractUsername(token);
    }

    @SneakyThrows
    public Long extractUserId(String token){
        return authRepository.extractUserId(token);
    }

    @SneakyThrows
    public String extractRandomUUID(String token) {
        return authRepository.extractRandomUUID(token);
    }
}
