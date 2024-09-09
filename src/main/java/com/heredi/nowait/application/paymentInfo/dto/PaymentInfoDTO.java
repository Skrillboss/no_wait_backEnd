package com.heredi.nowait.application.paymentInfo.dto;

import lombok.Data;

@Data
public class PaymentInfoDTO {

    private String id;
    private String cardNumber;
    private String expiryDate;
    private String cardHolderName;
}