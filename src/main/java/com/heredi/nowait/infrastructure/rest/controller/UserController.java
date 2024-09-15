package com.heredi.nowait.infrastructure.rest.controller;

import com.heredi.nowait.application.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.user.dto.in.LoginUserRequestDTO;
import com.heredi.nowait.application.user.dto.out.CreateUserResponseDTO;
import com.heredi.nowait.application.user.mapper.UserMapper;
import com.heredi.nowait.application.user.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
    }

    // Crear un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        return new ResponseEntity<CreateUserResponseDTO>(userService.createUser(createUserRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> loginUser(@RequestBody LoginUserRequestDTO loginDTO) {
        try {
            LoginUserResponseDTO authUserResultDTO = userService.loginUser(loginDTO.getNickName(), loginDTO.getPassword());
            return new ResponseEntity<LoginUserResponseDTO>(authUserResultDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
