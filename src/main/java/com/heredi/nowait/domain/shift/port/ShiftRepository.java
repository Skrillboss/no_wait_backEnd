package com.heredi.nowait.domain.shift.port;

import com.heredi.nowait.domain.shift.model.Shift;

import java.util.List;

public interface ShiftRepository {

    List<Shift> getShiftsByQueueId(Long queueId);
    Shift createShift(Long queueId, Long userId, Shift shift);
}
