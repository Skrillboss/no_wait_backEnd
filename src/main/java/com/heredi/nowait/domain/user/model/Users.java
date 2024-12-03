package com.heredi.nowait.domain.user.model;

import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.domain.paymentInfo.model.PaymentInfo;
import com.heredi.nowait.domain.role.model.Authority;
import com.heredi.nowait.domain.shift.model.Shift;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data // Genera getters, setters, toString, equals, y hashCode automáticamente
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los atributos
public class Users {

    private Long id;

    private String refreshUUID;

    private String name;

    private String nickName;

    private String email;

    private String password;

    private String phoneNumber;

    private Authority authority;

    private List<PaymentInfo> paymentInfoList;

    private Business business;

    private List<Shift> shifts;
}
