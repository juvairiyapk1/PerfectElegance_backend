package com.perfectElegance.utils;

import com.perfectElegance.modal.Role;

public class LoginResponse {
    private Integer id;
    private String token;
    private String name;
    private String email;
    private Long phoneNumber;
    private String gender;
    private Role role;
    private boolean logged;
    private boolean blocked;




    public String getToken() {
        return token;
    }

    public LoginResponse(Integer id,String token, String name, String email,Long phoneNumber,  String gender,Role role,boolean logged,boolean blocked) {
        this.id=id;
        this.token = token;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
        this.logged=logged;
        this.blocked = blocked;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public boolean isLogged() {
        return logged;
    }

    public Integer getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public boolean isBlocked() {
        return blocked;
    }
}
