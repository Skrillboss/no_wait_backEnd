package com.heredi.nowait.application.business.mapper;

import com.heredi.nowait.application.business.dto.BusinessDTO;
import com.heredi.nowait.application.item.mapper.ItemMapper;
import com.heredi.nowait.domain.model.Business;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {

    private final ItemMapper itemMapper;

    public BusinessMapper(ItemMapper itemMapper){
        this.itemMapper = itemMapper;
    }

    public BusinessDTO toBusinessDTO(Business business) {
        if (business == null) {
            return null;
        }

        BusinessDTO dto = new BusinessDTO();
        dto.setId(business.getId().toString());
        dto.setName(business.getName());
        dto.setAddress(business.getAddress());
        dto.setPhone(business.getPhone());
        dto.setItems(itemMapper.toItemsDTO(business.getItems()));

        return dto;
    }

    public Business toBusiness(BusinessDTO businessDTO) {
        if (businessDTO == null) {
            return null;
        }

        Business business = new Business();
        business.setId(Long.valueOf(businessDTO.getId()));
        business.setCif(businessDTO.getCif());
        business.setName(businessDTO.getName());
        business.setImageUrl(businessDTO.getImageUrl());
        business.setPhone(businessDTO.getPhone());
        business.setAddress(businessDTO.getAddress());
        business.setEmail(businessDTO.getEmail());
        business.setCreatedAt(businessDTO.getCreatedAt());
        business.setItems(itemMapper.toItems(businessDTO.getItems()));

        return business;
    }
}
