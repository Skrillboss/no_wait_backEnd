package com.heredi.nowait.application.item.mapper;

import com.heredi.nowait.application.item.dto.in.CreateItemRequestDTO;
import com.heredi.nowait.application.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.shift.mapper.ShiftMapper;
import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.model.Item.ItemStatus;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {

    private final ShiftMapper shiftMapper;

    public ItemMapper(ShiftMapper shiftMapper){
        this.shiftMapper = shiftMapper;
    }

    // Convierte de Item a ItemDTO
    public ItemResponseDTO toItemResponseDTO(Item item) {
        if (item == null) {
            return null;
        }

        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setNumberPeopleWaiting(item.getNumberPeopleWaiting());
        dto.setPeoplePerShift(item.getPeoplePerShift());
        dto.setNumberShiftsWaiting(item.getNumberShiftsWaiting());
        dto.setRating(item.getRating());
        dto.setMainImagePath(item.getMainImagePath());
        dto.setSecondaryImagePath(item.getSecondaryImagePath());
        dto.setCurrentWaitingDuration((int) item.getCurrentWaitingDuration().toMinutes());
        dto.setDurationPerShifts((int) item.getDurationPerShifts().toMinutes());
        dto.setStatus(item.getStatus().name());
        dto.setShifts(shiftMapper.toShiftsDTO(item.getShifts()));

        return dto;
    }

    public List<ItemResponseDTO> toItemsDTO(List<Item> items){
        if(items == null){
            return null;
        }

        return items.stream()
                .map(this::toItemResponseDTO)
                .collect(Collectors.toList());
    }

    // Convierte de ItemDTO a Item
    public Item toItem(CreateItemRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setNumberPeopleWaiting(0);
        item.setPeoplePerShift(dto.getPeoplePerShift());
        item.setNumberShiftsWaiting(0);
        item.setRating(0.0);
        item.setMainImagePath(dto.getMainImagePath());
        item.setSecondaryImagePath(dto.getSecondaryImagePath());
        item.setCurrentWaitingDuration(Duration.ofMinutes(0));
        item.setDurationPerShifts(Duration.ofMinutes(dto.getDurationPerShifts()));
        item.setStatus(ItemStatus.valueOf(dto.getStatus()));
        item.setShifts(shiftMapper.toShifts(null));

        return item;
    }

    public List<Item> toItems(List<CreateItemRequestDTO> itemsDTO){
        if(itemsDTO ==  null){
            return null;
        }

        return itemsDTO.stream()
                .map(this::toItem)
                .collect(Collectors.toList());
    }
}
