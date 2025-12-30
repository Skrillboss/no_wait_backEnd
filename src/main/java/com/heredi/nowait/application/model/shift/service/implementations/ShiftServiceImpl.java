package com.heredi.nowait.application.model.shift.service.implementations;

import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import com.heredi.nowait.application.model.shift.mapper.ShiftMapper;
import com.heredi.nowait.application.model.shift.service.interfaces.ShiftService;
import com.heredi.nowait.domain.queue.model.Queue;
import com.heredi.nowait.domain.queue.port.QueueRepository;
import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.domain.shift.port.ShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;

    private final QueueRepository queueRepository;

    @Autowired
    private final ShiftMapper shiftMapper;

    public ShiftServiceImpl(ShiftRepository shiftRepository, QueueRepository queueRepository, ShiftMapper shiftMapper) {
        this.shiftRepository = shiftRepository;
        this.queueRepository = queueRepository;
        this.shiftMapper = shiftMapper;
    }


    @Transactional
    @Override
    public ShiftResponseDTO create(Long queueId, Long userId) {

        Shift shift = new Shift();

        //TODO: estos valores no se pueden quedar asi
        //se debe crear la logica compleja de la geolocalización del usuario para
        //poder editar con mayor precisión el turno.
        shift.setCurrentWaitingDuration(Duration.ofMinutes(5));
        Queue obteinedQueue = queueRepository.getQueueById(queueId);
        List<Shift> obteinedShifts = shiftRepository.getShiftsByQueueId(queueId);
        shift.setShiftNumber(obteinedShifts.size() + 1);

        return this.shiftMapper.toShiftDTO(this.shiftRepository.createShift(queueId, userId, shift));
    }
}
