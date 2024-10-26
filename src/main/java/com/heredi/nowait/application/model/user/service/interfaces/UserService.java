package com.heredi.nowait.application.model.user.service.interfaces;

import com.heredi.nowait.application.model.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.model.user.dto.in.UpdateUserRequestDTO;
import com.heredi.nowait.application.model.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.UpdatedUserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.UserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.RefreshTokenResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDTO createUser(CreateUserRequestDTO userDTO);
    UpdatedUserResponseDTO updateUser(Long userId, UpdateUserRequestDTO updateUserRequestDTO);
    LoginUserResponseDTO loginUser(String nickName, String password);
    UserResponseDTO loginUserWithToken(String authorizationHeader);
    RefreshTokenResponseDTO refreshTokens(String authorizationHeader, String accessToken);
}
