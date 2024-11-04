package com.heredi.nowait.infrastructure.model.paymentInfo.jpa;

import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentInfoJPARepository extends JpaRepository<PaymentInfoEntity, Long> {
    List<PaymentInfoEntity> findByUserEntityId(Long userId);
}
