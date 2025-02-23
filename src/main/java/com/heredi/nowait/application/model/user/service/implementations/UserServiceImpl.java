package com.heredi.nowait.application.model.user.service.implementations;

import com.heredi.nowait.application.auth.AuthService;
import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.application.model.email.dto.EmailDTO;
import com.heredi.nowait.application.model.email.service.interfaces.MailSenderService;
import com.heredi.nowait.application.model.user.dto.in.CreateUserRequestDTO;
import com.heredi.nowait.application.model.user.dto.in.UpdateUserRequestDTO;
import com.heredi.nowait.application.model.user.dto.out.LoginUserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.UpdatedUserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.UserResponseDTO;
import com.heredi.nowait.application.model.user.dto.out.RefreshTokenResponseDTO;
import com.heredi.nowait.application.model.user.mapper.UserMapper;
import com.heredi.nowait.application.model.user.service.interfaces.UserService;
import com.heredi.nowait.application.model.user.service.validation.UserValidationService;
import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserValidationService validationService;

    private final AuthService authService;

    private final MailSenderService mailSenderService;

//    private final PhoneSenderService phoneService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AuthService authService, MailSenderService mailSenderService, UserValidationService validationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authService = authService;
        this.mailSenderService = mailSenderService;
        this.validationService = validationService;
    }

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) throws MessagingException {
        validationService.validateCreateUser(createUserRequestDTO);

        Users createdUser = this.userRepository.createUser(userMapper.toUser(createUserRequestDTO));

        EmailDTO emailDTO = new EmailDTO(
                createUserRequestDTO.getEmail(),
                "NoWait: verificación de correo electronico",
                "",
                createdUser.getNickName(),
                "Logo NoWait",
                new File("logoNoWait.png")
        );
        mailSenderService.sendNewMail("verifyEmail" , emailDTO);
//        phoneService.sendMessage(createdUser.getPhoneNumber(),
//        "TODO: Ya tengo la implementación dia 31/12/2024, débo verificar que funcione
//        me concentrare en el funcionamiento completo de la aplicación pero ya esta implementado
//        con Twilio

        return userMapper.toUserResponseDTO(createdUser);
    }

    @Override
    public UpdatedUserResponseDTO updateUser(Long userId, UpdateUserRequestDTO updateUserRequestDTO) {

        Users user = new Users();

        user.setId(userId);
        user.setName(updateUserRequestDTO.getName());
        user.setNickName(updateUserRequestDTO.getNickName());
        user.setEmail(updateUserRequestDTO.getEmail());
        user.setPhoneNumber(updateUserRequestDTO.getPhoneNumber());

        this.userRepository.updateUser(user);

        UpdatedUserResponseDTO responseDTO = new UpdatedUserResponseDTO();

        responseDTO.setUpdateUserRequestDTO(updateUserRequestDTO);
        responseDTO.setToken(this.authService.generateToken(userId, user.getNickName()));

        return responseDTO;
    }

    @Override
    public void verifyNickNameAndEmail(String nickName, String email) {
        this.userRepository.getUserByNickNameAndEmail(nickName, email);
    }

    @Transactional
    @Override
    public LoginUserResponseDTO loginUser(String nickName, String password) {
        Users obtainedUser = this.userRepository.getUser(nickName, password);
        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(obtainedUser);
        String accessToken = authService.generateToken(obtainedUser.getId(), obtainedUser.getNickName());
        String refreshToken = authService.generateRefreshToken();
        String randomUUID = authService.extractRandomUUID(refreshToken);
        userRepository.saveUUID(randomUUID, obtainedUser.getId());
        return new LoginUserResponseDTO(userResponseDTO, accessToken, refreshToken);
    }

    @Transactional
    @Override
    public UserResponseDTO loginUserWithToken(String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        Long userId = authService.extractUserId(accessToken);
        String nickName = authService.extractUsername(accessToken);
        Users obtainedUser = this.userRepository.getUserFromIdAndNickName(userId, nickName);
        return userMapper.toUserResponseDTO(obtainedUser);
    }

    @Transactional
    @Override
    public RefreshTokenResponseDTO refreshTokens(String authorizationHeader, String accessToken) {
        if (!authService.isExpiredToken(accessToken)) {
            throw new AppException(
                    AppErrorCode.TOKEN_NOT_EXPIRED_YET,
                    "refreshTokens",
                    "accessToken: " + accessToken,
                    HttpStatus.BAD_REQUEST);
        }
        Users obtainedUser = this.userRepository.getUserFromIdAndNickName(
                authService.extractUserId(accessToken),
                authService.extractUsername(accessToken));
        String refreshToken = authorizationHeader.replace("Bearer ", "");

        if (!authService.validateRefreshToken(refreshToken, obtainedUser.getRefreshUUID())) {
            throw new AppException(
                    AppErrorCode.INVALID_REFRESH_TOKEN,
                    "refreshTokens",
                    "refreshToken: " + refreshToken,
                    HttpStatus.UNAUTHORIZED);
        }
        String newRefreshToken = authService.generateRefreshToken();
        String newRandomUUID = authService.extractRandomUUID(newRefreshToken);
        userRepository.saveUUID(newRandomUUID, obtainedUser.getId());
        return new RefreshTokenResponseDTO(authService.generateToken(obtainedUser.getId(),
                obtainedUser.getNickName()), newRefreshToken);
    }
}
