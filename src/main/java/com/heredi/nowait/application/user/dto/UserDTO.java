package com.heredi.nowait.application.user.dto;

import com.heredi.nowait.application.business.dto.BusinessDTO;
import com.heredi.nowait.application.paymentInfo.dto.PaymentInfoDTO;
import lombok.Data;
import java.util.List;

@Data
public class UserDTO {

    private String id;
    private String name;
    private String nickName;
    private String email;
    private String phoneNumber;
    private List<PaymentInfoDTO> paymentInfos;
    private BusinessDTO business;
}
