package com.heredi.nowait.infrastructure.rest.controller;

import com.heredi.nowait.application.user.dto.AuthUserResultDTO;
import com.heredi.nowait.application.user.dto.LoginDTO;
import com.heredi.nowait.application.user.dto.UserDTO;
import com.heredi.nowait.application.user.mapper.UserMapper;
import com.heredi.nowait.application.user.service.interfaces.UserService;
import com.heredi.nowait.domain.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    UserController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // Crear un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody Users user) {
        Users createdUser = userService.createUser(user);
        UserDTO userDTO = userMapper.toUserDTO(createdUser);
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthUserResultDTO> loginUser(@RequestBody LoginDTO loginDTO){
        try {
            Users loggedUser = userService.loginUser(loginDTO.getNickName(), loginDTO.getPassword());
            UserDTO userDTO = userMapper.toUserDTO(loggedUser);
            String token = userService.generateToken(loggedUser);
            String refreshToken = userService.generateRefreshToken(loginDTO.getNickName(), loginDTO.getPassword());
            AuthUserResultDTO authUserResultDTO = new AuthUserResultDTO(userDTO, token, refreshToken);
            return new ResponseEntity<>(authUserResultDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
