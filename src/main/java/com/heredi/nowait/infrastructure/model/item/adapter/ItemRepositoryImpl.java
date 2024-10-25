package com.heredi.nowait.infrastructure.model.item.adapter;

import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.port.ItemRepository;
import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import com.heredi.nowait.infrastructure.model.business.jpa.BusinessJPARepository;
import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import com.heredi.nowait.infrastructure.model.item.jpa.ItemJPARepository;
import com.heredi.nowait.infrastructure.model.item.mapper.ItemEntityMapper;
import com.heredi.nowait.infrastructure.model.queue.jpa.QueueJPARepository;
import com.heredi.nowait.infrastructure.model.queue.mapper.QueueEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final BusinessJPARepository businessJPARepository;

    private final ItemJPARepository itemJPARepository;

    @Autowired
    private ItemEntityMapper itemEntityMapper;

    @Autowired
    private QueueEntityMapper queueEntityMapper;

    ItemRepositoryImpl(BusinessJPARepository businessJPARepository, @Lazy ItemJPARepository itemJPARepository, QueueJPARepository queueJPARepository) {
        this.businessJPARepository = businessJPARepository;
        this.itemJPARepository = itemJPARepository;
    }


    @Transactional
    @Override
    public Item create(Long businessId, Item item) {
        BusinessEntity businessEntity = this.businessJPARepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException("Business not found by Id: " + businessId));

        ItemEntity itemEntity = itemEntityMapper.toItemEntity(item);

        itemEntity.getQueue().setItem(itemEntity);

        businessEntity.getItems().add(itemEntity);

        itemEntity = this.itemJPARepository.save(itemEntity);

        this.businessJPARepository.save(businessEntity);

        return this.itemEntityMapper.toItem(itemEntity);
    }

    @Override
    public Item getItemById(Long itemId){
        return this.itemEntityMapper.toItem(this.itemJPARepository.getReferenceById(itemId));
    }
}
