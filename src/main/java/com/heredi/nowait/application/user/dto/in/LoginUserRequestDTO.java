package com.heredi.nowait.application.user.dto.in;

import lombok.Data;

@Data
public class LoginUserRequestDTO {
    private String nickName;
    private String password;
}
