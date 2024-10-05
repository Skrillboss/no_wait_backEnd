package com.heredi.nowait.infrastructure.model.item.adapter;

import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.port.ItemRepository;
import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import com.heredi.nowait.infrastructure.model.item.jpa.ItemJPARepository;
import com.heredi.nowait.infrastructure.model.item.mapper.ItemEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final ItemJPARepository itemJPARepository;

    @Autowired
    private ItemEntityMapper itemEntityMapper;

    ItemRepositoryImpl(@Lazy ItemJPARepository itemJPARepository) {
        this.itemJPARepository = itemJPARepository;
    }

    @Override
    public Item createItem(Item item) {
        ItemEntity itemEntity = this.itemEntityMapper.toItemEntity(item);
        return this.itemEntityMapper.toItem(this.itemJPARepository.save(itemEntity));
    }

    @Override
    public Item getItemById(Long itemId){
        return this.itemEntityMapper.toItem(this.itemJPARepository.getReferenceById(itemId));
    }
}
