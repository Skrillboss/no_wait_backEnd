package com.heredi.nowait.application.user.service.interfaces;

import com.heredi.nowait.application.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.user.dto.out.UserResponseDTO;
import com.heredi.nowait.application.user.dto.out.RefreshTokenResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDTO createUser(CreateUserRequestDTO userDTO);
    LoginUserResponseDTO loginUser(String nickName, String password);
    UserResponseDTO loginUserWithToken(String authorizationHeader);
    RefreshTokenResponseDTO refreshTokens(String authorizationHeader, String accessToken);
}
