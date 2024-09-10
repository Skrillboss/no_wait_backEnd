package com.heredi.nowait.infrastructure.adapters;
import com.heredi.nowait.domain.model.Users;
import com.heredi.nowait.domain.ports.UserRepository;
import com.heredi.nowait.infrastructure.entity.UserEntity;
import com.heredi.nowait.infrastructure.jwt.JwtProvider;
import com.heredi.nowait.infrastructure.rest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserJPARepositoryImpl  implements UserRepository {

    private final UserJPARepository userJPARepository;

    @Autowired
    private final JwtProvider jwtProvider;

    @Autowired
    private UserMapper userMapper;

    public UserJPARepositoryImpl(@Lazy UserJPARepository userJPARepository, JwtProvider jwtProvider){
        this.userJPARepository = userJPARepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Users createUser(Users user) {
        UserEntity userEntity = this.userMapper.toUserEntity(user);
        return this.userMapper.toUser(this.userJPARepository.save(userEntity));
    }

    @Override
    public Users getUser(String nickName, String password) {
        return this.userMapper.toUser(this.userJPARepository.findByNickNameAndPassword(nickName, password));
    }

    @Override
    public String getToken(Users user) {
        return jwtProvider.generateToken(user.getId().toString(), user.getNickName());
    }

    @Override
    public String getRefreshToken(String nickName, String password) {
        UserEntity userEntity = userJPARepository.findByNickNameAndPassword(nickName, password);
            String refreshToken = jwtProvider.generateRefreshToken();
            userEntity.setRefreshToken(refreshToken);
            userJPARepository.save(userEntity);
            return refreshToken;
    }
}
