package com.heredi.nowait.infrastructure.model.item.controller;

import com.google.zxing.WriterException;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.model.item.service.interfaces.ItemService;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    private final AuthJwtImpl authJwt;

    @Autowired
    ItemController(ItemService itemService, AuthJwtImpl authJwt){
        this.itemService = itemService;
        this.authJwt = authJwt;
    }

    @PostMapping("/create")
    public ResponseEntity<ItemResponseDTO> createItem(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ItemRequestDTO itemRequestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);
        return new ResponseEntity<ItemResponseDTO>(itemService.create(userId, itemRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable String itemId) {
        return new ResponseEntity<ItemResponseDTO>(itemService.get(itemId), HttpStatus.OK);
    }

    @PostMapping("/{itemId}/sendQR/mail")
    public void sendQRToMail(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String itemId) throws MessagingException, IOException, WriterException, NoSuchAlgorithmException, InvalidKeySpecException {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);

        itemService.saveItemIdQrToMail(userId, itemId);
    }
}
