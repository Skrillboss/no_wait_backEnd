package com.heredi.nowait.domain.item.port;

import com.heredi.nowait.domain.item.model.Item;

public interface ItemRepository {
    Item create(Long businessId, Item item);
    Item getItemById(Long itemId);
}
