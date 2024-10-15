package com.heredi.nowait.infrastructure.model.item.controller;

import com.heredi.nowait.application.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.item.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDTO> createItem(@PathVariable String itemId) {
        return new ResponseEntity<ItemResponseDTO>(itemService.getItem(itemId), HttpStatus.OK);
    }
}
