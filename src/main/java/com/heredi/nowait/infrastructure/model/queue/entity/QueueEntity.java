package com.heredi.nowait.infrastructure.model.queue.entity;

import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "queue")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador autogenerado

    @Column(nullable = false)
    private Duration currentWaitingDuration; // tiempo actual de espera para que sea tu turno

    @Column(nullable = false)
    private LocalTime shiftDuration; // cuando tarda aproximadamente turno a turno

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Column(nullable = false)
    private int peoplePerShift;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shifts_id")
    private List<ShiftEntity> shifts;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QueueStatus status; // estado del turno

    public enum QueueStatus {
        ACTIVE,
        EMPTY,
        FULL,
        SUSPENDED,
        INACTIVE,
        ERROR
    }
}
