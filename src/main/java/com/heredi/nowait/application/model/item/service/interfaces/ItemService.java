package com.heredi.nowait.application.model.item.service.interfaces;

import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    ItemResponseDTO getItem(String itemId);
}
