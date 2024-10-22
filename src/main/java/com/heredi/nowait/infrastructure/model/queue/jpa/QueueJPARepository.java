package com.heredi.nowait.infrastructure.model.queue.jpa;

import com.heredi.nowait.infrastructure.model.queue.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueJPARepository extends JpaRepository<QueueEntity, Long> {
}
