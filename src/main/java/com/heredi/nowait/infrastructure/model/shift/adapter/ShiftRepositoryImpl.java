package com.heredi.nowait.infrastructure.model.shift.adapter;

import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.domain.shift.port.ShiftRepository;
import com.heredi.nowait.infrastructure.model.shift.mapper.ShiftEntityMapper;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import com.heredi.nowait.infrastructure.model.shift.jpa.ShiftJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ShiftRepositoryImpl implements ShiftRepository {

    private final ShiftJPARepository shiftJPARepository;

    @Autowired
    private ShiftEntityMapper shiftEntityMapper;

    public ShiftRepositoryImpl(@Lazy ShiftJPARepository shiftJPARepository){
        this.shiftJPARepository = shiftJPARepository;
    }

    @Override
    public Shift createShift() {
        ShiftEntity shiftEntity = new ShiftEntity();
        shiftEntity.setCreateAt(LocalDateTime.now());
        shiftEntity.setStatus(ShiftEntity.ShiftStatus.CREATING);
        return this.shiftEntityMapper.toShift(this.shiftJPARepository.save(shiftEntity));
    }
}
