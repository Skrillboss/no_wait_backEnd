package com.heredi.nowait.application.item.dto.out;

import com.heredi.nowait.application.shift.dto.in.ShiftRequestDTO;
import com.heredi.nowait.application.shift.dto.out.ShiftResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class ItemResponseDTO {

    private String id;
    private String name;
    private String description;
    private int numberPeopleWaiting;
    private int peoplePerShift;
    private int numberShiftsWaiting;
    private Double rating;
    private String mainImagePath;
    private String secondaryImagePath;
    private Double currentWaitingDuration; // En minutos
    private Double durationPerShifts; // En minutos
    private String status; // Representa el ItemStatus como String
    private List<ShiftResponseDTO> shifts;
}
