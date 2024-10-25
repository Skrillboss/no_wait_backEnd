package com.heredi.nowait.infrastructure.model.queue.controller;

import com.heredi.nowait.application.model.queue.service.interfaces.QueueService;
import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    @Autowired
    QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("/generateShift")
    public ResponseEntity<ShiftResponseDTO> generateShift(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String itemId, String queueId) {
        return new ResponseEntity<ShiftResponseDTO>(queueService.generateShift(itemId, queueId, authorizationHeader), HttpStatus.OK);
    }
}
