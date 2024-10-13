package com.heredi.nowait.application.item.dto.in;

import lombok.Data;

@Data
public class ItemRequestDTO {

    private String name;
    private String description;
    private int peoplePerShift;
    private String mainImagePath;
    private String secondaryImagePath;
    private int durationPerShifts;
    private String status;
}
