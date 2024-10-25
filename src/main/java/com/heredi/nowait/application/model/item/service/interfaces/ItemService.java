package com.heredi.nowait.application.model.item.service.interfaces;

import com.heredi.nowait.application.model.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    AddItemResponseDTO create(String businessId, ItemRequestDTO itemRequestDTO);
    ItemResponseDTO get(String itemId);
}
