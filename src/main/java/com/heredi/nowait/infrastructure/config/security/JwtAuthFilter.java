package com.heredi.nowait.infrastructure.config.security;


import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
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

        String token = null;
        String username = null;
        String randomUUID = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7); // Extrae el token después de "Bearer "

            try {
                // Comprueba si el token es de acceso o refresh basándote en los claims o longitud del token
                if (isAccessToken(token)) {
                    username = jwtProvider.extractUsername(token); // Extraer username del accessToken
                } else {
                    randomUUID = jwtProvider.extractRandomUUID(token); // Extraer UUID del refreshToken
                }
            } catch (Exception e) {
                System.out.println("Token invalido: " + e.getMessage());
            }
        }

        // Si es accessToken, validamos por username
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            boolean isValidated = jwtProvider.validateToken(token, username);

            if (isValidated) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Si es refreshToken, validamos por UUID
        else if (randomUUID != null) {
            // Consulta en la base de datos si el UUID del token coincide
            UserEntity userEntity = userJPARepository.findByRefreshToken(randomUUID)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            if (userEntity != null) {
                // El token es válido, extraemos los detalles del usuario
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEntity.getNickName());

                // Crear la autenticación usando el UUID validado
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continúa el filtro
        filterChain.doFilter(request, response);
    }

    private boolean isAccessToken(String token) {
        try {
            String tokenType = jwtProvider.extractTokenType(token); // Extrae el tipo del token del claim
            return "ACCESS".equals(tokenType); // Devuelve true si es un accessToken
        } catch (Exception e) {
            return false; // Si ocurre un error, asumimos que no es un accessToken
        }
    }
}
