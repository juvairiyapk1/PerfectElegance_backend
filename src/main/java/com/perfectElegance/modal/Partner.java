package com.perfectElegance.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer height;
    private String physicalStatus;
    private boolean drinkingHabits;
    private String appearance;
    private String education;
    private String maritalStatus;
    private String complexion;
    private String languagesSpoken;
    private String religion;
    private String age;
    private String motherTongue;
    private String profession;
    private String country;
    private String city;
//    private Double matchScore;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" )
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "partner", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Profile profile;


}
