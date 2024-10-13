package com.heredi.nowait.infrastructure.model.user.jpa;

import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByNickName(String nickName);
    Optional<UserEntity> findByRefreshToken(String refreshToken);
}
