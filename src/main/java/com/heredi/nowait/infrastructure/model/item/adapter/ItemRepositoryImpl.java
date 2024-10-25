package com.heredi.nowait.infrastructure.model.item.adapter;

import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.port.ItemRepository;
import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import com.heredi.nowait.infrastructure.model.business.jpa.BusinessJPARepository;
import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import com.heredi.nowait.infrastructure.model.item.jpa.ItemJPARepository;
import com.heredi.nowait.infrastructure.model.item.mapper.ItemEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final BusinessJPARepository businessJPARepository;

    private final ItemJPARepository itemJPARepository;

    @Autowired
    private ItemEntityMapper itemEntityMapper;

    ItemRepositoryImpl(BusinessJPARepository businessJPARepository, @Lazy ItemJPARepository itemJPARepository) {
        this.businessJPARepository = businessJPARepository;
        this.itemJPARepository = itemJPARepository;
    }


    @Override
    public Item create(Long businessId, Item item) {
        BusinessEntity businessEntity = this.businessJPARepository.findById(businessId)
                .orElseThrow(() -> new UsernameNotFoundException("Business not found by Id: " + businessId));

        ItemEntity itemEntity = itemEntityMapper.toItemEntity(item);

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
