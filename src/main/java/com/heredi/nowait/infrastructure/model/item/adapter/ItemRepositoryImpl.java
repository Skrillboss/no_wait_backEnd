package com.heredi.nowait.infrastructure.model.item.adapter;

import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.port.ItemRepository;
import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import com.heredi.nowait.infrastructure.model.business.jpa.BusinessJPARepository;
import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import com.heredi.nowait.infrastructure.model.item.jpa.ItemJPARepository;
import com.heredi.nowait.infrastructure.model.item.mapper.ItemEntityMapper;
import com.heredi.nowait.infrastructure.model.queue.entity.QueueEntity;
import com.heredi.nowait.infrastructure.model.queue.jpa.QueueJPARepository;
import com.heredi.nowait.infrastructure.model.queue.mapper.QueueEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    private final BusinessJPARepository businessJPARepository;

    private final ItemJPARepository itemJPARepository;

    private final QueueJPARepository queueJPARepository;

    @Autowired
    private ItemEntityMapper itemEntityMapper;

    @Autowired
    private QueueEntityMapper queueEntityMapper;

    ItemRepositoryImpl(BusinessJPARepository businessJPARepository, ItemJPARepository itemJPARepository, QueueJPARepository queueJPARepository) {
        this.businessJPARepository = businessJPARepository;
        this.itemJPARepository = itemJPARepository;
        this.queueJPARepository = queueJPARepository;
    }


    @Transactional
    @Override
    public Item create(Long businessId, Item item) {
        BusinessEntity businessEntity = this.businessJPARepository.findById(businessId)
                .orElseThrow(() -> new NoSuchElementException("Business not found by Id: " + businessId));

        ItemEntity itemEntity = itemEntityMapper.toItemEntity(item);

        QueueEntity queueEntity = queueEntityMapper.toQueueEntity(item.getQueue());

        queueEntity.setItem(itemEntity);

        itemEntity.setBusiness(businessEntity);
        itemEntity.setQueue(queueEntity);

        return this.itemEntityMapper.toItem(this.itemJPARepository.save(itemEntity));
    }

    @Override
    public Item getItemById(Long itemId){
        ItemEntity itemEntity = this.itemJPARepository.findById(itemId)
                .orElseThrow(() -> new AppException(
                        AppErrorCode.ITEM_NOT_FOUND_BY_ID,
                        "getItemById",
                        "itemId: " + itemId,
                        HttpStatus.NOT_FOUND));

        return this.itemEntityMapper.toItem(itemEntity);
    }
}
