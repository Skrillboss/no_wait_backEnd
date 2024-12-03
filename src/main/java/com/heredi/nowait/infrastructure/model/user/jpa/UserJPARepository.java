package com.heredi.nowait.infrastructure.model.user.jpa;

import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);

    boolean existsByNickNameAndIdNot(String nickName, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

    Optional<UserEntity> findByNickName(String nickName);
    Optional<UserEntity> findByRefreshUUID(String refreshToken);
}
