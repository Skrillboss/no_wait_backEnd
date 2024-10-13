package com.heredi.nowait.infrastructure.model.user.adapter;

import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import com.heredi.nowait.infrastructure.model.user.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserEntityMapper userEntityMapper;

    public UserRepositoryImpl(@Lazy UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    public Users createUser(Users user) {
        UserEntity userEntity = this.userEntityMapper.toUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return this.userEntityMapper.toUser(this.userJPARepository.save(userEntity));
    }

    @Override
    public Users getUser(String nickName, String password) {
        UserEntity userEntity = this.userJPARepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Compara usando el passwordEncoder
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new NoSuchElementException("Invalid password");
        }

        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public Users getUserFromIdAndNickName(Long userId, String nickName) {
        UserEntity userEntity = this.userJPARepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Compara con Id
        if(!userEntity.getId().equals(userId)){
            throw new NoSuchElementException("Invalid Id");
        }

        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public void saveUUID(String randomUUID, Long userId) {
        UserEntity userEntity = userJPARepository.findById(userId).
                orElseThrow(() -> new NoSuchElementException("User not found"));
            userEntity.setRefreshToken(randomUUID);
            userJPARepository.save(userEntity);
    }

    @Override
    public void updateUser(Users user) {
        UserEntity userEntity = userEntityMapper.toUserEntity(user);
        userJPARepository.save(userEntity);
    }
}
