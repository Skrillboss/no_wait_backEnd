package com.heredi.nowait.infrastructure.model.business.controller;

import com.heredi.nowait.application.business.dto.in.AddItemRequestDTO;
import com.heredi.nowait.application.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.business.service.interfaces.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    BusinessController(BusinessService businessService){
        this.businessService = businessService;
    }

    @PostMapping("/addItem")
    public ResponseEntity<AddItemResponseDTO> addItem(@RequestBody AddItemRequestDTO addItemRequestDTO) {
        return new ResponseEntity<AddItemResponseDTO>(businessService.addItem(addItemRequestDTO), HttpStatus.CREATED);
    }
}
