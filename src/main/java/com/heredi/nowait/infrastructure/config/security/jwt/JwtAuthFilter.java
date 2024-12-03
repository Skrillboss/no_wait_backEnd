package com.heredi.nowait.infrastructure.config.security.jwt;

import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthJwtImpl jwtProvider;
    private final UserDetailsService userDetailsService;
    private final UserJPARepository userJPARepository;

    public JwtAuthFilter(AuthJwtImpl jwtProvider, UserDetailsService userDetailsService, UserJPARepository userJPARepository) {
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.userJPARepository = userJPARepository;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        boolean shouldContinueToExceptionHandling = false;

        String token = null;
        String username = null;
        String randomUUID = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Extrae el token después de "Bearer "
            shouldContinueToExceptionHandling = true;

            try {
                if (isAccessToken(token)) {
                    username = jwtProvider.extractUsername(token); // Extraer username del accessToken
                } else {
                    randomUUID = jwtProvider.extractRandomUUID(token); // Extraer UUID del refreshToken
                }
            } catch (Exception e) {
                System.out.println("Token invalido: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            validateAccessToken(token, username, request);
        } else if (randomUUID != null) {
            validateRefreshToken(randomUUID, request);
        }

        // Continúa con el siguiente filtro solo si se requiere manejar excepciones
        if (shouldContinueToExceptionHandling) {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isAccessToken(String token) {
        try {
            String tokenType = jwtProvider.extractTokenType(token); // Extrae el tipo del token del claim
            return "ACCESS".equals(tokenType); // Devuelve true si es un accessToken
        } catch (Exception e) {
            return false; // Si ocurre un error, asumimos que no es un accessToken
        }
    }

    private void validateAccessToken(String token, String username, HttpServletRequest request) throws AppException, NoSuchAlgorithmException, InvalidKeySpecException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtProvider.isExpired(token)) {
            throw new AppException(
                    AppErrorCode.ACCESS_TOKEN_EXPIRED,
                    "doFilterInternal",
                    "",
                    HttpStatus.UNAUTHORIZED
            );
        }
        if (!jwtProvider.extractUsername(token).equals(username)) {
            throw new AppException(
                    AppErrorCode.USERNAME_IN_TOKEN_MISMATCH,
                    "doFilterInternal",
                    "",
                    HttpStatus.UNAUTHORIZED
            );
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void validateRefreshToken(String randomUUID, HttpServletRequest request) {
        UserEntity userEntity = userJPARepository.findByRefreshUUID(randomUUID)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getNickName());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
