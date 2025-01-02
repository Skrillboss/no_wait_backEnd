package com.heredi.nowait.infrastructure.config.exception;

import com.heredi.nowait.application.exception.ApiError;
import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiError> handleAppException(AppException ex) {
        List<Integer> errorCodes = ex.getErrorCodes().stream().map(AppErrorCode::getCode).toList();
        List<String> explicationCode = generateMessage(ex.getErrorCodes(), AppErrorCode::getExplicationCode);
        List<String> detailsCode = generateMessage(ex.getErrorCodes(), errorCode ->
                errorCode.getDetails(ex.getDetails().get(ex.getErrorCodes().indexOf(errorCode))));

        ApiError error = new ApiError(
                ex.getStatus().value(),
                errorCodes,
                explicationCode,
                ex.getMethodName(),
                detailsCode
        );

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        if (ex instanceof NoHandlerFoundException) {
            ApiError error = new ApiError(
                    HttpStatus.NOT_FOUND.value(),
                    Collections.singletonList(AppErrorCode.RESOURCE_NOT_FOUND.getCode()), // Código de error personalizado
                    Collections.singletonList(AppErrorCode.RESOURCE_NOT_FOUND.getExplicationCode()),
                    "handleGeneralException",
                    Collections.emptyList()
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        // Manejo general de otras excepciones
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Collections.singletonList(AppErrorCode.UNEXPECTED_ERROR.getCode()),
                Collections.singletonList(AppErrorCode.UNEXPECTED_ERROR.getExplicationCode()),
                "unknown",
                Collections.singletonList(ex.getMessage())
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private List<String> generateMessage(List<AppErrorCode> errorCodes, Function<AppErrorCode, String> function) {
        return errorCodes.stream()
                .map(function)
                .collect(Collectors.toList());
    }
}
