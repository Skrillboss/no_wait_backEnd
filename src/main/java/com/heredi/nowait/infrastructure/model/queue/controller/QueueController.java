package com.heredi.nowait.infrastructure.model.queue.controller;

import com.heredi.nowait.application.model.queue.service.interfaces.QueueService;
import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final QueueService queueService;

    @Autowired
    QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("/generateShift/{queueId}")
    public ResponseEntity<ShiftResponseDTO> generateShift(@PathVariable String queueId, @PathVariable String userId) {
        return new ResponseEntity<ShiftResponseDTO>(queueService.generateShift(queueId, userId), HttpStatus.OK);
    }
}
