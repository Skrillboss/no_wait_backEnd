package com.heredi.nowait.infrastructure.model.queue.mapper;

import com.heredi.nowait.domain.queue.model.Queue;
import com.heredi.nowait.infrastructure.model.queue.entity.QueueEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface QueueEntityMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "currentWaitingDuration", target = "currentWaitingDuration"),
            @Mapping(source = "shiftDuration", target = "shiftDuration"),
            @Mapping(source = "startTimeHour", target = "startTimeHour"),
            @Mapping(source = "endTimeHour", target = "endTimeHour"),
            @Mapping(source = "peoplePerShift", target = "peoplePerShift"),
            @Mapping(source = "shifts", target = "shifts"),
            @Mapping(source = "status", target = "status"),
    })
    Queue toQueue(QueueEntity queueEntity);

    @InheritInverseConfiguration
    QueueEntity toQueueEntity(Queue queue);

}
