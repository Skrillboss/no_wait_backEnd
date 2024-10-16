package com.heredi.nowait.infrastructure.model.shift.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift")
@Data // Genera getters, setters, toString, equals, y hashCode automáticamente
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los atributos
public class ShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador autogenerado

    @Column(nullable = false)
    private String itemName; //TODO: este nombre lo debe heredar de su padre ItemEntity

    @Column(nullable = false)
    private String businessName; //TODO: este nombre lo debe heredar de su abuelo BusinessEntity

    @Column(nullable = false)
    private LocalDateTime shiftTime; // cuando tarda aproximadamente turno a turno

    @Column(nullable = false)
    private int peopleInShift; // cantidad de personas esperando su turno

    @Column(nullable = false)
    private LocalDateTime createAt; // fecha de creación

    @Column(nullable = false)
    private LocalDateTime notifyTime; // tiempo para notificar al usuario

    @Column(nullable = false)
    private Duration currentWaitingDuration; // tiempo actual de espera para que sea tu turno

    @Column(nullable = false)
    private LocalDateTime expirationTime; // tiempo de expiración del turno

    @Column(nullable = false)
    private Duration estimatedArrivalTime; // TODO: este valor lo debe tener cada uno de los usuario de manera infividual, no el propio turno.

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShiftStatus status; // estado del turno

    @Column(nullable = false)
    private int shiftNumber; // TODO: este valor lo debe tener cada uno de los usuarios de manera individual, no el propio turno.

    public enum ShiftStatus {
        ACTIVE, INACTIVE, EXPIRED, POSTPONED, ERROR
    }
}
