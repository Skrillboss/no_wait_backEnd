package com.heredi.nowait.application.user.service.interfaces;

import com.heredi.nowait.domain.model.Users;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    Users createUser(Users user);
    Users loginUser(String nickName, String password);
    String generateToken(Users user);
    String generateRefreshToken(String userId, String password);
}
