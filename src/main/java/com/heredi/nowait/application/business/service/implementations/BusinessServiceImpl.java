package com.heredi.nowait.application.business.service.implementations;

import com.heredi.nowait.application.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.business.dto.in.CreateBusinessRequestDTO;
import com.heredi.nowait.application.business.service.interfaces.BusinessService;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Override
    public BusinessResponseDTO createBusiness(CreateBusinessRequestDTO createBusinessDTO) {

        return null;
    }
}
