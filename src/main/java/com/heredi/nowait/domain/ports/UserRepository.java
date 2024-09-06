package com.heredi.nowait.domain.ports;

import com.heredi.nowait.domain.model.Users;

public interface UserRepository {
    // Crear un nuevo usuario
    Users createUser(Users user);

    // Obtener un usuario por email/nickname y password para login
    Users getUser(String identifier, String password);
}
