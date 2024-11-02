package com.heredi.nowait.infrastructure.model.authority.authority;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authority")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleUser role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    public enum RoleUser {
        ADMIN,
        USER
    }

    public enum UserStatus {
        ACTIVE,
        EMAIL_UNVERIFIED,
        SUSPENDED,
        UNHANDLED_ERROR
    }
}
