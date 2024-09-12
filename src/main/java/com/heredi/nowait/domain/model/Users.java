package com.heredi.nowait.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data // Genera getters, setters, toString, equals, y hashCode automáticamente
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los atributos
public class Users {

    private Long id;

    private String name;

    private String nickName;

    private String email;

    private String password;

    private String phoneNumber;

    private List<PaymentInfo> paymentInfos;

    private Business business;

    private List<Shift> shifts;
}
