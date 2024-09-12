package com.heredi.nowait.domain.ports;

import com.heredi.nowait.domain.model.Users;

public interface UserRepository {
    // Crear un nuevo usuario
    Users createUser(Users user);

    // Obtener un usuario por email/nickname y password para login
    Users getUser(String nickName, String password);

    // Obtener un token por nickname
    String getToken(Users user);

    // Obtener un refreshToken
    String getRefreshToken(String nickName, String password);
}
