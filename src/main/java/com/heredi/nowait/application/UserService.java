package com.heredi.nowait.application;

import com.heredi.nowait.domain.entities.User;
import com.heredi.nowait.infrastructure.UserRepository;
import com.heredi.nowait.presentation.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse createUser(User user) {
        User savedUser = userRepository.save(user);
        UserDTO userDTO = new UserDTO(savedUser);
        return new UserResponse(userDTO);
    }
}
