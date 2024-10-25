package com.heredi.nowait.domain.shift.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift {

    private String id;
    private LocalDateTime createAt;
    private LocalDateTime notifyTime;
    private Duration currentWaitingDuration;
    private LocalDateTime expirationTime;
    private Duration estimatedArrivalTime;
    private String itemName;
    private String itemImageUrl;
    private ShiftStatus status;
    private int shiftNumber;

    public enum ShiftStatus {
        ACTIVE,
        CREATING,
        INACTIVE,
        EXPIRED,
        POSTPONED,
        ERROR
    }
}
