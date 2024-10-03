package com.heredi.nowait.infrastructure.model.user.adapter;

import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import com.heredi.nowait.infrastructure.jwt.JwtProvider;
import com.heredi.nowait.infrastructure.model.user.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtProvider jwtProvider;

    @Autowired
    private UserEntityMapper userEntityMapper;

    public UserRepositoryImpl(@Lazy UserJPARepository userJPARepository, JwtProvider jwtProvider) {
        this.userJPARepository = userJPARepository;
        this.jwtProvider = jwtProvider;
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
    public String getToken(Users user) {
        String token;
        try{
            token = jwtProvider.generateToken(user.getId(), user.getNickName());
        } catch (Exception e){
            System.out.println("El error es el siguiente ================= " + e);
            return null;
        }
        return token;
    }

    @Override
    public String getRefreshToken(String nickName, String password) {
        UserEntity userEntity = userJPARepository.findByNickName(nickName).
                filter(user -> user.getPassword().equals(password)).
                orElseThrow(() -> new NoSuchElementException("User not found"));
        String refreshToken;
        try{
            refreshToken = jwtProvider.generateRefreshToken();
            String claimFromRefreshToken = jwtProvider.extractRandomUUID(refreshToken);
            userEntity.setRefreshToken(claimFromRefreshToken);
            userJPARepository.save(userEntity);
        }catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return refreshToken;
    }
}
