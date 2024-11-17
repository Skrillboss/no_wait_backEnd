package com.heredi.nowait.domain.paymentInfo.port;

import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;

import java.util.List;

public interface PaymentInfoRepository {
    PaymentInfo createPaymentInfo(Long userId, PaymentInfo paymentInfo);
    List<PaymentInfo> getPaymentInfoByUserId(Long userId);
    PaymentInfo updatePaymentInfo(Long userId, PaymentInfo paymentInfo);
}
