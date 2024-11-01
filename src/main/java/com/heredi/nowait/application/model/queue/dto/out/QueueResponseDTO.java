package com.heredi.nowait.application.model.queue.dto.out;

import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class QueueResponseDTO {

    private String id;
    private double currentWaitingDuration;
    private double shiftDuration;
    private String startTimeHour;
    private String endTimeHour;
    private int peoplePerShift;
    private int activeShifts;
    private List<ShiftResponseDTO> shiftResponseDTO;
    private String status;
}
