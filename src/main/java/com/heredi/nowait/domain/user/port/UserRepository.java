package com.heredi.nowait.domain.user.port;

import com.heredi.nowait.domain.user.model.Users;

public interface UserRepository {
    Users getUserById(Long userId);

    // Crear un nuevo usuario
    Users createUser(Users user);

    void updateUser(Users user);

    // Obtener un usuario por email/nickname y password para login
    Users getUser(String nickName, String password);

    Users getUserFromIdAndNickName(Long userId, String nickName);

    void saveUUID(String randomUUID, Long userId);
}
