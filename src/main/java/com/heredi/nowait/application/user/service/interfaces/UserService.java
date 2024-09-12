package com.heredi.nowait.application.user.service.interfaces;

import com.heredi.nowait.application.user.dto.AuthUserResultDTO;
import com.heredi.nowait.application.user.dto.UserDTO;
import com.heredi.nowait.domain.model.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDTO createUser(Users user);
    AuthUserResultDTO loginUser(String nickName, String password);
    String generateToken(UserDTO user);
    String generateRefreshToken(String userId, String password);
}
