package com.heredi.nowait.application.user.dto.in;

import com.heredi.nowait.application.business.dto.in.BusinessRequestDTO;
import com.heredi.nowait.application.paymentInfo.dto.in.PaymentInfoRequestDTO;
import com.heredi.nowait.application.shift.dto.in.ShiftRequestDTO;
import com.heredi.nowait.application.shift.dto.out.ShiftResponseDTO;
import lombok.Data;
import java.util.List;

@Data
public class CreateUserRequestDTO {

    private String id;
    private String name;
    private String nickName;
    private String email;
    private String password;
    private String phoneNumber;
    private List<PaymentInfoRequestDTO> paymentInfos;
    private BusinessRequestDTO business;
    private List<ShiftRequestDTO> shifts;
}
