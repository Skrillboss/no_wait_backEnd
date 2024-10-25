package com.heredi.nowait.application.model.queue.service.implementations;

import com.heredi.nowait.application.auth.AuthService;
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

    private final AuthService authService;


    @Deprecated
    public QueueServiceImpl(QueueRepository queueRepository, ShiftRepository shiftRepository, UserRepository userRepository, ShiftMapper shiftMapper, AuthService authService) {
        this.queueRepository = queueRepository;
        this.shiftRepository = shiftRepository;
        this.userRepository = userRepository;
        this.shiftMapper = shiftMapper;
        this.authService = authService;
    }

    @Override
    public ShiftResponseDTO generateShift(String itemId, String queueId, String authorizationHeader) {
        return null;
    }
}
