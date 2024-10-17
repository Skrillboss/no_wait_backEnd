package com.heredi.nowait.application.model.user.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RefreshTokenResponseDTO {
    String token;
    String refreshToken;
}
