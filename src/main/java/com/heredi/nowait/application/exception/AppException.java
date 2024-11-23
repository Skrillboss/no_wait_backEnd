package com.heredi.nowait.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class AppException extends RuntimeException {
    private final List<AppErrorCode> errorCodes;
    private final HttpStatus status;

    public AppException(AppErrorCode errorCode, HttpStatus status) {
        super(errorCode.getFormattedError());
        this.errorCodes = List.of(errorCode);
        this.status = status;
    }

    public AppException(List<AppErrorCode> errorCodes, HttpStatus status) {
        super(generateMessage(errorCodes));
        this.errorCodes = errorCodes;
        this.status = status;
    }

    private static String generateMessage(List<AppErrorCode> errorCodes) {
        return errorCodes.stream()
                .map(AppErrorCode::getFormattedError)
                .reduce((msg1, msg2) -> msg1 + " || " + msg2)
                .orElse("Unknown error");
    }
}
