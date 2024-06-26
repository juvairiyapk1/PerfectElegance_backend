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
    private String livingSituation;
    private String fatherDetails;
    private String motherDetails;
    private Integer NoOfElderBro;
    private Integer NoOfElderSis;
    private Integer NoOfYoungerSis;
    private Integer NoOfYoungerBro;
    private Integer NoOfMarriedSis;
    private String image;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private User user;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @OneToOne
    private Subscription subscription;


}
