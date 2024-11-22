package com.heredi.nowait.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppErrorCode {
    USER_NOT_FOUND("APP-1001"),
    INVALID_USER_ID("APP-1002"),
    DUPLICATE_EMAIL("APP-1003"),
    DATABASE_ERROR("APP-2001"),
    UNEXPECTED_ERROR("APP-9999");

    private final String code;
}
