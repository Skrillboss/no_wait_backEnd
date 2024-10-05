package com.heredi.nowait.infrastructure.model.business.controller;

import com.google.zxing.WriterException;
import com.heredi.nowait.application.business.dto.in.AddItemRequestDTO;
import com.heredi.nowait.application.business.dto.in.SaveItemIdToMailDTO;
import com.heredi.nowait.application.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.business.service.interfaces.BusinessService;
import jakarta.mail.MessagingException;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    BusinessController(BusinessService businessService){
        this.businessService = businessService;
    }

    @PostMapping("/item/add")
    public ResponseEntity<AddItemResponseDTO> addItem(@RequestBody AddItemRequestDTO addItemRequestDTO) throws IOException, WriterException {
        return new ResponseEntity<AddItemResponseDTO>(businessService.addItem(addItemRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/item/saveQR/mail")
    public boolean saveIdItemToMail(@RequestBody SaveItemIdToMailDTO saveItemIdToMailDTO) throws MessagingException, IOException, WriterException {
        return businessService.saveItemIdToMail(saveItemIdToMailDTO);
    }
}
