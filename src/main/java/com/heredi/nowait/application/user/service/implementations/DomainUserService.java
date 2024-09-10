package com.heredi.nowait.application.user.service.implementations;

import com.heredi.nowait.application.user.service.interfaces.UserService;
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
    public Users loginUser(String nickName, String password) {
        return this.userRepository.getUser(nickName, password);
    }

    @Override
    public String generateToken(Users user) {
        return userRepository.getToken(user);
    }
}
