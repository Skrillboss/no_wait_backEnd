package com.heredi.nowait.infrastructure.model.business.mapper;

import com.heredi.nowait.domain.business.model.Business;
import com.heredi.nowait.infrastructure.model.business.entity.BusinessEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BusinessEntityMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "cif", target = "cif"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "address", target = "address"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "items", target = "items"),
    })
    Business toBusiness(BusinessEntity businessEntity);

    @InheritInverseConfiguration
    BusinessEntity toBusinessEntity(Business business);
}
