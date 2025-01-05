package com.heredi.nowait.application.model.user.service.validation;

import com.heredi.nowait.application.exception.AppErrorCode;
import com.heredi.nowait.application.exception.AppException;
import com.heredi.nowait.application.model.user.dto.in.CreateUserRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserValidationService {

    private final List<AppErrorCode> errorCodes = new ArrayList<>();
    private final List<String> details = new ArrayList<>();

    public void validateCreateUser(CreateUserRequestDTO createUser) {
        this.errorCodes.clear();
        this.details.clear();

        if (createUser == null) {
            throw new AppException(
                    AppErrorCode.CREATE_USER_NULL,
                    "validateCreateUser",
                    "The CreateUserRequestDTO object provided in the 'validateCreateUser' method is null",
                    HttpStatus.BAD_REQUEST
            );
        }

        validateName(createUser.getName());
        validateNickName(createUser.getNickName());
        validateEmail(createUser.getEmail());
        validatePassword(createUser.getPassword());
        validatePhoneNumber(createUser.getPhoneNumber());
        validateRoleSpecificInfo(createUser);

        if (!this.errorCodes.isEmpty() || !this.details.isEmpty()) {
            throw new AppException(
                    this.errorCodes,
                    "validateUniqueFields",
                    this.details,
                    HttpStatus.BAD_REQUEST);
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            this.errorCodes.add(AppErrorCode.USERNAME_EMPTY_TO_REGISTER);
            this.details.add("The username provided is empty or void, username:" + name);
        } else if (name.length() < 3 || name.length() > 30) {
            this.errorCodes.add(AppErrorCode.USERNAME_LENGTH_ERROR);
            this.details.add("The username: " + name + ", has a length of " + name.length() +
                    " this length must be between 3 and 30");
        }
    }

    private void validateNickName(String nickName) {
        if (nickName == null || nickName.trim().isEmpty()) {
            this.errorCodes.add(AppErrorCode.NICKNAME_EMPTY_TO_REGISTER);
            this.details.add("The nickName provided is empty or void, nickName:" + nickName);
        } else if (nickName.length() < 8 || nickName.length() > 20) {
            this.errorCodes.add(AppErrorCode.NICKNAME_LENGTH_ERROR);
            this.details.add("The nickName: " + nickName + ", has a length of " + nickName.length() +
                    " this length must be between 8 and 20");
        }
    }

    private void validateEmail(String email) {
        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        if (email == null || email.trim().isEmpty()) {
            this.errorCodes.add(AppErrorCode.USER_EMAIL_EMPTY_TO_REGISTER);
            this.details.add("The email provided is empty or void, email:" + email);
        } else {
            if (email.length() < 6 || email.length() > 254) {
                this.errorCodes.add(AppErrorCode.USER_EMAIL_LENGTH_ERROR);
                this.details.add("The email: " + email + ", has a length of " + email.length() +
                        " this length must be between 6 and 254");
            }
            if (!email.matches(EMAIL_REGEX)) {
                this.errorCodes.add(AppErrorCode.EMAIL_FORMAT_ERROR);
                this.details.add("The email must respect the following REGEX: " + EMAIL_REGEX +
                        ", the email provided was: " + email);
            }
        }
    }

    private void validatePassword(String password) {
        final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$";

        if (password == null || password.trim().isEmpty()) {
            this.errorCodes.add(AppErrorCode.PASSWORD_EMPTY_TO_REGISTER);
            this.details.add("The password provided is empty or void.");
        } else {
            if (password.length() < 8 || password.length() > 50) {
                // TODO: el texto de 8 y 50 debe estar guardado en constantes para igualarse a la validación (texto al usuario)
                this.errorCodes.add(AppErrorCode.PASSWORD_LENGTH_ERROR);
                this.details.add("The password has a length of " + password.length() +
                        ". It must be between 8 and 50 characters.");
            }
            if (!password.matches(PASSWORD_REGEX)) {
                this.errorCodes.add(AppErrorCode.PASSWORD_FORMAT_ERROR);
                this.details.add("The password does not meet the required format. " +
                        "It must contain at least one uppercase letter, one lowercase letter, one number, and one special character.");
            }
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        final String PHONE_REGEX = "^[+]?[0-9]{10,15}$";

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            this.errorCodes.add(AppErrorCode.PHONE_NUMBER_EMPTY_TO_REGISTER);
            this.details.add("The phone number provided is empty or void.");
        } else if (!phoneNumber.matches(PHONE_REGEX)) {
            this.errorCodes.add(AppErrorCode.PHONE_NUMBER_FORMAT_ERROR);
            this.details.add("The phone number provided is invalid. Must follow the pattern: " + PHONE_REGEX +
                    ". Provided phone number: " + phoneNumber);
        }
    }

    private void validateRoleSpecificInfo(CreateUserRequestDTO createUserRequestDTO) {
        if(createUserRequestDTO.getRoleRequestDTO() == null){
            this.errorCodes.add(AppErrorCode.USER_ROLE_NOT_FOUND);
            this.details.add("The roleRequestDTO value which is inside the createUserRequestDTO "+
                    "object given by parameter within the validateRoleSpecificInfo "+
                    "method is null");
        }else{
            boolean isAdmin = "ADMIN".equals(createUserRequestDTO.getRoleRequestDTO().getName());
            boolean hasBusinessInfo = createUserRequestDTO.getBusinessRequestDTO() != null;
            boolean hasPaymentInfo = createUserRequestDTO.getPaymentInfoRequestDTOList() != null
                    && !createUserRequestDTO.getPaymentInfoRequestDTOList().isEmpty();

            if (isAdmin && (!hasBusinessInfo || !hasPaymentInfo)) {
                this.errorCodes.add(AppErrorCode.ADMIN_ROLE_NEEDS_PAYMENT_AND_BUSINESS);
                this.details.add("");
            }
            if (!isAdmin && (hasBusinessInfo || hasPaymentInfo)) {
                this.errorCodes.add(AppErrorCode.ONLY_ADMIN_CAN_RECORD_PAYMENT_OR_BUSINESS);
                this.details.add("");
            }
        }
    }
}