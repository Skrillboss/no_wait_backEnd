package com.heredi.nowait.infrastructure.model.item.jpa;
import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJPARepository extends JpaRepository<ItemEntity, Long> {
}
