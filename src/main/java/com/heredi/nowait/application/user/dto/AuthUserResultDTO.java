package com.heredi.nowait.application.user.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserResultDTO {
    private UserDTO userDTO;
    private String token;
    private String refreshToken;
}
