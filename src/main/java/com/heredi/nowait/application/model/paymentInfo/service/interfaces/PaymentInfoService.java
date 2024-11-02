package com.heredi.nowait.application.model.paymentInfo.service.interfaces;

import com.heredi.nowait.application.model.paymentInfo.dto.out.PaymentInfoResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentInfoService {
    List<PaymentInfoResponseDTO> getPaymentInfo(Long userId);
}
