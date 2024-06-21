package com.perfectElegance.modal;

import jakarta.persistence.*;
import lombok.Data;

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
    private String livingSituation;
    private String fatherDetails;
    private String motherDetails;
    private Integer NoOfElderBro;
    private Integer NoOfElderSis;
    private Integer NoOfYoungerSis;
    private Integer NoOfYoungerBro;
    private Integer NoOfMarriedSis;
    private String image;

    @OneToOne
    private User user;

    @OneToOne
    private Profile profile;

    @OneToOne
    private Subscription subscription;


}
