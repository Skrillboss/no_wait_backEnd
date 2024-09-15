package com.heredi.nowait.infrastructure.rest.controller;

import com.heredi.nowait.application.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.business.dto.in.CreateBusinessRequestDTO;
import com.heredi.nowait.application.business.service.interfaces.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create")
    public ResponseEntity<BusinessResponseDTO> createBusiness(@RequestBody CreateBusinessRequestDTO createBusinessDTO){
        return null;
    }
}
