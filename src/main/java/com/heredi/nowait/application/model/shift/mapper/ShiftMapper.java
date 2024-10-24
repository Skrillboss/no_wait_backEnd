package com.heredi.nowait.application.model.shift.mapper;

import com.heredi.nowait.application.model.shift.dto.in.ShiftRequestDTO;
import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import com.heredi.nowait.domain.shift.model.Shift;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShiftMapper {
    public ShiftResponseDTO toShiftDTO(Shift shift){
        if(shift == null){
            return null;
        }
        ShiftResponseDTO dto = new ShiftResponseDTO();
        dto.setId(shift.getId());
        dto.setCreateAt(shift.getCreateAt().toString());
        dto.setNotifyTime(shift.getNotifyTime().toString());
        dto.setCurrentWaitingDuration(shift.getCurrentWaitingDuration().toMinutes());
        dto.setExpirationTime(shift.getExpirationTime().toString());
        dto.setEstimatedArrivalTime(shift.getEstimatedArrivalTime().toMinutes());
        dto.setStatus(shift.getStatus().name());
        dto.setShiftNumber(shift.getShiftNumber());

        return dto;
    }

    public List<ShiftResponseDTO> toShiftsDTO(List<Shift> shifts){
        if(shifts == null){
            return new ArrayList<>();
        }

        return shifts.stream()
                .map(this::toShiftDTO)
                .collect(Collectors.toList());
    }

    public Shift toShift(ShiftRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Shift shift = new Shift();
        shift.setCreateAt(LocalDateTime.parse(dto.getCreateAt()));
        shift.setNotifyTime(LocalDateTime.parse(dto.getNotifyTime()));
        shift.setCurrentWaitingDuration(Duration.ofMinutes((long) dto.getCurrentWaitingDuration()));
        shift.setExpirationTime(LocalDateTime.parse(dto.getExpirationTime()));
        shift.setEstimatedArrivalTime(Duration.ofMinutes((long) dto.getEstimatedArrivalTime()));
        shift.setStatus(Shift.ShiftStatus.valueOf(dto.getStatus()));
        shift.setShiftNumber(dto.getShiftNumber());

        return shift;
    }

    public List<Shift> toShifts(List<ShiftRequestDTO> shiftsDTO){
        if(shiftsDTO == null){
            return null;
        }

        return shiftsDTO.stream()
                .map(this::toShift)
                .collect(Collectors.toList());
    }
}
