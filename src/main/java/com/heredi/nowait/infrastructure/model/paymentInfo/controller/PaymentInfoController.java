package com.heredi.nowait.infrastructure.model.paymentInfo.controller;

import com.heredi.nowait.application.model.paymentInfo.dto.out.PaymentInfoResponseDTO;
import com.heredi.nowait.application.model.paymentInfo.service.interfaces.PaymentInfoService;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequestMapping("/paymentInfo")
public class PaymentInfoController {

    private final PaymentInfoService paymentInfoService;

    private final AuthJwtImpl authJwt;

    PaymentInfoController(PaymentInfoService paymentInfoService, AuthJwtImpl authJwt){
        this.paymentInfoService = paymentInfoService;
        this.authJwt = authJwt;
    }

    @GetMapping
    public ResponseEntity<List<PaymentInfoResponseDTO>> getPaymentInfo(@RequestHeader("Authorization") String authorizationHeader) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);
        return new ResponseEntity<List<PaymentInfoResponseDTO>>(paymentInfoService.getPaymentInfo(userId), HttpStatus.OK);
    }
}
