package com.heredi.nowait.domain.shift.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift {

    private String id; // Identificador del turno
    private String itemName; // Nombre del producto o servicio que vas a reclamar por el turno
    private String businessName; // Nombre del negocio
    private LocalDateTime shiftTime; // Cuánto tarda aproximadamente turno a turno
    private int peopleInShift; // Cantidad de personas esperando actualmente su turno
    private LocalDateTime createAt; // Fecha de creación del turno
    private LocalDateTime notifyTime; // Tiempo para notificar al usuario
    private Duration currentWaitingDuration; // Tiempo de duración actual para que sea tu turno
    private LocalDateTime expirationTime; // Tiempo de expiración del turno
    private Duration estimatedArrivalTime; // Tiempo estimado de llegada al negocio para reclamar tu turno
    private ShiftStatus status; // Estado del turno
    private int shiftNumber; // Número de turno

    public enum ShiftStatus {
        ACTIVE,
        INACTIVE,
        EXPIRED,
        POSTPONED,
        ERROR
    }
}
