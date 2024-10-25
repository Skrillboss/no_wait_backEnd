package com.heredi.nowait.domain.shift.port;

import com.heredi.nowait.domain.shift.model.Shift;

public interface ShiftRepository {
    Shift createShift(Long queueId, Long userId, Shift shift);
}
