package com.heredi.nowait.application.model.business.dto.out;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class BusinessResponseDTO {

    private String id;
    private String cif;
    private String name;
    private String imageUrl;
    private String phone;
    private String address;
    private String email;
    private String createdAt;
    private List<ItemResponseDTO> items;
}