package com.heredi.nowait.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppErrorCode {

//**************************************************************************//
//*********************** Data Validation (1000 - 1999) ********************//
//**************************************************************************//

    RESOURCE_NOT_FOUND(1001, "The resource you want to access doesn't exist"),
    USER_ROLE_NOT_FOUND(1002, "The user's role was not sent when it was created"),
    USERNAME_EMPTY_TO_REGISTER(1003, "User name field cannot be empty"),
    //TODO: el texto de 3 y 30 debe estar guardado en constantes para igualarse a la validación
    USERNAME_LENGTH_ERROR(1004, "The user's name must be between 3 and 30 characters"),
    NICKNAME_EMPTY_TO_REGISTER(1005, "NickName field cannot be empty"),
    NICKNAME_LENGTH_ERROR(1006, "The user's nickName must be between 8 and 20 characters"),
    USER_EMAIL_EMPTY_TO_REGISTER(1007, "Email field cannot be empty"),
    USER_EMAIL_LENGTH_ERROR(1008, "The user's email must be between 6 and 254 characters"),
    EMAIL_FORMAT_ERROR(1009, "The email format is invalid."),
    PASSWORD_EMPTY_TO_REGISTER(1010, "Password field cannot be empty"),
    PASSWORD_LENGTH_ERROR(1011, "The password must be between 8 and 50 characters"),
    PASSWORD_FORMAT_ERROR(1012, "The password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"),
    PHONE_NUMBER_EMPTY_TO_REGISTER(1013, "Phone number field cannot be empty"),
    PHONE_NUMBER_FORMAT_ERROR(1014, "The phone number format is invalid. It must have 10 digits."),


    //**************************************************************************//
    //******************* Resource conflicts (2000 - 2999) *********************//
    //**************************************************************************//

    NICKNAME_ALREADY_EXIST(2001, "The nickname already exists in the system"),
    EMAIL_ALREADY_EXIST(2002, "The email is already registered"),
    PHONE_NUMBER_ALREADY_EXIST(2003, "The phone number is already in use"),
    BUSINESS_NOT_FOUND_BY_ID(2004, "Business with the provided ID was not found"),
    ITEM_NOT_FOUND_BY_ID(2005, "The item with the specified ID does not exist"),
    PAYMENT_INFO_NOT_FOUND_BY_ID(2006, "Payment information with the provided ID was not found"),
    USER_NOT_FOUND_BY_NICK_NAME(2007, "User with the provided NickName was not found"),
    USER_NOT_FOUND_BY_ID(2008, "User not found by the ID provided"),
    USER_NICKNAME_DOES_NOT_CORRESPOND_TO_ID(2009, "The user found by the nickName, does not contain the ID provided"),

    //**************************************************************************//
    //************* Authentication and Authorization (3000 - 3999) *************//
    //**************************************************************************//

    ACCESS_TOKEN_EXPIRED(3001, "The access token has expired"),
    INCORRECT_PASSWORD(3002, "The provided password is incorrect"),
    TOKEN_NOT_EXPIRED_YET(3003, "The provided token has not expired yet"),
    INVALID_REFRESH_TOKEN(3004, "The provided refresh token does not match the stored claim"),
    INVALID_TOKEN_SIGNATURE(3005, "The token signature is invalid"),
    USERNAME_IN_TOKEN_MISMATCH(3006, "The username extracted from the token does not match the provided username"),

    //**************************************************************************//
    //****************** Business logic error (4000 - 4999) ********************//
    //**************************************************************************//

    ADMIN_ROLE_NEEDS_PAYMENT_AND_BUSINESS(4001, "'ADMIN' roles need both payment and business information"),
    ONLY_ADMIN_CAN_RECORD_PAYMENT_OR_BUSINESS(4002, "Only 'ADMIN' roles can record payment or business information"),
    ITEM_NOT_FOUND_IN_BUSINESS(4003, "The provided itemId does not belong to the user's business."),
    PAYMENT_INFO_DOES_NOT_BELONG_TO_USER(4004, "Payment information does not belong to the user"),
    BUSINESS_DOES_NOT_BELONG_TO_USER(4005, "Business does not belong to the user"),
    CREATE_USER_NULL(4006, "The 'CreateUserRequestDTO' object is null"),

    //**************************************************************************//
    //****************** Internal Server error (5000 - 5999) *******************//
    //**************************************************************************//

    EMAIL_SENDING_FAILED(5001, "Failed to send the email"),

    //**************************************************************************//
    //********************** SAAS error (9999) *********************************//
    //**************************************************************************//

    UNEXPECTED_ERROR(9999, "An unexpected error occurred");

    private final int code;
    private final String description;

    public String getExplicationCode() {
        return code + ": " + description;
    }

    public String getDetails(String additionalDetails) {
        return code + ": " + additionalDetails;
    }
}
