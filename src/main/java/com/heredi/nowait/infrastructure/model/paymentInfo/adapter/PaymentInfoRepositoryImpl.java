package com.heredi.nowait.infrastructure.model.paymentInfo.adapter;

import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;
import com.heredi.nowait.domain.paymentInfo.port.PaymentInfoRepository;
import com.heredi.nowait.infrastructure.model.paymentInfo.jpa.PaymentInfoJPARepository;
import com.heredi.nowait.infrastructure.model.paymentInfo.mapper.PaymentInfoEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentInfoRepositoryImpl implements PaymentInfoRepository {

    private final PaymentInfoJPARepository paymentInfoJPARepository;

    private final PaymentInfoEntityMapper paymentInfoEntityMapper;

    PaymentInfoRepositoryImpl(PaymentInfoJPARepository paymentInfoJPARepository, PaymentInfoEntityMapper paymentInfoEntityMapper){
        this.paymentInfoJPARepository = paymentInfoJPARepository;
        this.paymentInfoEntityMapper = paymentInfoEntityMapper;
    }

    @Override
    public List<PaymentInfo> getPaymentInfoByUserId(Long userId) {
        return List.of();
    }
}
