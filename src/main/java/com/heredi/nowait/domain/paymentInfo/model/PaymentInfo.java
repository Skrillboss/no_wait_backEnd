package com.heredi.nowait.domain.paymentInfo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data // Genera getters, setters, toString, equals, y hashCode automáticamente
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los atributos
public class PaymentInfo {

    private Long id;

    private String cardNumber;

    private String cardHolderName;

    private LocalDate expiryDate;

    private String cardType;

    private String cvv;
}
