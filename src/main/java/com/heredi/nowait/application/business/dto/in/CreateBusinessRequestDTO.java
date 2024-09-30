package com.heredi.nowait.application.business.dto.in;

import com.heredi.nowait.application.business.dto.out.BusinessResponseDTO;
import lombok.Data;

@Data
public class CreateBusinessRequestDTO {

    private BusinessResponseDTO businessDTO;
    private String userId;
}
