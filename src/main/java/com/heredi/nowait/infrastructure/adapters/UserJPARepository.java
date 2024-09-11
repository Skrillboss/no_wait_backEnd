package com.heredi.nowait.infrastructure.adapters;

import com.heredi.nowait.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNickName(String nickName);
    UserEntity findByRefreshToken(String refreshToken);
}
