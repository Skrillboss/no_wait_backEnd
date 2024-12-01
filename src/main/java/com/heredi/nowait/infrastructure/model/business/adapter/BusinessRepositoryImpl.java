package com.heredi.nowait.infrastructure.model.business.adapter;

import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.domain.business.port.BusinessRepository;
import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import com.heredi.nowait.infrastructure.model.business.jpa.BusinessJPARepository;
import com.heredi.nowait.infrastructure.model.business.mapper.BusinessEntityMapper;
import com.heredi.nowait.infrastructure.model.item.mapper.ItemEntityMapper;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BusinessRepositoryImpl implements BusinessRepository {
    private final BusinessJPARepository businessJPARepository;

    private final UserJPARepository userJPARepository;

    @Autowired
    private BusinessEntityMapper businessEntityMapper;
    @Autowired
    private ItemEntityMapper itemEntityMapper;

    BusinessRepositoryImpl(@Lazy BusinessJPARepository businessJPARepository, UserJPARepository userJPARepository) {
        this.businessJPARepository = businessJPARepository;
        this.userJPARepository = userJPARepository;
    }

    @Override
    public Business getBusiness(Long businessId) {
        BusinessEntity businessEntity = this.businessJPARepository.findById(businessId).
                orElseThrow(() -> new AppException(
                        AppErrorCode.BUSINESS_NOT_FOUND_BY_ID,
                        "getBusiness",
                        "businessId: " + businessId,
                        HttpStatus.NOT_FOUND
                ));

        return this.businessEntityMapper.toBusiness(businessEntity);
    }

    @Override
    public Business updateBusiness(Long userId, Business business) {
        BusinessEntity businessEntity = this.businessJPARepository.findById(business.getId())
                .orElseThrow(() -> new AppException(
                        AppErrorCode.BUSINESS_NOT_FOUND_BY_ID,
                        "updateBusiness",
                        "businessId: " + business.getId(),
                        HttpStatus.NOT_FOUND
                ));

        UserEntity userEntity = this.userJPARepository.getReferenceById(userId);

        if(!userEntity.getBusiness().getId().equals(businessEntity.getId())){
            throw new AppException(
                    AppErrorCode.BUSINESS_DOES_NOT_BELONG_TO_USER,
                    "updateBusiness",
                    "userId: " + userId + "BusinessId: " + businessEntity.getId(),
                    HttpStatus.FORBIDDEN);
        }
        businessEntity.setCif(business.getCif());
        businessEntity.setName(business.getName());
        businessEntity.setImageUrl(business.getImageUrl());
        businessEntity.setPhone(business.getPhone());
        businessEntity.setAddress(business.getAddress());
        businessEntity.setEmail(business.getEmail());

        return this.businessEntityMapper.toBusiness(this.businessJPARepository.save(businessEntity));
    }
}
