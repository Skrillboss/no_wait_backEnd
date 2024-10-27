package com.heredi.nowait.application.model.user.service.implementations;

import com.heredi.nowait.application.auth.AuthService;
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
import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.domain.user.port.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;

    private final AuthService authService;

    @Autowired
    private MailSenderService mailSenderService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AuthService authService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authService = authService;
    }

    @Override
    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) throws MessagingException {
        validateRoleSpecificInfo(createUserRequestDTO);

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

    private void validateRoleSpecificInfo(CreateUserRequestDTO createUserRequestDTO) {
        boolean isAdmin = "ADMIN".equals(createUserRequestDTO.getRoleRequestDTO().getName());
        boolean hasBusinessInfo = createUserRequestDTO.getBusinessRequestDTO() != null;
        boolean hasPaymentInfo = createUserRequestDTO.getPaymentInfoRequestDTOList() != null;

        if (isAdmin && (!hasBusinessInfo || !hasPaymentInfo)) {
            throw new IllegalArgumentException("'ADMIN' roles need both payment and business information");
        }
        if (!isAdmin && (hasBusinessInfo || hasPaymentInfo)) {
            throw new IllegalArgumentException("Only 'ADMIN' roles can record payment or business information");
        }
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
        if (!authService.isNotExpiredToken(accessToken)) {
            String nickName = authService.extractUsername(accessToken);
            Long userId = authService.extractUserId(accessToken);
            Users obtainedUser = this.userRepository.getUserFromIdAndNickName(userId, nickName);
            String refreshToken = authorizationHeader.replace("Bearer ", "");

            if (obtainedUser.getRefreshToken().equals(authService.extractRandomUUID(refreshToken))) {
                String newRefreshToken = authService.generateRefreshToken();
                String newRandomUUID = authService.extractRandomUUID(newRefreshToken);
                userRepository.saveUUID(newRandomUUID, userId);
                return new RefreshTokenResponseDTO(authService.generateToken(obtainedUser.getId(), obtainedUser.getNickName()), newRefreshToken);
            }
        } else {
            throw new IllegalStateException("The access token has not expired yet.");
        }
        throw new IllegalStateException("Invalid refresh token.");
    }

}
