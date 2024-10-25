package com.heredi.nowait.application.model.shift.service.interfaces;

import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ShiftService {
    ShiftResponseDTO create(String queueId, Long userId);
}
