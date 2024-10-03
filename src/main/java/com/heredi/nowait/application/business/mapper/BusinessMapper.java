package com.heredi.nowait.application.business.mapper;

import com.heredi.nowait.application.business.dto.in.BusinessRequestDTO;
import com.heredi.nowait.application.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.item.mapper.ItemMapper;
import com.heredi.nowait.domain.business.model.Business;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BusinessMapper {

    private final ItemMapper itemMapper;

    public BusinessMapper(ItemMapper itemMapper){
        this.itemMapper = itemMapper;
    }

    public BusinessResponseDTO toBusinessDTO(Business business) {
        if (business == null) {
            return null;
        }

        BusinessResponseDTO dto = new BusinessResponseDTO();
        dto.setId(business.getId());
        dto.setCif(business.getCif());
        dto.setName(business.getName());
        dto.setImageUrl(business.getImageUrl());
        dto.setPhone(business.getPhone());
        dto.setAddress(business.getAddress());
        dto.setEmail(business.getEmail());
        dto.setCreatedAt(business.getCreatedAt());
        dto.setItems(itemMapper.toItemsDTO(business.getItems()));

        return dto;
    }

    public Business toBusiness(BusinessRequestDTO businessDTO) {
        if (businessDTO == null) {
            return null;
        }

        Business business = new Business();
        business.setCif(businessDTO.getCif());
        business.setName(businessDTO.getName());
        business.setImageUrl(businessDTO.getImageUrl());
        business.setPhone(businessDTO.getPhone());
        business.setAddress(businessDTO.getAddress());
        business.setEmail(businessDTO.getEmail());
        business.setCreatedAt(LocalDate.now().toString());
        business.setItems(itemMapper.toItems(businessDTO.getItems()));

        return business;
    }
}
