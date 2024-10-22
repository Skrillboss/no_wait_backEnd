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
    private LocalDateTime createAt; // fecha de creación

    private LocalDateTime notifyTime; // tiempo para notificar al usuario

    private Duration currentWaitingDuration; // tiempo actual de espera para que sea tu turno

    private LocalDateTime expirationTime; // tiempo de expiración del turno

    private Duration estimatedArrivalTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShiftStatus status; // estado del turno

    private int shiftNumber;

    public enum ShiftStatus {
        ACTIVE, CREATING, INACTIVE, EXPIRED, POSTPONED, ERROR
    }
}
