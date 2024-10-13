package com.heredi.nowait.infrastructure.model.user.entity;

import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import com.heredi.nowait.infrastructure.model.role.entity.RoleEntity;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data // Genera getters, setters, toString, equals, y hashCode automáticamente
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los atributos
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un valor único para el ID
    @Column(name = "user_id")
    private Long id;

    private String refreshToken;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    private RoleEntity roleEntity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_info_id")
    @ToString.Exclude
    private List<PaymentInfoEntity> paymentInfoEntityList;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @ToString.Exclude
    private BusinessEntity business;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shifts_id")
    @ToString.Exclude
    private List<ShiftEntity> shifts;
}
