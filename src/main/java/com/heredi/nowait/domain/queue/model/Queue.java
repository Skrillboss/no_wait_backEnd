package com.heredi.nowait.domain.queue.model;

import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Queue {

    private Long id;
    private Duration currentWaitingDuration; // tiempo actual de espera para que sea tu turno
    private Duration shiftDuration; // cuando tarda aproximadamente turno a turno
    private LocalTime startTimeHour;
    private LocalTime endTimeHour;
    private int peoplePerShift;
    private List<Shift> shifts;
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
