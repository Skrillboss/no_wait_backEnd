package com.heredi.nowait.presentation.user.controller;

import com.heredi.nowait.application.UserService;
import com.heredi.nowait.domain.entities.User;
import com.heredi.nowait.presentation.user.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Crear un nuevo usuario
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        UserResponse createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
