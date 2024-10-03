package com.heredi.nowait.infrastructure.model.business.jpa;

import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessJPARepository extends JpaRepository<BusinessEntity, Long> {
}
