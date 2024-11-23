package com.heredi.nowait.infrastructure.config.exception;

import com.heredi.nowait.application.exception.ApiError;
import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiError> handleAppException(AppException ex) {
        List<String> errorCodes = ex.getErrorCodes().stream().map(AppErrorCode::getCode).toList();
        ApiError error = new ApiError(
                ex.getStatus().value(),
                errorCodes,
                ex.getMessage()
        );
        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Collections.singletonList(AppErrorCode.UNEXPECTED_ERROR.getCode()),
                "An unexpected error occurred: " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
