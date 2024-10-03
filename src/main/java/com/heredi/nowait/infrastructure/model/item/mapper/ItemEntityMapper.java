package com.heredi.nowait.infrastructure.model.item.mapper;

import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.infrastructure.model.item.entity.ItemEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ItemEntityMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "numberPeopleWaiting", target = "numberPeopleWaiting"),
            @Mapping(source = "peoplePerShift", target = "peoplePerShift"),
            @Mapping(source = "numberShiftsWaiting", target = "numberShiftsWaiting"),
            @Mapping(source = "rating", target = "rating"),
            @Mapping(source = "mainImagePath", target = "mainImagePath"),
            @Mapping(source = "secondaryImagePath", target = "secondaryImagePath"),
            @Mapping(source = "currentWaitingDuration", target = "currentWaitingDuration"),
            @Mapping(source = "durationPerShifts", target = "durationPerShifts"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "shifts", target = "shifts"),
    })
    Item toItem(ItemEntity itemEntity);

    @InheritInverseConfiguration
    ItemEntity toItemEntity(Item item);
}
