package com.heredi.nowait.infrastructure.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.heredi.nowait.application.exception.ApiError;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
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
        try {
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
                boolean isExpired = jwtProvider.isExpired(token);
                boolean isUserNameMatched = jwtProvider.extractUsername(token).matches(username);
                if(isExpired){
                    throw new AppException(
                            AppErrorCode.ACCESS_TOKEN_EXPIRED,
                            "doFilterInternal",
                            "",
                            HttpStatus.UNAUTHORIZED
                    );
                }
                if(!isUserNameMatched){
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

            // Si es refreshToken, validamos por UUID
            else if (randomUUID != null) {
                // Consulta en la base de datos si el UUID del token coincide
                UserEntity userEntity = userJPARepository.findByRefreshUUID(randomUUID)
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
            filterChain.doFilter(request, response);

        } catch (AppException ex) {
            handleAppException(response, ex);
        } catch (Exception ex) {
            handleAppException(response, new AppException(
                    AppErrorCode.UNEXPECTED_ERROR,
                    "ExceptionHandlingFilter",
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            ));
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


    private void handleAppException(HttpServletResponse response, AppException ex) throws IOException {
        response.setStatus(ex.getStatus().value());
        response.setContentType("application/json");

        ApiError error = new ApiError(
                ex.getStatus().value(),
                ex.getErrorCodes().stream().map(AppErrorCode::getCode).toList(),
                List.of(ex.getMessage()),
                ex.getMethodName(),
                Collections.emptyList()
        );

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}