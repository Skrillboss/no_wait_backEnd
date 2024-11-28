package com.heredi.nowait.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppErrorCode {

    //**************************************************************************//
    //*********************** Data Validation (1000 - 1999) ********************//
    //**************************************************************************//

    //**************************************************************************//
    //******************* Resource conflicts (2000 - 2999) *********************//
    //**************************************************************************//

    NICKNAME_ALREADY_EXIST("APP-2001", "The nickname already exists in the system"),
    EMAIL_ALREADY_EXIST("APP-2002", "The email is already registered"),
    PHONE_NUMBER_ALREADY_EXIST("APP-2003", "The phone number is already in use"),

    //**************************************************************************//
    //************* Authentication and Authorization (3000 - 3999) *************//
    //**************************************************************************//

    TOKEN_NOT_EXPIRED("APP-3002", "The provided token has not expired yet"),
    INVALID_REFRESH_TOKEN("APP-3003", "The provided refresh token does not match the stored claim"),
    INVALID_TOKEN_SIGNATURE("APP-3003", "The token signature is invalid"),

    UNEXPECTED_ERROR("APP-9999", "An unexpected error occurred");

    private final String code;
    private final String description;

    public String getFormattedError() {
        return code + ": " + description;
    }
}
