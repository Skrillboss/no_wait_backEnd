package com.heredi.nowait.application.business.mapper;

import com.heredi.nowait.application.business.dto.BusinessDTO;
import com.heredi.nowait.domain.model.Business;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {

    public BusinessDTO toBusinessDTO(Business business) {
        if (business == null) {
            return null;
        }

        BusinessDTO dto = new BusinessDTO();
        dto.setId(business.getId().toString());
        dto.setName(business.getName());
        dto.setAddress(business.getAddress());
        dto.setPhone(business.getPhone());

        return dto;
    }
}
