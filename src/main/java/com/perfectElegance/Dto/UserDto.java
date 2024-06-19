package com.perfectElegance.Dto;

import com.perfectElegance.modal.Role;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String name;
    private Long phoneNumber;
    private String email;
//    private String gender;
    private boolean blocked;

    private Role role;
}
