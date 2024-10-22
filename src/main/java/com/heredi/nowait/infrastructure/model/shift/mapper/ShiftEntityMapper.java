package com.heredi.nowait.infrastructure.model.shift.mapper;

import com.heredi.nowait.domain.shift.model.Shift;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import org.mapstruct.InheritInverseConfiguration;
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
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "shiftNumber", target = "shiftNumber")
    })
    Shift toShift(ShiftEntity shiftEntity);

    // De Shift a ShiftEntity
    @InheritInverseConfiguration
    ShiftEntity toShiftEntity(Shift shift);
}
