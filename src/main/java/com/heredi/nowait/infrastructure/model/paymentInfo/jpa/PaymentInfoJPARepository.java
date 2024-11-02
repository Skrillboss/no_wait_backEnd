package com.heredi.nowait.infrastructure.model.paymentInfo.jpa;

import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoJPARepository extends JpaRepository<PaymentInfoEntity, Long> {
}
