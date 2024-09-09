package com.heredi.nowait.application.user.mapper;

import com.heredi.nowait.application.business.mapper.BusinessMapper;
import com.heredi.nowait.application.paymentInfo.mapper.PaymentInfoMapper;
import com.heredi.nowait.application.user.dto.UserDTO;
import com.heredi.nowait.domain.model.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PaymentInfoMapper paymentInfoMapper;
    private final BusinessMapper businessMapper;

    public UserMapper(PaymentInfoMapper paymentInfoMapper, BusinessMapper businessMapper) {
        this.paymentInfoMapper = paymentInfoMapper;
        this.businessMapper = businessMapper;
    }

    public UserDTO toUserDTO(Users user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId().toString());
        dto.setName(user.getName());
        dto.setNickName(user.getNickName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setPaymentInfos(paymentInfoMapper.toPaymentInfoDTOs(user.getPaymentInfos()));
        dto.setBusiness(businessMapper.toBusinessDTO(user.getBusiness()));

        return dto;
    }
}