package com.heredi.nowait.application.user.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RefreshTokenResponseDTO {
    String token;
    String refreshToken;
}
