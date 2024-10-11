package com.heredi.nowait.infrastructure.model.business.controller;

import com.google.zxing.WriterException;
import com.heredi.nowait.application.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.business.service.interfaces.BusinessService;
import com.heredi.nowait.application.item.dto.in.ItemRequestDTO;
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

    @PostMapping("{businessId}/item/add")
    public ResponseEntity<AddItemResponseDTO> addItem(@PathVariable String businessId, @RequestBody ItemRequestDTO itemRequestDTO) throws IOException, WriterException {
        return new ResponseEntity<AddItemResponseDTO>(businessService.addItem(businessId, itemRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/item/{businessId}/{itemId}/sendQR/mail")
    public boolean sendQRToMail(@PathVariable String businessId, @PathVariable String itemId) throws MessagingException, IOException, WriterException {
        return businessService.saveItemIdToMail(businessId, itemId);
    }

}
