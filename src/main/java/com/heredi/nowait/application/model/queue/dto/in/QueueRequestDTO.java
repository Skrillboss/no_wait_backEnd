package com.heredi.nowait.application.model.queue.dto.in;

import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueRequestDTO {

    private double currentWaitingDuration;
    private double shiftDuration;
    private String startTimeHour;
    private String endTimeHour;
    private int peoplePerShift;
    private String status;
}
