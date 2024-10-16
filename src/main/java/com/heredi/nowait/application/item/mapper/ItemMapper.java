package com.heredi.nowait.application.item.mapper;

import com.heredi.nowait.application.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.shift.mapper.ShiftMapper;
import com.heredi.nowait.domain.item.model.Item;
import com.heredi.nowait.domain.item.model.Item.ItemStatus;
import org.springframework.stereotype.Component;

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
        dto.setId(item.getId().toString());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setRating(item.getRating());
        dto.setMainImagePath(item.getMainImagePath());
        dto.setSecondaryImagePath(item.getSecondaryImagePath());
        dto.setStatus(item.getStatus().name());

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
    public Item toItem(ItemRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setRating(0.0);
        item.setMainImagePath(dto.getMainImagePath());
        item.setSecondaryImagePath(dto.getSecondaryImagePath());
        item.setStatus(ItemStatus.valueOf(dto.getStatus()));

        return item;
    }

    public List<Item> toItems(List<ItemRequestDTO> itemsDTO){
        if(itemsDTO ==  null){
            return null;
        }

        return itemsDTO.stream()
                .map(this::toItem)
                .collect(Collectors.toList());
    }
}
