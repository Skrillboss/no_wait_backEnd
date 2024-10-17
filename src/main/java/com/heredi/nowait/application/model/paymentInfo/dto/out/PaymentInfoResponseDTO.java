package com.heredi.nowait.application.model.paymentInfo.dto.out;

import lombok.Data;

@Data
public class PaymentInfoResponseDTO {

    private String id;
    private String cardNumber;
    private String expiryDate;
    private String cardHolderName;
}