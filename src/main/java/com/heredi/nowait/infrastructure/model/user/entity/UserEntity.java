package com.heredi.nowait.infrastructure.model.user.entity;

import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import com.heredi.nowait.infrastructure.model.role.authority.AuthorityEntity;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera un valor único para el ID
    @Column(name = "id")
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
    @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false)
    private AuthorityEntity authorityEntity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<PaymentInfoEntity> paymentInfoEntityList;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    @ToString.Exclude
    private BusinessEntity business;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<ShiftEntity> shifts;
}