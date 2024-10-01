package com.heredi.nowait.application.item.service.interfaces;

import com.heredi.nowait.application.item.dto.in.CreateItemRequestDTO;
import com.heredi.nowait.application.item.dto.out.ItemResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    ItemResponseDTO createItem(CreateItemRequestDTO createItemRequestDTO);
}
