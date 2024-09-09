package com.heredi.nowait.application.paymentInfo.mapper;
import com.heredi.nowait.application.paymentInfo.dto.PaymentInfoDTO;
import com.heredi.nowait.domain.model.PaymentInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class PaymentInfoMapper {

    public PaymentInfoDTO toPaymentInfoDTO(PaymentInfo paymentInfo) {
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
}

