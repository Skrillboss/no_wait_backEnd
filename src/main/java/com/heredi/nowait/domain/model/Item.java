package com.heredi.nowait.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private String id;
    private String name;
    private String description;
    private int numberPeopleWaiting;
    private int peoplePerShift;
    private int numberShiftsWaiting;
    private Double rating;
    private String mainImagePath;
    private String secondaryImagePath;
    private Duration currentWaitingDuration;
    private Duration durationPerShifts;
    private ItemStatus status;
    private List<Shift> shifts;

    public enum ItemStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        NO_STOCK,
        UNHANDLED_ERROR
    }
}
