package com.heredi.nowait.application.model.paymentInfo.service.interfaces;

import com.heredi.nowait.application.model.paymentInfo.dto.in.PaymentInfoRequestDTO;
import com.heredi.nowait.application.model.paymentInfo.dto.out.PaymentInfoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentInfoService {
    PaymentInfoResponseDTO createPaymentInfo(Long userId, PaymentInfoRequestDTO paymentInfoRequestDTO);
    List<PaymentInfoResponseDTO> getPaymentInfo(Long userId);
    PaymentInfoResponseDTO updatePaymentInfo(String paymentInfoId, PaymentInfoRequestDTO paymentInfoRequestDTO, Long userId);
}
