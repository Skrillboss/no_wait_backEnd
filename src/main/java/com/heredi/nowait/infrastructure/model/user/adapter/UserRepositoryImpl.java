package com.heredi.nowait.infrastructure.model.user.adapter;

import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import com.heredi.nowait.infrastructure.model.role.entity.RoleEntity;
import com.heredi.nowait.infrastructure.model.shift.entity.ShiftEntity;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import com.heredi.nowait.infrastructure.model.user.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public Users getUserById(Long userId) {
        return this.userEntityMapper.toUser(this.userJPARepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found")));
    }

    @Override
    public Users createUser(Users user) {
        if(this.userJPARepository.existsByNickName(user.getNickName())){
            throw new IllegalArgumentException("NickName from User try to register already exist");
        }
        if(this.userJPARepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email from User try to register already exist");
        }
        if(this.userJPARepository.existsByPhoneNumber(user.getPhoneNumber())){
            throw new IllegalArgumentException("PhoneNumber from User try to register already exist");
        }
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
        if (!userEntity.getId().equals(userId)) {
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

    //no estoy actualizando all el usuario
    @Override
    public void updateUser(Users user) {
        UserEntity userEntity = userJPARepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        userEntity.setRefreshToken(user.getRefreshToken());
        userEntity.setName(user.getName());
        userEntity.setNickName(user.getNickName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setPhoneNumber(user.getPhoneNumber());

        if (user.getShifts() != null) {
            List<ShiftEntity> shiftEntities = user.getShifts().stream()
                    .map(shift -> {
                        // Lógica para convertir Shift a ShiftEntity
                        return new ShiftEntity(); // Reemplaza esto con la conversión real
                    })
                    .collect(Collectors.toList());
            userEntity.setShifts(shiftEntities);
        }

        userJPARepository.save(userEntity);
    }

}
