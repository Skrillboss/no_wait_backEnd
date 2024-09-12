package com.heredi.nowait.application.shift.mapper;

import com.heredi.nowait.application.shift.dto.ShiftDTO;
import com.heredi.nowait.domain.model.Shift;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShiftMapper {
    private ShiftDTO toShiftDTO(Shift shift){
        if(shift == null){
            return null;
        }
        ShiftDTO dto = new ShiftDTO();
        dto.setId(shift.getId());
        dto.setItemName(shift.getItemName());
        dto.setBusinessName(shift.getBusinessName());
        dto.setShiftTime(shift.getShiftTime().toString());
        dto.setPeopleInShift(shift.getPeopleInShift());
        dto.setCreateAt(shift.getCreateAt().toString());
        dto.setNotifyTime(shift.getNotifyTime().toString());
        dto.setCurrentWaitingDuration(shift.getCurrentWaitingDuration().toMinutes());
        dto.setExpirationTime(shift.getExpirationTime().toString());
        dto.setEstimatedArrivalTime(shift.getEstimatedArrivalTime().toMinutes());
        dto.setStatus(shift.getStatus().name());
        dto.setShiftNumber(shift.getShiftNumber());

        return dto;
    }

    public List<ShiftDTO> toShiftsDTO(List<Shift> shifts){
        if(shifts == null){
            return null;
        }

        return shifts.stream()
                .map(this::toShiftDTO)
                .collect(Collectors.toList());
    }

    private Shift toShift(ShiftDTO dto) {
        if (dto == null) {
            return null;
        }

        Shift shift = new Shift();
        shift.setId(dto.getId());
        shift.setItemName(dto.getItemName());
        shift.setBusinessName(dto.getBusinessName());
        shift.setShiftTime(LocalDateTime.parse(dto.getShiftTime()));
        shift.setPeopleInShift(dto.getPeopleInShift());
        shift.setCreateAt(LocalDateTime.parse(dto.getCreateAt()));
        shift.setNotifyTime(LocalDateTime.parse(dto.getNotifyTime()));
        shift.setCurrentWaitingDuration(Duration.ofMinutes((long) dto.getCurrentWaitingDuration()));
        shift.setExpirationTime(LocalDateTime.parse(dto.getExpirationTime()));
        shift.setEstimatedArrivalTime(Duration.ofMinutes((long) dto.getEstimatedArrivalTime()));
        shift.setStatus(Shift.ShiftStatus.valueOf(dto.getStatus()));
        shift.setShiftNumber(dto.getShiftNumber());

        return shift;
    }

    public List<Shift> toShifts(List<ShiftDTO> shiftsDTO){
        if(shiftsDTO == null){
            return null;
        }

        return shiftsDTO.stream()
                .map(this::toShift)
                .collect(Collectors.toList());
    }
}
