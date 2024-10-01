package com.heredi.nowait.application.business.service.implementations;

import com.heredi.nowait.application.business.dto.in.AddItemRequestDTO;
import com.heredi.nowait.application.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.business.dto.in.CreateBusinessRequestDTO;
import com.heredi.nowait.application.business.mapper.BusinessMapper;
import com.heredi.nowait.application.business.service.interfaces.BusinessService;
import com.heredi.nowait.application.item.mapper.ItemMapper;
import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.domain.business.port.BusinessRepository;
import com.heredi.nowait.domain.item.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;
    @Autowired
    private final BusinessMapper businessMapper;
    @Autowired
    private final ItemMapper itemMapper;

    public BusinessServiceImpl(BusinessRepository businessRepository, BusinessMapper businessMapper, ItemMapper itemMapper) {
        this.businessRepository = businessRepository;
        this.businessMapper = businessMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public AddItemResponseDTO addItem(AddItemRequestDTO addItemRequestDTO) {
        Item item = itemMapper.toItem(addItemRequestDTO.getCreateItemRequestDTO());
        Long businessId = Long.parseLong(addItemRequestDTO.getBusinessId());
        Business business = businessRepository.addItem(businessId, item);

        return new AddItemResponseDTO(business.getId(), itemMapper.toItemResponseDTO(item));
    }
}
