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

    USER_NOT_FOUND("APP-1001", "The specified user does not exist"),
    INVALID_USER_ID("APP-1002", "The provided user ID is invalid"),
    DUPLICATE_EMAIL("APP-1003", "The email is already associated with another user"),
    DATABASE_ERROR("APP-2001", "A database error occurred"),
    UNEXPECTED_ERROR("APP-9999", "An unexpected error occurred");

    private final String code;
    private final String description;

    public String getFormattedError() {
        return code + ": " + description;
    }
}
