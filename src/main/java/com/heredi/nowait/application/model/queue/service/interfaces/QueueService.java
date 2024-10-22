package com.heredi.nowait.application.model.queue.service.interfaces;

import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;

public interface QueueService {
    ShiftResponseDTO generateShift(String queueId, String userId);
}
