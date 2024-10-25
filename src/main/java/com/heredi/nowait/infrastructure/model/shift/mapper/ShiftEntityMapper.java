package com.heredi.nowait.infrastructure.model.shift.mapper;

import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ShiftEntityMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "createAt", target = "createAt"),
            @Mapping(source = "notifyTime", target = "notifyTime"),
            @Mapping(source = "currentWaitingDuration", target = "currentWaitingDuration"),
            @Mapping(source = "expirationTime", target = "expirationTime"),
            @Mapping(source = "estimatedArrivalTime", target = "estimatedArrivalTime"),
            @Mapping(source = "queue.item.name", target = "itemName"),
            @Mapping(source = "queue.item.mainImagePath", target = "itemImageUrl"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "shiftNumber", target = "shiftNumber")
    })
    Shift toShift(ShiftEntity shiftEntity);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "createAt", target = "createAt"),
            @Mapping(source = "notifyTime", target = "notifyTime"),
            @Mapping(source = "currentWaitingDuration", target = "currentWaitingDuration"),
            @Mapping(source = "expirationTime", target = "expirationTime"),
            @Mapping(source = "estimatedArrivalTime", target = "estimatedArrivalTime"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "shiftNumber", target = "shiftNumber")
    })
    ShiftEntity toShiftEntity(Shift shift);
}
