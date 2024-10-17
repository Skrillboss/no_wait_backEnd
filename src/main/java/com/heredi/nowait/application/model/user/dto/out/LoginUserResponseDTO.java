package com.heredi.nowait.application.model.user.dto.out;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponseDTO {
    private UserResponseDTO userDTO;
    private String token;
    private String refreshToken;
}
