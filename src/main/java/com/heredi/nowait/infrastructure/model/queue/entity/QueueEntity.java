package com.heredi.nowait.infrastructure.model.queue.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Formula;

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
    @Column(name = "id")
    private Long id; // Identificador autogenerado

    @Column(nullable = false)
    private Duration currentWaitingDuration; // tiempo actual de espera para que sea tu turno

    @Column(nullable = false)
    private Duration shiftDuration; // cuando tarda aproximadamente turno a turno

    @Column
    private LocalTime startTimeHour;

    @Column
    private LocalTime endTimeHour;

    @Column(nullable = false)
    private int peoplePerShift;

    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @ToString.Exclude
    private ItemEntity item;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
