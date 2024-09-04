package com.heredi.nowait.presentation.user.dto;

public class UserResponse {
    private UserDTO user;

    public UserResponse(UserDTO userDTO) {
        this.user = userDTO;
    }

    // Getter y setter
    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
