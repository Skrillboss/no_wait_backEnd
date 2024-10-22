package com.heredi.nowait.infrastructure.model.shift.jpa;

import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftJPARepository extends JpaRepository<ShiftEntity, Long> {
}
