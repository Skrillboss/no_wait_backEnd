package com.heredi.nowait.infrastructure.model.business.adapter;

import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.domain.business.port.BusinessRepository;
import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import com.heredi.nowait.infrastructure.model.business.jpa.BusinessJPARepository;
import com.heredi.nowait.infrastructure.model.business.mapper.BusinessEntityMapper;
import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import com.heredi.nowait.infrastructure.model.item.jpa.ItemJPARepository;
import com.heredi.nowait.infrastructure.model.item.mapper.ItemEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BusinessRepositoryImpl implements BusinessRepository {
    private final BusinessJPARepository businessJPARepository;

    private final ItemJPARepository itemJPARepository;

    @Autowired
    private BusinessEntityMapper businessEntityMapper;
    @Autowired
    private ItemEntityMapper itemEntityMapper;

    BusinessRepositoryImpl(@Lazy BusinessJPARepository businessJPARepository, @Lazy ItemJPARepository itemJPARepository) {
        this.businessJPARepository = businessJPARepository;
        this.itemJPARepository = itemJPARepository;
    }

    @Override
    public Business getBusiness(Long businessId) {
        BusinessEntity businessEntity = this.businessJPARepository.findById(businessId).
                orElseThrow(() -> new NoSuchElementException("Business not found"));

        return this.businessEntityMapper.toBusiness(businessEntity);
    }

    @Override
    public Business addItem(Long businessId, Item item) {
        BusinessEntity businessEntity = this.businessJPARepository.findById(businessId).
                orElseThrow(() -> new NoSuchElementException("Business not found"));

        ItemEntity itemEntity = itemJPARepository.save(itemEntityMapper.toItemEntity(item));

        businessEntity.getItems().add(itemEntity);

        return businessEntityMapper.toBusiness(this.businessJPARepository.save(businessEntity));
    }
}
