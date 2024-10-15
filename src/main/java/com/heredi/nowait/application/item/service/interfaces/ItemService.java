package com.heredi.nowait.application.item.service.interfaces;

import com.heredi.nowait.application.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.domain.item.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    ItemResponseDTO getItem(String itemId);
}
