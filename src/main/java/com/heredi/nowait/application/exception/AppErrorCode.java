package com.heredi.nowait.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    TOKEN_NOT_EXPIRED_YET("APP-3002", "The provided token has not expired yet"),
    INVALID_REFRESH_TOKEN("APP-3003", "The provided refresh token does not match the stored claim"),
    INVALID_TOKEN_SIGNATURE("APP-3004", "The token signature is invalid"),

    //**************************************************************************//
    //****************** Business logic error (4000 - 4999) ********************//
    //**************************************************************************//

    ITEM_NOT_FOUND("APP-4001", "The item with the specified ID does not exist"),
    ITEM_NOT_FOUND_IN_BUSINESS("APP-4004", "The provided itemId does not belong to the user's business."),

    //**************************************************************************//
    //****************** Internal Server error (5000 - 5999) *******************//
    //**************************************************************************//


    //**************************************************************************//
    //********************** SAAS error (4000 - 4999) **************************//
    //**************************************************************************//

    UNEXPECTED_ERROR("APP-9999", "An unexpected error occurred");

    private final String code;
    private String description;

    public String getFormattedError() {
        return code + ": " + description;
    }

    public AppErrorCode withDetails(String additionalDetails) {
        this.description = this.description + " Details: " + additionalDetails;
        return this;
    }
}