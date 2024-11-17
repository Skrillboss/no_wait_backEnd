package com.heredi.nowait.infrastructure.model.business.controller;

import com.heredi.nowait.application.model.business.dto.in.BusinessRequestDTO;
import com.heredi.nowait.application.model.business.dto.out.BusinessResponseDTO;
import com.heredi.nowait.application.model.business.service.interfaces.BusinessService;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    private final AuthJwtImpl authJwt;

    @Autowired
    BusinessController(BusinessService businessService, AuthJwtImpl authJwt, AuthJwtImpl authJwt1){
        this.businessService = businessService;
        this.authJwt = authJwt1;
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<BusinessResponseDTO> getBusiness(@PathVariable String businessId){
        return new ResponseEntity<BusinessResponseDTO>(this.businessService.getBusiness(businessId), HttpStatus.OK);
    }

    @PatchMapping("/update/{businessId}")
    public ResponseEntity<BusinessResponseDTO> updateBusiness(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String businessId, @RequestBody BusinessRequestDTO businessRequestDTO ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);
        return new ResponseEntity<BusinessResponseDTO>(this.businessService.updateBusiness(businessId, businessRequestDTO, userId), HttpStatus.OK);
    }
}
