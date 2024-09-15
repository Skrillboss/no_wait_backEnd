package com.heredi.nowait.application.user.service.interfaces;

import com.heredi.nowait.application.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.user.dto.out.CreateUserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    CreateUserResponseDTO createUser(CreateUserRequestDTO userDTO);
    LoginUserResponseDTO loginUser(String nickName, String password);
}
