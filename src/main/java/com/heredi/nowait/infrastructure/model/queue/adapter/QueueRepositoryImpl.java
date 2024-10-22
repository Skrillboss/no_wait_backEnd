package com.heredi.nowait.infrastructure.model.queue.adapter;

import com.heredi.nowait.domain.queue.model.Queue;
import com.heredi.nowait.domain.queue.port.QueueRepository;
import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.infrastructure.model.queue.entity.QueueEntity;
import com.heredi.nowait.infrastructure.model.queue.jpa.QueueJPARepository;
import com.heredi.nowait.infrastructure.model.queue.mapper.QueueEntityMapper;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class QueueRepositoryImpl implements QueueRepository {

    private final QueueJPARepository queueJPARepository;

    @Autowired
    private QueueEntityMapper queueEntityMapper;

    public QueueRepositoryImpl(QueueJPARepository queueJPARepository) {
        this.queueJPARepository = queueJPARepository;
    }


    //falta actualizar mas valores
    @Override
    public void save(Queue queue) {
        QueueEntity queueEntity = queueJPARepository.getReferenceById(queue.getId());

        queueEntity.setCurrentWaitingDuration(queue.getCurrentWaitingDuration());
        queueEntity.setShiftDuration(queue.getShiftDuration());
        queueEntity.setStartTime(queue.getStartTime());
        queueEntity.setEndTime(queue.getEndTime());
        queueEntity.setPeoplePerShift(queue.getPeoplePerShift());

        if (queue.getShifts() != null) {
            List<ShiftEntity> shiftEntities = queue.getShifts().stream()
                    .map(shift -> {
                        return new ShiftEntity();
                    })
                    .collect(Collectors.toList());
            queueEntity.setShifts(shiftEntities);
        }

        queueJPARepository.save(queueEntity);
    }


    @Override
    public Queue getQueueById(Long queueId) {
        return null;
    }

    @Override
    public Shift addShift(Long shiftId) {
        return null;
    }
}
