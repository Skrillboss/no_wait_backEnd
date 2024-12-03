package com.heredi.nowait.infrastructure.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heredi.nowait.application.exception.ApiError;
import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class ExceptionHandlingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
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

    private void handleAppException(HttpServletResponse response, AppException ex) throws IOException {
        response.setStatus(ex.getStatus().value());
        response.setContentType("application/json");

        ApiError error = new ApiError(
                ex.getStatus().value(),
                ex.getErrorCodes().stream().map(AppErrorCode::getCode).toList(),
                List.of("Error processing request: " + ex.getMessage()),
                ex.getMethodName(),
                Collections.emptyList()
        );

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(error));
    }
}
