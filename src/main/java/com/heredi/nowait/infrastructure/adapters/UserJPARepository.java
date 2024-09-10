package com.heredi.nowait.infrastructure.adapters;

import com.heredi.nowait.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByNickNameAndPassword(String name, String password);
}
