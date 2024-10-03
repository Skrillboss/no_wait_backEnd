package com.heredi.nowait.infrastructure.model.item.controller;

import com.heredi.nowait.application.item.dto.in.CreateItemRequestDTO;
import com.heredi.nowait.application.item.dto.out.ItemResponseDTO;
import com.heredi.nowait.application.item.service.interfaces.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    ItemController(ItemService itemService){
        this.itemService = itemService;
    }

//    Este endpoint fue un error ya que no se puede crear un item por si mismo, sino que se debe crear a partir del modelo
//    Business para poder introducir dentro del la entididad de Business el item, por lo tanto este endpoint no debe existir
//    Lo conservo por una futura implementación de un metodo Item...
    @PostMapping("/create")
    public ResponseEntity<ItemResponseDTO> createItem(@RequestBody CreateItemRequestDTO createItemRequestDTO) {
        return new ResponseEntity<ItemResponseDTO>(itemService.createItem(createItemRequestDTO), HttpStatus.CREATED);
    }
}
