package com.heredi.nowait.application.model.item.service.implementations;

import com.heredi.nowait.application.model.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.model.item.mapper.ItemMapper;
import com.heredi.nowait.application.model.item.service.interfaces.ItemService;
import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.port.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper){
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public AddItemResponseDTO create(String businessId, ItemRequestDTO itemRequestDTO) {
        Item item = itemMapper.toItem(itemRequestDTO);
        Long longBusinessId = Long.parseLong(businessId);
        item = itemRepository.create(longBusinessId, item);

        return new AddItemResponseDTO(businessId, itemMapper.toItemResponseDTO(item));
    }

    @Transactional
    @Override
    public ItemResponseDTO get(String itemId) {
        Item obteinedItem = itemRepository.getItemById(Long.parseLong(itemId));
        return itemMapper.toItemResponseDTO(obteinedItem);
    }
}
