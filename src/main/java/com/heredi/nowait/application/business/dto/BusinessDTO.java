package com.heredi.nowait.application.business.dto;
import lombok.Data;

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
}