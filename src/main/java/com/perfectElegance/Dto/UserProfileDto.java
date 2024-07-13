package com.perfectElegance.Dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserProfileDto {

    private boolean isVerified;
    private boolean blocked;
    private String createProfileFor;
    private String name;
    private Long phoneNumber;
    private String gender;


    private Date DOB;

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
    private String residentialStatus;


    private boolean isSubscribed = false;

    private Integer subscriptionId;

    private LocalDateTime subscriptionEndDate;
}
