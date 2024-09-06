package com.heredi.nowait.infrastructure.adapters;
import com.heredi.nowait.domain.model.Users;
import com.heredi.nowait.domain.ports.UserRepository;
import com.heredi.nowait.infrastructure.entity.UserEntity;
import com.heredi.nowait.infrastructure.rest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class UserJPARepositoryImpl  implements UserRepository {

    private final UserJPARepository userJPARepository;

    @Autowired
    private UserMapper userMapper;

    public UserJPARepositoryImpl(@Lazy UserJPARepository userJPARepository){
        this.userJPARepository = userJPARepository;
    }

    @Override
    public Users createUser(Users user) {
        UserEntity userEntity = this.userMapper.toUserEntity(user);
        return this.userMapper.toUser(this.userJPARepository.save(userEntity));
    }

    @Override
    public Users getUser(String identifier, String password) {
        return this.userMapper.toUser(this.userJPARepository.findByNameAndEmail(identifier, password));
    }
}
