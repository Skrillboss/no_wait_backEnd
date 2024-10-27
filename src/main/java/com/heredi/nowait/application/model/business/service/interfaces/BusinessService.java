package com.heredi.nowait.application.model.business.service.interfaces;

import com.heredi.nowait.application.model.business.dto.out.BusinessResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface BusinessService {
    BusinessResponseDTO getBusiness(String businessId);
}
