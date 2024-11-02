package com.heredi.nowait.infrastructure.model.paymentInfo.adapter;

import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;
import com.heredi.nowait.domain.paymentInfo.port.PaymentInfoRepository;
import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import com.heredi.nowait.infrastructure.model.paymentInfo.jpa.PaymentInfoJPARepository;
import com.heredi.nowait.infrastructure.model.paymentInfo.mapper.PaymentInfoEntityMapper;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class PaymentInfoRepositoryImpl implements PaymentInfoRepository {

    private final PaymentInfoJPARepository paymentInfoJPARepository;

    private final UserJPARepository userJPARepository;

    @Autowired
    private PaymentInfoEntityMapper paymentInfoEntityMapper;

    PaymentInfoRepositoryImpl(PaymentInfoJPARepository paymentInfoJPARepository, UserJPARepository userJPARepository) {
        this.paymentInfoJPARepository = paymentInfoJPARepository;
        this.userJPARepository = userJPARepository;
    }

    @Override
    public List<PaymentInfo> getPaymentInfoByUserId(Long userId) {
        UserEntity userEntity = userJPARepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found by Id: " + userId));
        List<PaymentInfoEntity> PaymentInfoEntityList = userEntity.getPaymentInfoEntityList();
        return PaymentInfoEntityList.stream()
                .map(paymentInfoEntity -> this.paymentInfoEntityMapper.toPaymentInfo(paymentInfoEntity))
                .collect(Collectors.toList());
    }
}
