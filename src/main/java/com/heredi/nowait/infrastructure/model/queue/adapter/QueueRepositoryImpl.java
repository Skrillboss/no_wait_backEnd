package com.heredi.nowait.infrastructure.model.queue.adapter;

import com.heredi.nowait.domain.queue.model.Queue;
import com.heredi.nowait.domain.queue.port.QueueRepository;
import com.heredi.nowait.infrastructure.model.queue.entity.QueueEntity;
import com.heredi.nowait.infrastructure.model.queue.jpa.QueueJPARepository;
import com.heredi.nowait.infrastructure.model.queue.mapper.QueueEntityMapper;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import com.heredi.nowait.infrastructure.model.shift.mapper.ShiftEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJPARepository queueJPARepository;

    @Autowired
    private QueueEntityMapper queueEntityMapper;

    @Autowired
    private ShiftEntityMapper shiftEntityMapper;

    public QueueRepositoryImpl(QueueJPARepository queueJPARepository) {
        this.queueJPARepository = queueJPARepository;
    }


    //falta actualizar mas valores
    @Override
    public void save(Queue queue) {
        QueueEntity queueEntity = queueJPARepository.getReferenceById(queue.getId());

        queueEntity.setCurrentWaitingDuration(queue.getCurrentWaitingDuration());
        queueEntity.setShiftDuration(queue.getShiftDuration());
        queueEntity.setStartTimeHour(queue.getStartTimeHour());
        queueEntity.setEndTimeHour(queue.getEndTimeHour());
        queueEntity.setPeoplePerShift(queue.getPeoplePerShift());

        if (queue.getShifts() != null) {
            List<ShiftEntity> shiftEntities = queue.getShifts().stream()
                    .map(shift -> shiftEntityMapper.toShiftEntity(shift))
                    .collect(Collectors.toList());
            queueEntity.setShifts(shiftEntities);
        }

        queueJPARepository.save(queueEntity);
    }


    @Override
    public Queue getQueueById(Long queueId) {
        return this.queueEntityMapper.toQueue(this.queueJPARepository.findById(queueId)
                .orElseThrow(() -> new NoSuchElementException("User not found")));
    }
}
