package com.heredi.nowait.application.model.user.service.interfaces;

import com.heredi.nowait.application.model.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.model.user.dto.in.UpdateUserRequestDTO;
import com.heredi.nowait.application.model.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.UpdatedUserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.UserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.RefreshTokenResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserResponseDTO createUser(CreateUserRequestDTO userDTO) throws MessagingException;
    UpdatedUserResponseDTO updateUser(Long userId, UpdateUserRequestDTO updateUserRequestDTO);
    void verifyNickNameAndEmail(String nickName, String email);
    LoginUserResponseDTO loginUser(String nickName, String password);
    UserResponseDTO loginUserWithToken(String authorizationHeader);
    RefreshTokenResponseDTO refreshTokens(String authorizationHeader, String accessToken);
}
