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

    @Autowired
    private PaymentInfoEntityMapper paymentInfoEntityMapper;

    PaymentInfoRepositoryImpl(PaymentInfoJPARepository paymentInfoJPARepository) {
        this.paymentInfoJPARepository = paymentInfoJPARepository;
    }

    @Override
    public PaymentInfo createPaymentInfo(Long userId, PaymentInfo paymentInfo) {
        return null;
    }

    @Override
    public List<PaymentInfo> getPaymentInfoByUserId(Long userId) {
        List<PaymentInfoEntity> PaymentInfoEntityList = this.paymentInfoJPARepository.findByUserEntityId(userId);
        return PaymentInfoEntityList.stream()
                .map(paymentInfoEntity -> this.paymentInfoEntityMapper.toPaymentInfo(paymentInfoEntity))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentInfo updatePaymentInfo(Long userId, PaymentInfo paymentInfo) {

        PaymentInfoEntity paymentInfoEntity = this.paymentInfoJPARepository.findById(paymentInfo.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found by Id: " + userId));

        if(!paymentInfoEntity.getUserEntity().getId().equals(userId)){
            throw new IllegalArgumentException("Payment information does not belong to the user with Id: " + userId);
        }

        paymentInfoEntity.setCardNumber(paymentInfo.getCardNumber());
        paymentInfoEntity.setCardHolderName(paymentInfo.getCardHolderName());
        paymentInfoEntity.setExpiryDate(paymentInfo.getExpiryDate());
        paymentInfoEntity.setCardType(paymentInfo.getCardType());
        paymentInfoEntity.setCvv(paymentInfo.getCvv());

        return this.paymentInfoEntityMapper.toPaymentInfo(this.paymentInfoJPARepository.save(paymentInfoEntity));
    }
}
