package com.heredi.nowait.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ApiError {
    private int statusCode;
    private List<Integer> errorCode;
    private List<String> codeExplication;
    private String methodName;
    private List<String> details;
}
