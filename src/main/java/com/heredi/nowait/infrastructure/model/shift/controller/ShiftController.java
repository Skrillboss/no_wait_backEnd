package com.heredi.nowait.infrastructure.model.shift.controller;

import com.heredi.nowait.application.model.shift.dto.out.ShiftResponseDTO;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import com.heredi.nowait.application.model.shift.service.interfaces.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@RestController
@RequestMapping("/shift")

public class ShiftController {

    private final ShiftService shiftService;

    private final AuthJwtImpl authJwt;

    @Autowired
    ShiftController(ShiftService shiftService, AuthJwtImpl authJwt) {
        this.authJwt = authJwt;
        this.shiftService = shiftService;
    }

    @PostMapping("/create/{queueId}")
    public ResponseEntity<ShiftResponseDTO> createShift(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String queueId) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);
        return new ResponseEntity<ShiftResponseDTO>(shiftService.create(queueId, userId), HttpStatus.OK);
    }
}