package com.heredi.nowait.infrastructure.rest.controller;

import com.heredi.nowait.application.user.dto.UserDTO;
import com.heredi.nowait.application.user.mapper.UserMapper;
import com.heredi.nowait.application.user.service.UserService;
import com.heredi.nowait.domain.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

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

}
