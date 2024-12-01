package com.heredi.nowait.infrastructure.model.paymentInfo.adapter;

import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;
import com.heredi.nowait.domain.paymentInfo.port.PaymentInfoRepository;
import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import com.heredi.nowait.infrastructure.model.paymentInfo.jpa.PaymentInfoJPARepository;
import com.heredi.nowait.infrastructure.model.paymentInfo.mapper.PaymentInfoEntityMapper;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public PaymentInfo createPaymentInfo(Long userId, PaymentInfo paymentInfo) {
        UserEntity userEntity = this.userJPARepository.getReferenceById(userId);
        PaymentInfoEntity paymentInfoEntity = this.paymentInfoEntityMapper.toPaymentInfoEntity(paymentInfo);
        paymentInfoEntity.setUserEntity(userEntity);
        return this.paymentInfoEntityMapper.toPaymentInfo(this.paymentInfoJPARepository.save(paymentInfoEntity));
    }

    @Override
    public List<PaymentInfo> getPaymentInfoByUserId(Long userId) {
        //TODO: se deben realizar pruebas para verificar si funciona bien en caso de no encontrar informacion de pago
        //en este usuario (es decir que no tenga informacion de pago, ya de por si no deberia poder acceder
        //un usuario normal, por lo tanto no deberia no existir el caso de que el usuario que acceda a esta funcionalidad
        //no tenga un informacion de pago pero hay que verificarlo por si acaso.
        List<PaymentInfoEntity> PaymentInfoEntityList = this.paymentInfoJPARepository.findByUserEntityId(userId);
        return PaymentInfoEntityList.stream()
                .map(paymentInfoEntity -> this.paymentInfoEntityMapper.toPaymentInfo(paymentInfoEntity))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentInfo updatePaymentInfo(Long userId, PaymentInfo paymentInfo) {

        PaymentInfoEntity paymentInfoEntity = this.paymentInfoJPARepository.findById(paymentInfo.getId())
                .orElseThrow(() -> new AppException(
                        AppErrorCode.PAYMENT_INFO_NOT_FOUND_BY_ID,
                        "updatePaymentInfo",
                        "PaymentInfo Id Provided: " + paymentInfo.getId(),
                        HttpStatus.NOT_FOUND));

        if (!paymentInfoEntity.getUserEntity().getId().equals(userId)) {
            throw new AppException(
                    AppErrorCode.PAYMENT_INFO_DOES_NOT_BELONG_TO_USER,
                    "updatePaymentInfo",
                    "Payment Info Id: " + paymentInfoEntity.getId() +
                            "User Id: " + userId,
                    HttpStatus.FORBIDDEN);
        }

        paymentInfoEntity.setCardNumber(paymentInfo.getCardNumber());
        paymentInfoEntity.setCardHolderName(paymentInfo.getCardHolderName());
        paymentInfoEntity.setExpiryDate(paymentInfo.getExpiryDate());
        paymentInfoEntity.setCardType(paymentInfo.getCardType());
        paymentInfoEntity.setCvv(paymentInfo.getCvv());

        return this.paymentInfoEntityMapper.toPaymentInfo(this.paymentInfoJPARepository.save(paymentInfoEntity));
    }
}
