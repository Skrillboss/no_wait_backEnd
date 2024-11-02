package com.heredi.nowait.infrastructure.model.paymentInfo.mapper;

import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;
import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentInfoEntityMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "cardNumber", target = "cardNumber"),
            @Mapping(source = "cardHolderName", target = "cardHolderName"),
            @Mapping(source = "expiryDate", target = "expiryDate"),
            @Mapping(source = "cardType", target = "cardType"),
            @Mapping(source = "cvv", target = "cvv"),
    })
    PaymentInfo toPaymentInfo(PaymentInfoEntity paymentInfoEntity);

    @InheritInverseConfiguration
    PaymentInfoEntity toPaymentInfoEntity(PaymentInfo paymentInfo);
}
