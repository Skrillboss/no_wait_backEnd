package com.heredi.nowait.domain.item.port;

import com.heredi.nowait.domain.item.model.Item;

public interface ItemRepository {
    Item getItemById(Long itemId);
    // Crear un nuevo Item
    Item createItem(Item item);
}
