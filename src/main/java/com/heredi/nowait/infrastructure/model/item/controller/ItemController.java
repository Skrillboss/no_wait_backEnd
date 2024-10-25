package com.heredi.nowait.infrastructure.model.item.controller;

import com.google.zxing.WriterException;
import com.heredi.nowait.application.model.business.dto.out.AddItemResponseDTO;
import com.heredi.nowait.application.model.item.dto.in.ItemRequestDTO;
import com.heredi.nowait.application.model.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.model.item.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @PostMapping("/create/{businessId}")
    public ResponseEntity<AddItemResponseDTO> createItem(@PathVariable String businessId, @RequestBody ItemRequestDTO itemRequestDTO) throws IOException, WriterException {
        return new ResponseEntity<AddItemResponseDTO>(itemService.create(businessId, itemRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDTO> getItem(@PathVariable String itemId) {
        return new ResponseEntity<ItemResponseDTO>(itemService.get(itemId), HttpStatus.OK);
    }
}
