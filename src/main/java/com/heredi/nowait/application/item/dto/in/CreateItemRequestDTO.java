package com.heredi.nowait.application.item.dto.in;

import lombok.Data;

@Data
public class CreateItemRequestDTO {

    private String name;
    private String description;
    private int peoplePerShift;
    private String mainImagePath;
    private String secondaryImagePath;
    private int durationPerShifts; // En minutos
    private String status; // Representa el ItemStatus como String
}
