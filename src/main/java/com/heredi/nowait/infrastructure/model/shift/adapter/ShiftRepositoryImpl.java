package com.heredi.nowait.infrastructure.model.shift.adapter;

import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.domain.shift.port.ShiftRepository;
import com.heredi.nowait.infrastructure.model.queue.entity.QueueEntity;
import com.heredi.nowait.infrastructure.model.queue.jpa.QueueJPARepository;
import com.heredi.nowait.infrastructure.model.shift.mapper.ShiftEntityMapper;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import com.heredi.nowait.infrastructure.model.shift.jpa.ShiftJPARepository;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Repository
public class ShiftRepositoryImpl implements ShiftRepository {

    private final QueueJPARepository queueJPARepository;

    private final ShiftJPARepository shiftJPARepository;

    private final UserJPARepository userJPARepository;

    @Autowired
    private ShiftEntityMapper shiftEntityMapper;

    public ShiftRepositoryImpl(QueueJPARepository queueJPARepository, @Lazy ShiftJPARepository shiftJPARepository, UserJPARepository userJPARepository){
        this.queueJPARepository = queueJPARepository;
        this.shiftJPARepository = shiftJPARepository;
        this.userJPARepository = userJPARepository;
    }

    @Override
    public Shift createShift(Long queueId, Long userId, Shift shift) {

        QueueEntity queueEntity = this.queueJPARepository.findById(queueId)
                .orElseThrow(() -> new NoSuchElementException("Queue not found by Id: " + queueId));

        UserEntity userEntity = this.userJPARepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found by Id: " + userId));

        ShiftEntity shiftEntity = this.shiftEntityMapper.toShiftEntity(shift);

        shiftEntity.setQueue(queueEntity);
        shiftEntity.setUserEntity(userEntity);

        ShiftEntity savedShiftEntity = this.shiftJPARepository.save(shiftEntity);

        return this.shiftEntityMapper.toShift(savedShiftEntity);
    }
}
