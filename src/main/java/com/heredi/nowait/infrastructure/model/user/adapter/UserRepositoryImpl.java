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

        if(user.getPaymentInfoList() != null && !(user.getPaymentInfoList().isEmpty())){
            List<PaymentInfoEntity> paymentInfoEntityList = user.getPaymentInfoList().stream()
                    .map(paymentInfo -> {
                        PaymentInfoEntity paymentInfoCollected = this.paymentInfoEntityMapper.toPaymentInfoEntity(paymentInfo);
                        paymentInfoCollected.setUserEntity(savedUserEntity);
                        return this.paymentInfoJPARepository.save(paymentInfoCollected);
                    })
                    .toList();
            savedUserEntity.setPaymentInfoEntityList(paymentInfoEntityList);
        }
        return this.userEntityMapper.toUser(savedUserEntity);
    }

    @Override
    public void updateUser(Users user) {
        validateUniqueFields(user);

        UserEntity userEntity = userJPARepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));


        if(user.getNickName() != null) userEntity.setNickName(user.getNickName());
        if(user.getEmail() != null) userEntity.setEmail(user.getEmail());
        if(user.getPhoneNumber() != null) userEntity.setPhoneNumber(user.getPhoneNumber());

        userJPARepository.save(userEntity);
    }

    private void validateUniqueFields(Users user) {
        List<AppErrorCode> errorCodes = new ArrayList<>();
        List<String> details = new ArrayList<>();

        //TODO: mejorar este condicional
        if (user.getId() == null) {
            //en esta condición entraran solo cuando se esta haciendo uso de createUser
            //debido a que aun no se habrá generado previamente un ID
            if (userJPARepository.existsByNickName(user.getNickName())) {
                errorCodes.add(AppErrorCode.NICKNAME_ALREADY_EXIST);
                details.add("nickName: " + user.getNickName());
            }
            if (userJPARepository.existsByEmail(user.getEmail())) {
                errorCodes.add(AppErrorCode.EMAIL_ALREADY_EXIST);
                details.add("email: " + user.getEmail());
            }
            if (userJPARepository.existsByPhoneNumber(user.getPhoneNumber())) {
                errorCodes.add(AppErrorCode.PHONE_NUMBER_ALREADY_EXIST);
                details.add("phoneNumber: " + user.getPhoneNumber());
            }
            //en este else entraran los que quieran actualizar los datos del usuario, ya que
            //comprobara si los datos que intenta actualizar ya existen a excepción de los que
            //el ya tiene.
        } else {
            if (userJPARepository.existsByNickNameAndIdNot(user.getNickName(), user.getId())) {
                errorCodes.add(AppErrorCode.NICKNAME_ALREADY_EXIST);
            }
            if (userJPARepository.existsByEmailAndIdNot(user.getEmail(), user.getId())) {
                errorCodes.add(AppErrorCode.EMAIL_ALREADY_EXIST);
            }
            if (userJPARepository.existsByPhoneNumberAndIdNot(user.getPhoneNumber(), user.getId())) {
                errorCodes.add(AppErrorCode.PHONE_NUMBER_ALREADY_EXIST);
            }
        }

        if (!errorCodes.isEmpty()) {
            throw new AppException(
                    errorCodes,
                    "validateUniqueFields",
                    details,
                    HttpStatus.CONFLICT);
        }
    }

    @Override
    public Users getUser(String nickName, String password) {
        UserEntity userEntity = this.userJPARepository.findByNickName(nickName)
                .orElseThrow(() -> new AppException(
                        AppErrorCode.USER_NOT_FOUND_BY_NICK_NAME,
                        "getUser",
                        "nickName: " + nickName,
                        HttpStatus.NOT_FOUND
                ));

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new AppException(
                    AppErrorCode.INCORRECT_PASSWORD,
                    "getUser",
                    "nickName: " + nickName,
                    HttpStatus.FORBIDDEN
            );
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
                .orElseThrow(() -> new AppException(
                        AppErrorCode.USER_NOT_FOUND_BY_NICK_NAME,
                        "getUser",
                        "nickName: " + nickName,
                        HttpStatus.NOT_FOUND
                ));

        if (!userEntity.getId().equals(userId)) {
            throw new AppException(
                    AppErrorCode.USER_NICKNAME_DOES_NOT_CORRESPOND_TO_ID,
                    "getUserFromIdAndNickName",
                    "userId: " + userId + " nickName: " + nickName,
                    HttpStatus.BAD_REQUEST
            );
        }

        return userEntityMapper.toUser(userEntity);
    }

    @Override
    public void saveUUID(String randomUUID, Long userId) {
        UserEntity userEntity = userJPARepository.findById(userId).
                orElseThrow(() -> new AppException(
                        AppErrorCode.USER_NOT_FOUND_BY_ID,
                        "saveUUID",
                        "userId: " + userId,
                        HttpStatus.BAD_REQUEST
                ));
        userEntity.setRefreshUUID(randomUUID);
        userJPARepository.save(userEntity);
    }
}
