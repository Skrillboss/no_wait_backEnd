package com.heredi.nowait.infrastructure.model.shift.jpa;

import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftJPARepository extends JpaRepository<ShiftEntity, Long> {
    List<ShiftEntity> findByQueueId(Long queueId);
}
