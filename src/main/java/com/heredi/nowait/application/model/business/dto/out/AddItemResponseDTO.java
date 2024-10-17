package com.heredi.nowait.application.model.business.dto.out;

import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddItemResponseDTO {

    private String businessId;
    private ItemResponseDTO itemResponseDTO;
}
