package com.perfectElegance.Dto;

import com.perfectElegance.modal.Role;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private Integer id;
    private boolean isVerified;
    private boolean blocked;
    private String name;
    private Long phoneNumber;
    private String email;
    private Role role;


}
