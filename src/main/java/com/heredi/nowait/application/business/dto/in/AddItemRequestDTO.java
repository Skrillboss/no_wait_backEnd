package com.heredi.nowait.application.business.dto.in;
import com.heredi.nowait.application.item.dto.in.ItemRequestDTO;
import lombok.Data;

@Data
public class AddItemRequestDTO {

    private String businessId;
    private ItemRequestDTO createItemRequestDTO;
}
