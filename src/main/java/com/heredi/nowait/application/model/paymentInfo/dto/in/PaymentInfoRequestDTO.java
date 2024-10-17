package com.heredi.nowait.application.model.paymentInfo.dto.in;

import lombok.Data;

@Data
public class PaymentInfoRequestDTO {

    private String cardNumber;
    private String expiryDate;
    private String cardType;
    private String cardHolderName;
    private String cvv;
}
