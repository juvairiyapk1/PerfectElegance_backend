package com.perfectElegance.Dto;

import com.perfectElegance.modal.User;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
public class ProfileByUserDto {
    private boolean willingToRelocate;
    private String languagesKnown;
    private String educationInstitute;
    private String eyeColor;
    private String bloodGroup;
    private String familyType;
    private String homeType;
    private String livingSituation;
    private String marriagePlans;
    private String hairColor;
    private String hairType;
    private String image;
    private String name;
    private Date dob;
    private String education;
    private String homeLocation;
    private String relegion;
    private String gender;
    private String profession;
    private Integer height;
    private Integer weight;
    private String skinTone;
    private String bodyType;
    private String presentLocation;
    private String createProfileFor;
    private Long phoneNumber;

    @Temporal(TemporalType.DATE)
    private Date DOB;

    private String nationality;

    private String email;
    private String maritalStatus;
    private String physicaleStatus;
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

































}
