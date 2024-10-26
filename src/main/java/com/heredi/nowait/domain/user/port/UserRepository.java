package com.heredi.nowait.domain.user.port;

import com.heredi.nowait.domain.user.model.Users;

public interface UserRepository {
    Users getUserById(Long userId);

    Users createUser(Users user);

    void updateUser(Users user);

    Users getUser(String nickName, String password);

    void getUserByNickNameAndEmail(String nickName, String email);

    Users getUserFromIdAndNickName(Long userId, String nickName);

    void saveUUID(String randomUUID, Long userId);
}
