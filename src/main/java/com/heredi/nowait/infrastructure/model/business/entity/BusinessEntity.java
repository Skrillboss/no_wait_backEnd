package com.heredi.nowait.infrastructure.model.business.entity;

import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "business")
@Data // Genera automáticamente getters, setters, toString, equals, y hashCode
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los atributos
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cif;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String createdAt;

    @Lob
    @OneToMany(fetch = FetchType.LAZY)
    private List<ItemEntity> items;
}
