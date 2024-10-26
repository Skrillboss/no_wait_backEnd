package com.heredi.nowait.application.model.user.dto.in;

import lombok.Data;

@Data
public class UpdateUserRequestDTO {
    private String name;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
}
