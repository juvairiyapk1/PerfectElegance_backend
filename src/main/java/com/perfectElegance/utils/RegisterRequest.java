package com.perfectElegance.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.perfectElegance.modal.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class RegisterRequest {


    private boolean isVerified;
    private String createProfileFor;
    private String name;
    private Long phoneNumber;
    private String gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private String DOB;

    private String relegion;
    private String nationality;
    private String email;
    private String password;
    private Integer height;
    private Integer weight;
    private String maritalStatus;
    private String skinTone;
    private String bodyType;
    private String physicaleStatus;
    private String education;
    private String profession;
    private String homeLocation;
    private String currentLocation;
    private String motherTongue;
    private String eatingHabits;
    private String financialStatus;
    private Long primaryNumber;
    private Long secondryNumber;
    private String preferredContactType;
    private String contactPersonAndRelationship;
    private String convenientTimeToCall;
    private Role role;
}
