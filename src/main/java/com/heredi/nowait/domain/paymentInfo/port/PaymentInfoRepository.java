package com.heredi.nowait.domain.paymentInfo.port;

import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;

import java.util.List;

public interface PaymentInfoRepository {
    List<PaymentInfo> getPaymentInfoByUserId(Long userId);
}
