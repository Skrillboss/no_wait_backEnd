package com.heredi.nowait.application;

import com.heredi.nowait.domain.entities.User;
import com.heredi.nowait.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        System.out.println("Creo que se creo de manera correcta el usuario dentro de mi base de datos postgres, por favor compruebalo...");
        return userRepository.save(user);
    }
}
