package com.heredi.nowait.application.model.queue.mapper;

import com.heredi.nowait.application.model.queue.dto.in.QueueRequestDTO;
import com.heredi.nowait.application.model.queue.dto.out.QueueResponseDTO;
import com.heredi.nowait.application.model.shift.mapper.ShiftMapper;
import com.heredi.nowait.domain.queue.model.Queue;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class QueueMapper {

    private final ShiftMapper shiftMapper;

    public QueueMapper(ShiftMapper shiftMapper) {
        this.shiftMapper = shiftMapper;
    }

    public Queue toQueue(QueueRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Queue queue = new Queue();
        queue.setCurrentWaitingDuration(Duration.ofMinutes((long) dto.getCurrentWaitingDuration()));
        queue.setShiftDuration(Duration.ofMinutes((long) dto.getShiftDuration()));
        queue.setStartTimeHour(LocalTime.parse(dto.getStartTimeHour(), DateTimeFormatter.ofPattern("HH:mm")));
        queue.setEndTimeHour(LocalTime.parse(dto.getEndTimeHour(), DateTimeFormatter.ofPattern("HH:mm")));
        queue.setPeoplePerShift(dto.getPeoplePerShift());
        queue.setStatus(Queue.QueueStatus.valueOf(dto.getStatus()));

        return queue;
    }

    public QueueResponseDTO toQueueResponseDTO(Queue queue) {
        if (queue == null) {
            return null;
        }

        QueueResponseDTO dto = new QueueResponseDTO();

        dto.setId(queue.getId().toString());
        dto.setCurrentWaitingDuration(queue.getCurrentWaitingDuration().toMinutes());
        dto.setShiftDuration(queue.getShiftDuration().toMinutes());
        dto.setStartTimeHour(queue.getStartTimeHour().toString());
        dto.setEndTimeHour(queue.getEndTimeHour().toString());
        dto.setPeoplePerShift(queue.getPeoplePerShift());
        dto.setShiftResponseDTO(queue.getShifts() != null ?
                shiftMapper.toShiftResponseDTOList(queue.getShifts())
                : null);
        dto.setStatus(queue.getStatus().name());

        return dto;
    }
}
