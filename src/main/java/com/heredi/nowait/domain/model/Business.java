package com.heredi.nowait.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera automáticamente getters, setters, toString, equals, y hashCode
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los atributos
public class Business {

    private Long id;

    private String cif;

    private String name;

    private String imageUrl;

    private String phone;

    private String address;

    private String email;

    private String createdAt;
}
