package com.heredi.nowait.infrastructure.model.business.controller;

import com.heredi.nowait.application.model.business.service.interfaces.BusinessService;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    BusinessController(BusinessService businessService, AuthJwtImpl authJwt){
        this.businessService = businessService;
    }
}
