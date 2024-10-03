package com.heredi.nowait.application.item.service.implementations;

import com.heredi.nowait.application.item.dto.in.CreateItemRequestDTO;
import com.heredi.nowait.application.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.item.mapper.ItemMapper;
import com.heredi.nowait.application.item.service.interfaces.ItemService;
import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.port.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ItemResponseDTO createItem(CreateItemRequestDTO createItemRequestDTO) {
        Item createdItem = this.itemRepository.createItem(itemMapper.toItem(createItemRequestDTO));
        return itemMapper.toItemResponseDTO(createdItem);
    }
}
