package com.heredi.nowait.application.item.mapper;

import com.heredi.nowait.application.item.dto.ItemDTO;
import com.heredi.nowait.application.shift.mapper.ShiftMapper;
import com.heredi.nowait.domain.model.Item;
import com.heredi.nowait.domain.model.Item.ItemStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ItemMapper {

    private final ShiftMapper shiftMapper;

    public ItemMapper(ShiftMapper shiftMapper){
        this.shiftMapper = shiftMapper;
    }

    // Convierte de Item a ItemDTO
    public ItemDTO toItemDTO(Item item) {
        if (item == null) {
            return null;
        }

        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setNumberPeopleWaiting(item.getNumberPeopleWaiting());
        dto.setPeoplePerShift(item.getPeoplePerShift());
        dto.setNumberShiftsWaiting(item.getNumberShiftsWaiting());
        dto.setRating(item.getRating());
        dto.setMainImagePath(item.getMainImagePath());
        dto.setSecondaryImagePath(item.getSecondaryImagePath());
        dto.setCurrentWaitingDuration((double) item.getCurrentWaitingDuration().toMinutes());
        dto.setDurationPerShifts((double) item.getDurationPerShifts().toMinutes());
        dto.setStatus(item.getStatus().name());
        dto.setShifts(shiftMapper.toShiftsDTO(item.getShifts()));

        return dto;
    }

    // Convierte de ItemDTO a Item
    public Item toItem(ItemDTO dto) {
        if (dto == null) {
            return null;
        }

        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setNumberPeopleWaiting(dto.getNumberPeopleWaiting());
        item.setPeoplePerShift(dto.getPeoplePerShift());
        item.setNumberShiftsWaiting(dto.getNumberShiftsWaiting());
        item.setRating(dto.getRating());
        item.setMainImagePath(dto.getMainImagePath());
        item.setSecondaryImagePath(dto.getSecondaryImagePath());
        item.setCurrentWaitingDuration(Duration.ofMinutes(dto.getCurrentWaitingDuration().longValue()));
        item.setDurationPerShifts(Duration.ofMinutes(dto.getDurationPerShifts().longValue()));
        item.setStatus(ItemStatus.valueOf(dto.getStatus()));
        item.setShifts(shiftMapper.toShifts(dto.getShifts()));

        return item;
    }
}
