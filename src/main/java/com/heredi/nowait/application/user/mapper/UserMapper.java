package com.heredi.nowait.application.user.mapper;

import com.heredi.nowait.application.business.mapper.BusinessMapper;
import com.heredi.nowait.application.paymentInfo.mapper.PaymentInfoMapper;
import com.heredi.nowait.application.role.dto.mapper.RoleMapper;
import com.heredi.nowait.application.shift.mapper.ShiftMapper;
import com.heredi.nowait.application.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.user.dto.out.UserResponseDTO;
import com.heredi.nowait.domain.user.model.Users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final RoleMapper roleMapper;
    private final PaymentInfoMapper paymentInfoMapper;
    private final BusinessMapper businessMapper;
    private final ShiftMapper shiftMapper;

    public UserMapper(RoleMapper roleMapper, PaymentInfoMapper paymentInfoMapper, BusinessMapper businessMapper, ShiftMapper shiftMapper) {
        this.roleMapper = roleMapper;
        this.paymentInfoMapper = paymentInfoMapper;
        this.businessMapper = businessMapper;
        this.shiftMapper = shiftMapper;
    }

    public UserResponseDTO toUserResponseDTO(Users user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setName(user.getName());
        dto.setNickName(user.getNickName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRoleDTO(roleMapper.toRoleDTO(user.getRole()));
        dto.setPaymentInfoResponseDTOList(paymentInfoMapper.toPaymentInfoListResponseDTO(user.getPaymentInfoList()));
        dto.setBusiness(businessMapper.toBusinessDTO(user.getBusiness()));

        return dto;
    }

    public Users toUser(CreateUserRequestDTO createUserRequestDTO) {
        if (createUserRequestDTO == null) {
            return null;
        }

        Users user = new Users();
        user.setName(createUserRequestDTO.getName());
        user.setNickName(createUserRequestDTO.getNickName());
        user.setEmail(createUserRequestDTO.getEmail());
        user.setPassword(createUserRequestDTO.getPassword());
        user.setPhoneNumber(createUserRequestDTO.getPhoneNumber());
        user.setRole(roleMapper.toRole(createUserRequestDTO.getRoleRequestDTO()));
        user.setPaymentInfoList(paymentInfoMapper.toPaymentInfoList(createUserRequestDTO.getPaymentInfoRequestDTOList()));
        user.setBusiness(businessMapper.toBusiness(createUserRequestDTO.getBusinessRequestDTO()));
        user.setShifts(shiftMapper.toShifts(createUserRequestDTO.getShifts()));

        return user;
    }
}
