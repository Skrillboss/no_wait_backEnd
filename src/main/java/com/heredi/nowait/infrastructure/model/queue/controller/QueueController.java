package com.heredi.nowait.infrastructure.model.queue.controller;

import com.heredi.nowait.application.model.queue.dto.out.QueueResponseDTO;
import com.heredi.nowait.application.model.queue.service.interfaces.QueueService;
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

    @GetMapping("/{queueId}")
     public ResponseEntity<QueueResponseDTO> getQueue(@PathVariable String queueId){
        return new ResponseEntity<QueueResponseDTO>(queueService.get(queueId), HttpStatus.OK);
    }
}
