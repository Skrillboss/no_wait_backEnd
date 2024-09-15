package com.heredi.nowait.application.user.dto.out;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponseDTO {
    private CreateUserResponseDTO userDTO;
    private String token;
    private String refreshToken;
}
