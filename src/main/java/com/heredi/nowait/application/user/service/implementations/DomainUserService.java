package com.heredi.nowait.application.user.service.implementations;

import com.heredi.nowait.application.user.dto.UserDTO;
import com.heredi.nowait.application.user.mapper.UserMapper;
import com.heredi.nowait.application.user.service.interfaces.UserService;
import com.heredi.nowait.domain.model.Users;
import com.heredi.nowait.domain.ports.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainUserService implements UserService {

    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;

    public DomainUserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(Users user) {
        Users createdUser = this.userRepository.createUser(user);
        return userMapper.toUserDTO(createdUser);
    }

    @Override
    public UserDTO loginUser(String nickName, String password) {
        Users obtainedUser = this.userRepository.getUser(nickName, password);
        return userMapper.toUserDTO(obtainedUser);
    }

    @Override
    public String generateToken(UserDTO userDTO) {
        Users user = userMapper.toUser(userDTO);
        return userRepository.getToken(user);
    }

    @Override
    public String generateRefreshToken(String nickName, String password){
        return userRepository.getRefreshToken(nickName, password);
    }
}
