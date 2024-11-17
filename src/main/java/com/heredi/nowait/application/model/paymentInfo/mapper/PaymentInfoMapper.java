package com.heredi.nowait.application.model.paymentInfo.mapper;

import com.heredi.nowait.application.model.paymentInfo.dto.in.PaymentInfoRequestDTO;
import com.heredi.nowait.application.model.paymentInfo.dto.out.PaymentInfoResponseDTO;
import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentInfoMapper {

    public PaymentInfoResponseDTO toPaymentInfoResponseDTO(PaymentInfo paymentInfo) {
        if (paymentInfo == null) {
            return null;
        }

        PaymentInfoResponseDTO dto = new PaymentInfoResponseDTO();
        dto.setId(paymentInfo.getId().toString());
        dto.setCardNumber(paymentInfo.getCardNumber());
        dto.setExpiryDate(paymentInfo.getExpiryDate().toString());
        dto.setCardHolderName(paymentInfo.getCardHolderName());

        return dto;
    }

    public List<PaymentInfoResponseDTO> toPaymentInfoListResponseDTO(List<PaymentInfo> paymentInfos) {
        if (paymentInfos == null) {
            return null;
        }

        return paymentInfos.stream()
                .map(this::toPaymentInfoResponseDTO)
                .collect(Collectors.toList());
    }

    public PaymentInfo toPaymentInfo(PaymentInfoRequestDTO paymentInfoDTO) {
        if (paymentInfoDTO == null) {
            return null;
        }

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardNumber(paymentInfoDTO.getCardNumber());
        paymentInfo.setCardHolderName(paymentInfoDTO.getCardHolderName());
        paymentInfo.setCardType(paymentInfoDTO.getCardType());
        paymentInfo.setExpiryDate(LocalDate.parse(paymentInfoDTO.getExpiryDate()));
        paymentInfo.setCvv(paymentInfoDTO.getCvv());

        return paymentInfo;
    }

    public List<PaymentInfo> toPaymentInfoList(List<PaymentInfoRequestDTO> paymentInfoRequestDTOList) {
        if (paymentInfoRequestDTOList == null) {
            return null;
        }

        return paymentInfoRequestDTOList.stream()
                .map(this::toPaymentInfo)
                .collect(Collectors.toList());
    }
}
