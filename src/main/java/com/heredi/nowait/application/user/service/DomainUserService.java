package com.heredi.nowait.application.user.service;

import com.heredi.nowait.domain.model.Users;
import com.heredi.nowait.domain.ports.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DomainUserService implements UserService {

    private final UserRepository userRepository;

    public DomainUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Users createUser(Users user) {
        return this.userRepository.createUser(user);
    }

    @Override
    public Users loginUser(String identifier, String password) {
        return this.userRepository.getUser(identifier, password);
    }
}
