package com.heredi.nowait.infrastructure.model.user.controller;

import com.heredi.nowait.application.model.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.model.user.dto.in.UpdateUserRequestDTO;
import com.heredi.nowait.application.model.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.UpdatedUserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.UserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.RefreshTokenResponseDTO;
import com.heredi.nowait.application.model.user.service.interfaces.UserService;
import com.heredi.nowait.infrastructure.auth.jwt.AuthJwtImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final AuthJwtImpl authJwt;

    @Autowired
    UserController(UserService userService, AuthJwtImpl authJwt) {
        this.userService = userService;
        this.authJwt = authJwt;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) throws MessagingException {
        return new ResponseEntity<>(userService.createUser(createUserRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/verifyEmail/{nickName}/{email}")
    public void verifyEmail(@PathVariable String nickName, @PathVariable String email){
        userService.verifyNickNameAndEmail(nickName, email);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> loginUser(@RequestParam String nickName, String password) {
            LoginUserResponseDTO authUserResultDTO = userService.loginUser(nickName, password);
            return new ResponseEntity<LoginUserResponseDTO>(authUserResultDTO, HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<UpdatedUserResponseDTO> updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UpdateUserRequestDTO updateUserRequestDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authJwt.extractUserId(accessToken);

        return new ResponseEntity<UpdatedUserResponseDTO>(userService.updateUser(userId,updateUserRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/login/jwt")
    public ResponseEntity<UserResponseDTO> loginUserWithJWT(@RequestHeader("Authorization") String authorizationHeader){
        UserResponseDTO authUserResultDTO = userService.loginUserWithToken(authorizationHeader);
        return new ResponseEntity<UserResponseDTO>(authUserResultDTO, HttpStatus.OK);
    }

    @PatchMapping("/refreshToken")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String accessToken) {
        RefreshTokenResponseDTO refreshTokenResponseDTO = userService.refreshTokens(authorizationHeader, accessToken);
        return new ResponseEntity<RefreshTokenResponseDTO>(refreshTokenResponseDTO, HttpStatus.OK);
    }
}
