package com.heredi.nowait.domain.role.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals, y hashCode automáticamente
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los atributos
public class Authority {

    private Long id;

    private RoleUser role;

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
