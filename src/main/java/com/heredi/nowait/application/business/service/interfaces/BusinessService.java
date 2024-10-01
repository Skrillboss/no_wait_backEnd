package com.heredi.nowait.application.business.service.interfaces;

import com.heredi.nowait.application.business.dto.in.AddItemRequestDTO;
import com.heredi.nowait.application.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.business.dto.in.CreateBusinessRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface BusinessService {
    AddItemResponseDTO addItem(AddItemRequestDTO addItemRequestDTO);
}
