package com.heredi.nowait.infrastructure.model.user.adapter;

import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import com.heredi.nowait.infrastructure.model.authority.authority.AuthorityEntity;
import com.heredi.nowait.infrastructure.model.paymentInfo.entity.PaymentInfoEntity;
import com.heredi.nowait.infrastructure.model.paymentInfo.jpa.PaymentInfoJPARepository;
import com.heredi.nowait.infrastructure.model.paymentInfo.mapper.PaymentInfoEntityMapper;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import com.heredi.nowait.infrastructure.model.user.mapper.UserEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJPARepository userJPARepository;

    private final PaymentInfoJPARepository paymentInfoJPARepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private PaymentInfoEntityMapper paymentInfoEntityMapper;

    public UserRepositoryImpl(@Lazy UserJPARepository userJPARepository, PaymentInfoJPARepository paymentInfoJPARepository) {
        this.userJPARepository = userJPARepository;
        this.paymentInfoJPARepository = paymentInfoJPARepository;
    }

    @Override
    public Users getUserById(Long userId) {
        return this.userEntityMapper.toUser(this.userJPARepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found")));
    }

    @Override
    public Users createUser(Users user) {
        validateUniqueFields(user);

        UserEntity userEntity = this.userEntityMapper.toUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.getAuthorityEntity().setStatus(AuthorityEntity.UserStatus.EMAIL_UNVERIFIED);
        UserEntity savedUserEntity = this.userJPARepository.save(userEntity);

        List<PaymentInfoEntity> paymentInfoEntityList = user.getPaymentInfoList().stream()
                .map(paymentInfo -> {
                    PaymentInfoEntity paymentInfoCollected = this.paymentInfoEntityMapper.toPaymentInfoEntity(paymentInfo);
                    paymentInfoCollected.setUserEntity(savedUserEntity); // Asigna el usuario ya persistido.
                    return this.paymentInfoJPARepository.save(paymentInfoCollected);
                })
                .toList();

        savedUserEntity.setPaymentInfoEntityList(paymentInfoEntityList);

        return this.userEntityMapper.toUser(savedUserEntity);
    }

    @Override
    public void updateUser(Users user) {
        validateUniqueFields(user);

        UserEntity userEntity = userJPARepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        userEntity.setName(user.getName());
        userEntity.setNickName(user.getNickName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhoneNumber(user.getPhoneNumber());

        userJPARepository.save(userEntity);
    }

    private void validateUniqueFields(Users user) {
        List<AppErrorCode> errorCodes = new ArrayList<>();
        StringBuilder errorDescription = new StringBuilder();

        addValidationErrorIfExists(user.getNickName(), this.userJPARepository::existsByNickName,
                AppErrorCode.NICKNAME_ALREADY_EXIST, "NickName from User trying to register already exists",
                errorCodes, errorDescription);

        addValidationErrorIfExists(user.getEmail(), this.userJPARepository::existsByEmail,
                AppErrorCode.EMAIL_ALREADY_EXIST, "Email from User trying to register already exists",
                errorCodes, errorDescription);

        addValidationErrorIfExists(user.getPhoneNumber(), this.userJPARepository::existsByPhoneNumber,
                AppErrorCode.PHONE_NUMBER_ALREADY_EXIST, "PhoneNumber from User trying to register already exists",
                errorCodes, errorDescription);

        if (!errorCodes.isEmpty()) {
            throw new AppException(
                    errorCodes,
                    errorDescription.toString(),
                    HttpStatus.CONFLICT
            );
        }
    }

    private void addValidationErrorIfExists(String field, Function<String, Boolean> existsFunction,
                                            AppErrorCode errorCode, String errorMessage,
                                            List<AppErrorCode> errorCodes, StringBuilder errorDescription) {
        if (existsFunction.apply(field)) {
            errorCodes.add(errorCode);
            errorDescription.append("\n").append(errorMessage);
        }
    }


    @Override
    public Users getUser(String nickName, String password) {
        UserEntity userEntity = this.userJPARepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new NoSuchElementException("Invalid password");
        }
        userEntity.setPaymentInfoEntityList(this.paymentInfoJPARepository.findByUserEntityId(userEntity.getId()));

        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public void getUserByNickNameAndEmail(String nickName, String email) {

        UserEntity userEntity = this.userJPARepository.findByNickName(nickName)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if(email.equals(userEntity.getEmail())){
            userEntity.getAuthorityEntity().setStatus(AuthorityEntity.UserStatus.ACTIVE);
            userJPARepository.save(userEntity);
        }
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
}
