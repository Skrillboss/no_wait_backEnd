package com.heredi.nowait.infrastructure.model.paymentInfo.controller;

import com.heredi.nowait.application.model.paymentInfo.dto.in.PaymentInfoRequestDTO;
import com.heredi.nowait.application.model.paymentInfo.dto.out.PaymentInfoResponseDTO;
import com.heredi.nowait.application.model.paymentInfo.service.interfaces.PaymentInfoService;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseEntity<PaymentInfoResponseDTO> createPaymentInfo(@RequestHeader("Authorization") String authorizationHeader, @RequestBody PaymentInfoRequestDTO paymentInfoRequestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException{
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);
        return new ResponseEntity<PaymentInfoResponseDTO>(paymentInfoService.createPaymentInfo(userId, paymentInfoRequestDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PaymentInfoResponseDTO>> getPaymentInfo(@RequestHeader("Authorization") String authorizationHeader) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);
        return new ResponseEntity<List<PaymentInfoResponseDTO>>(paymentInfoService.getPaymentInfo(userId), HttpStatus.OK);
    }

    @PatchMapping("/update/{paymentInfoId}")
    public ResponseEntity<PaymentInfoResponseDTO> updatePaymentInfo(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String paymentInfoId, @RequestBody PaymentInfoRequestDTO paymentInfoRequestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);
        return new ResponseEntity<PaymentInfoResponseDTO>(paymentInfoService.updatePaymentInfo(paymentInfoId, paymentInfoRequestDTO, userId), HttpStatus.OK);
    }
}
