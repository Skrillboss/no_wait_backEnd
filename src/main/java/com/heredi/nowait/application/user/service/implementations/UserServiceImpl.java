package com.heredi.nowait.application.user.service.implementations;

import com.heredi.nowait.application.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.user.dto.out.CreateUserResponseDTO;
import com.heredi.nowait.application.user.mapper.UserMapper;
import com.heredi.nowait.application.user.service.interfaces.UserService;
import com.heredi.nowait.domain.model.Users;
import com.heredi.nowait.domain.ports.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public CreateUserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        Users createdUser = this.userRepository.createUser(userMapper.toUser(createUserRequestDTO));
        return userMapper.toCreateUserResponseDTO(createdUser);
    }

    @Override
    public LoginUserResponseDTO loginUser(String nickName, String password) {
        Users obtainedUser = this.userRepository.getUser(nickName, password);
        CreateUserResponseDTO userResponseDTO = userMapper.toCreateUserResponseDTO(obtainedUser);
        String accessToken = userRepository.getToken(obtainedUser);
        String refreshToken = userRepository.getRefreshToken(obtainedUser.getNickName(), obtainedUser.getPassword());
        return new LoginUserResponseDTO(userResponseDTO, accessToken,refreshToken);
    }
}
