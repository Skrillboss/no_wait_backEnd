package com.heredi.nowait.domain.business.port;

import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.domain.item.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface BusinessRepository {
    Business getBusiness(Long businessId);
    Business updateBusiness(Long userId, Business business);
}
