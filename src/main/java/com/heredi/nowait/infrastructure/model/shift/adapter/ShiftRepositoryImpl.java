package com.heredi.nowait.infrastructure.model.shift.adapter;

import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.domain.shift.port.ShiftRepository;
import com.heredi.nowait.infrastructure.model.queue.entity.QueueEntity;
import com.heredi.nowait.infrastructure.model.queue.jpa.QueueJPARepository;
import com.heredi.nowait.infrastructure.model.shift.mapper.ShiftEntityMapper;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import com.heredi.nowait.infrastructure.model.shift.jpa.ShiftJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Repository
public class ShiftRepositoryImpl implements ShiftRepository {

    private final QueueJPARepository queueJPARepository;

    private final ShiftJPARepository shiftJPARepository;

    @Autowired
    private ShiftEntityMapper shiftEntityMapper;

    public ShiftRepositoryImpl(QueueJPARepository queueJPARepository, @Lazy ShiftJPARepository shiftJPARepository){
        this.queueJPARepository = queueJPARepository;
        this.shiftJPARepository = shiftJPARepository;
    }

    @Override
    public Shift createShift(Long queueId, Shift shift) {

        QueueEntity queueEntity = this.queueJPARepository.findById(queueId)
                .orElseThrow(() -> new NoSuchElementException("Queue not found by Id: " + queueId));

        ShiftEntity shiftEntity = this.shiftEntityMapper.toShiftEntity(shift);

        shiftEntity.setQueue(queueEntity);

        queueEntity.getShifts().add(shiftEntity);

        this.queueJPARepository.save(queueEntity);

        return this.shiftEntityMapper.toShift(this.shiftJPARepository.save(shiftEntity));
    }
}
