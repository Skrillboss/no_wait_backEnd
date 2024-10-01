package com.heredi.nowait.infrastructure.model.user.entity;

import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import com.heredi.nowait.infrastructure.model.role.entity.RoleEntity;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = false)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private List<RoleEntity> roleEntityList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_payment_info_id")
    private List<PaymentInfoEntity> paymentInfoEntityList;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_business_id")
    private BusinessEntity business;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_shifts_id")
    private List<ShiftEntity> shifts;
}
