package com.heredi.nowait.application.paymentInfo.mapper;

import com.heredi.nowait.application.paymentInfo.dto.PaymentInfoDTO;
import com.heredi.nowait.domain.model.PaymentInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentInfoMapper {

    private PaymentInfoDTO toPaymentInfoDTO(PaymentInfo paymentInfo) {
        if (paymentInfo == null) {
            return null;
        }

        PaymentInfoDTO dto = new PaymentInfoDTO();
        dto.setId(paymentInfo.getId().toString());
        dto.setCardNumber(paymentInfo.getCardNumber());
        dto.setExpiryDate(paymentInfo.getExpiryDate().toString());
        dto.setCardHolderName(paymentInfo.getCardHolderName());

        return dto;
    }

    public List<PaymentInfoDTO> toPaymentInfoDTOs(List<PaymentInfo> paymentInfos) {
        if (paymentInfos == null) {
            return null;
        }

        return paymentInfos.stream()
                .map(this::toPaymentInfoDTO)
                .collect(Collectors.toList());
    }

    private PaymentInfo toPaymentInfo(PaymentInfoDTO paymentInfoDTO) {
        if (paymentInfoDTO == null) {
            return null;
        }

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setId(Long.valueOf(paymentInfoDTO.getId()));
        paymentInfo.setCardNumber(paymentInfoDTO.getCardNumber());
        paymentInfo.setCardHolderName(paymentInfoDTO.getCardHolderName());
        paymentInfo.setExpiryDate(LocalDate.parse(paymentInfoDTO.getExpiryDate()));

        return paymentInfo;
    }

    public List<PaymentInfo> toPaymentInfos(List<PaymentInfoDTO> paymentInfoDTOs) {
        if (paymentInfoDTOs == null) {
            return null;
        }

        return paymentInfoDTOs.stream()
                .map(this::toPaymentInfo)
                .collect(Collectors.toList());
    }
}
