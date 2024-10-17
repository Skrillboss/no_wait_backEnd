package com.heredi.nowait.application.model.shift.dto.in;

import lombok.Data;

@Data
public class ShiftRequestDTO {

    private String itemName; // Nombre del producto o servicio
    private String businessName; // Nombre del negocio
    private String shiftTime; // Tiempo aproximado del turno
    private int peopleInShift; // Número de personas en la cola del turno
    private String createAt; // Fecha y hora de creación del turno
    private String notifyTime; // Tiempo para notificar al usuario
    private double currentWaitingDuration; // Duración actual de la espera
    private String expirationTime; // Tiempo de expiración del turno
    private double estimatedArrivalTime; // Tiempo estimado de llegada
    private String status; // Estado del turno (enum ShiftStatus)
    private int shiftNumber; // Número de turno
}
