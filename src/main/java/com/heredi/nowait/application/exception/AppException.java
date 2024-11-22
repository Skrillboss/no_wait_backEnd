package com.heredi.nowait.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class AppException extends RuntimeException {
    private final List<AppErrorCode> errorCodes;
    private final HttpStatus status;

    public AppException(AppErrorCode errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCodes = List.of(errorCode);
        this.status = status;
    }

    public AppException(List<AppErrorCode> errorCodes, String message, HttpStatus status) {
        super(message);
        this.errorCodes = errorCodes;
        this.status = status;
    }
}
