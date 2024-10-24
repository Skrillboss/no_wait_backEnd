package com.heredi.nowait.application.model.queue.service.implementations;

import com.heredi.nowait.application.model.queue.service.interfaces.QueueService;
import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import com.heredi.nowait.application.model.shift.mapper.ShiftMapper;
import com.heredi.nowait.domain.queue.model.Queue;
import com.heredi.nowait.domain.queue.port.QueueRepository;
import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.domain.shift.port.ShiftRepository;
import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;

    private final ShiftRepository shiftRepository;

    private final UserRepository userRepository;
    @Autowired
    private final ShiftMapper shiftMapper;

    public QueueServiceImpl(QueueRepository queueRepository, ShiftRepository shiftRepository, UserRepository userRepository, ShiftMapper shiftMapper) {
        this.queueRepository = queueRepository;
        this.shiftRepository = shiftRepository;
        this.userRepository = userRepository;
        this.shiftMapper = shiftMapper;
    }

    @Override
    public ShiftResponseDTO generateShift(String queueId, String userId) {
        Shift obteinedShift = shiftRepository.createShift();
        Queue obteinedQueue = queueRepository.getQueueById(Long.parseLong(queueId));
        Users obteinedUser = userRepository.getUserById(Long.parseLong(userId));

        obteinedQueue.getShifts().add(obteinedShift);
        obteinedUser.getShifts().add(obteinedShift);

        queueRepository.save(obteinedQueue);
        userRepository.updateUser(obteinedUser);

        return shiftMapper.toShiftDTO(obteinedShift);
    }
}
