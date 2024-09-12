package com.heredi.nowait.application.business.dto;
import com.heredi.nowait.application.item.dto.ItemDTO;
import lombok.Data;

import java.util.List;

@Data
public class BusinessDTO {

    private String id;
    private String cif;
    private String name;
    private String imageUrl;
    private String phone;
    private String address;
    private String email;
    private String createdAt;
    private List<ItemDTO> items;
}