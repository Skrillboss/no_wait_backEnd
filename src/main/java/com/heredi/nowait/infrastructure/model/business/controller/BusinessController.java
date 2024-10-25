package com.heredi.nowait.infrastructure.model.business.controller;

import com.google.zxing.WriterException;
import com.heredi.nowait.application.model.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.model.business.service.interfaces.BusinessService;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    BusinessController(BusinessService businessService){
        this.businessService = businessService;
    }

    @PostMapping("/item/{businessId}/{itemId}/sendQR/mail")
    public boolean sendQRToMail(@PathVariable String businessId, @PathVariable String itemId) throws MessagingException, IOException, WriterException {
        return businessService.saveItemIdToMail(businessId, itemId);
    }

}
