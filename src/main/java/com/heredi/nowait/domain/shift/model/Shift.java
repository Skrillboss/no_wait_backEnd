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
    private LocalDateTime createAt; // Fecha de creación del turno
    private LocalDateTime notifyTime; // Tiempo para notificar al usuario
    private Duration currentWaitingDuration; // Tiempo de duración actual para que sea tu turno
    private LocalDateTime expirationTime; // Tiempo de expiración del turno
    private Duration estimatedArrivalTime; // Tiempo estimado de llegada al negocio para reclamar tu turno
    private ShiftStatus status; // Estado del turno
    private int shiftNumber; // Número de turno

    public enum ShiftStatus {
        ACTIVE,
        CREATING,
        INACTIVE,
        EXPIRED,
        POSTPONED,
        ERROR
    }
}
