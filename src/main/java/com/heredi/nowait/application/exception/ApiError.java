package com.heredi.nowait.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiError {
    private int statusCode;
    private String errorCode;
    private String message;
}
