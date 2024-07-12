package com.perfectElegance.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean willingToRelocate;
    private String languagesKnown;
    private String educationInstitute;
    private String eyeColor;
    private String bloodGroup;
    private String familyType;
    private String homeType;
    private String physicalStatus;
    private String livingSituation;
    private String marriagePlans;
    private String hairColor;
    private String hairType;
    private String image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private User user;


    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "partner_id")
    private Partner partner;



}
