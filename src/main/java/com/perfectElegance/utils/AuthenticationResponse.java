package com.perfectElegance.utils;

import com.perfectElegance.modal.Role;
import java.util.Date;


public class AuthenticationResponse {

    private Integer id;
//   private String token;
    private String name;
    private String email;
    private Role role;
    private Date expirationTime;

    public AuthenticationResponse(Integer id, String name, String email, Role role,Date expirationTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.expirationTime= expirationTime;
    }


//    public String getToken() {
//        return token;
//    }



    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public Role getRole(){
        return role;
    }

    public Integer getId() {
        return id;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }
}
