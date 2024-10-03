package com.heredi.nowait.application.business.dto.out;

import com.heredi.nowait.application.item.dto.out.ItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddItemResponseDTO {

    private String businessId;
    private ItemResponseDTO itemResponseDTO;
}
