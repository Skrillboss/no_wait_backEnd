package com.heredi.nowait.application.user.dto.out;

import com.heredi.nowait.application.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.paymentInfo.dto.out.PaymentInfoResponseDTO;
import com.heredi.nowait.application.role.dto.RoleDTO;
import com.heredi.nowait.application.shift.dto.out.ShiftResponseDTO;
import lombok.Data;
import java.util.List;

@Data
public class UserResponseDTO {

    private String name;
    private String nickName;
    private String email;
    private String phoneNumber;
    private RoleDTO roleDTO;
    private List<PaymentInfoResponseDTO> paymentInfoResponseDTOList;
    private BusinessResponseDTO business;
    private List<ShiftResponseDTO> shifts;
}
