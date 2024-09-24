package com.heredi.nowait.infrastructure.adapters;

import com.heredi.nowait.domain.model.Users;
import com.heredi.nowait.domain.ports.UserRepository;
import com.heredi.nowait.infrastructure.entity.UserEntity;
import com.heredi.nowait.infrastructure.jwt.JwtProvider;
import com.heredi.nowait.infrastructure.rest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.NoSuchElementException;

@Repository
public class UserJPARepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtProvider jwtProvider;

    @Autowired
    private UserMapper userMapper;

    public UserJPARepositoryImpl(@Lazy UserJPARepository userJPARepository, JwtProvider jwtProvider) {
        this.userJPARepository = userJPARepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Users createUser(Users user) {
        UserEntity userEntity = this.userMapper.toUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return this.userMapper.toUser(this.userJPARepository.save(userEntity));
    }

    @Override
    public Users getUser(String nickName, String password) {
        UserEntity userEntity = this.userJPARepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // Compara usando el passwordEncoder
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new NoSuchElementException("Invalid password");
        }

        return userMapper.toUser(userEntity);
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
        //TODO: aqui no tengo que guardar el token completo, sino el claim del token
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
