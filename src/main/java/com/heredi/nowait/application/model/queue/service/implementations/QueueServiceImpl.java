package com.heredi.nowait.application.model.queue.service.implementations;

import com.heredi.nowait.application.model.queue.dto.out.QueueResponseDTO;
import com.heredi.nowait.application.model.queue.mapper.QueueMapper;
import com.heredi.nowait.application.model.queue.service.interfaces.QueueService;
import com.heredi.nowait.domain.queue.model.Queue;
import com.heredi.nowait.domain.queue.port.QueueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;

    private final QueueMapper queueMapper;

    @Deprecated
    public QueueServiceImpl(QueueRepository queueRepository, QueueMapper queueMapper) {
        this.queueRepository = queueRepository;
        this.queueMapper = queueMapper;
    }

    @Transactional
    @Override
    public QueueResponseDTO get(String queueId) {
        Queue obteinedQueue = queueRepository.getQueueById(Long.parseLong(queueId));
        return queueMapper.toQueueResponseDTO(obteinedQueue);
    }
}
