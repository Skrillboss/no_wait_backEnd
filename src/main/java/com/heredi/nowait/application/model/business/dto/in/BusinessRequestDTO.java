package com.heredi.nowait.application.model.business.dto.in;

import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import lombok.Data;

import java.util.List;

@Data
public class BusinessRequestDTO {

    private String cif;
    private String name;
    private String imageUrl;
    private String phone;
    private String address;
    private String email;
    private List<ItemRequestDTO> items;
}