package com.heredi.nowait.infrastructure.model.business.controller;

import com.heredi.nowait.application.model.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.model.business.service.interfaces.BusinessService;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    BusinessController(BusinessService businessService, AuthJwtImpl authJwt){
        this.businessService = businessService;
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<BusinessResponseDTO> getBusiness(@PathVariable String businessId){
        return new ResponseEntity<BusinessResponseDTO>(this.businessService.getBusiness(businessId), HttpStatus.OK);
    }
}
