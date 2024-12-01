package com.heredi.nowait.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AppException extends RuntimeException {
    private final List<AppErrorCode> errorCodes;
    private final HttpStatus status;
    private final String methodName;
    private final List<String> details;

    public AppException(AppErrorCode errorCode, String methodName, String details, HttpStatus status) {
        super(errorCode.getExplicationCode());
        this.errorCodes = List.of(errorCode);
        this.methodName = methodName;
        this.details = List.of(details);
        this.status = status;
    }

    public AppException(List<AppErrorCode> errorCodes, String methodName, List<String> details, HttpStatus status) {
        super(generateMessage(errorCodes, new ArrayList<>()));
        this.errorCodes = errorCodes;
        this.methodName = methodName;
        this.details = details;
        this.status = status;
    }

    private static String generateMessage(List<AppErrorCode> errorCodes, List<String> details) {
        if (details.isEmpty()) {
            return errorCodes.stream()
                    .map(AppErrorCode::getExplicationCode)
                    .reduce((msg1, msg2) -> msg1 + " || " + msg2)
                    .orElse("Unknown error");
        } else {
            return errorCodes.stream()
                    .map(errorCode -> errorCode.getDetails(details.get(errorCodes.indexOf(errorCode))))
                    .reduce((msg1, msg2) -> msg1 + " || " + msg2)
                    .orElse("Unknown error");
        }
    }
}
