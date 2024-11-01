package com.heredi.nowait.application.model.queue.service.interfaces;

import com.heredi.nowait.application.model.queue.dto.out.QueueResponseDTO;
import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface QueueService {
    QueueResponseDTO get(String queueId);
}
