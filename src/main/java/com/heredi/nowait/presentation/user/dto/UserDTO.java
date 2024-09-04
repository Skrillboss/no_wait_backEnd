package com.heredi.nowait.presentation.user.dto;

import com.heredi.nowait.domain.entities.User;

public class UserDTO {
    private String id;
    private String name;
    private String nickName;
    private String email;
    private String phoneNumber;

    // Constructor
    public UserDTO(User user) {
        this.id = user.getId().toString();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
