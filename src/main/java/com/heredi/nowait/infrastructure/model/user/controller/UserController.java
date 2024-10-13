package com.heredi.nowait.infrastructure.model.user.controller;

import com.heredi.nowait.application.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.user.dto.out.CreateUserResponseDTO;
import com.heredi.nowait.application.user.dto.out.RefreshTokenResponseDTO;
import com.heredi.nowait.application.user.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        return new ResponseEntity<CreateUserResponseDTO>(userService.createUser(createUserRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> loginUser(@RequestParam String nickName, String password) {
        try {
            LoginUserResponseDTO authUserResultDTO = userService.loginUser(nickName, password);
            return new ResponseEntity<LoginUserResponseDTO>(authUserResultDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String accessToken) {
        RefreshTokenResponseDTO refreshTokenResponseDTO = userService.refreshTokens(authorizationHeader, accessToken);
        return new ResponseEntity<RefreshTokenResponseDTO>(refreshTokenResponseDTO, HttpStatus.OK);
    }
}
