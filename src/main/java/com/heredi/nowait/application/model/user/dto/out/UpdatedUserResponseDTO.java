package com.heredi.nowait.application.model.user.dto.out;

import com.heredi.nowait.application.model.user.dto.in.UpdateUserRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UpdatedUserResponseDTO {
    private UpdateUserRequestDTO updateUserRequestDTO;
    private String token;
}
