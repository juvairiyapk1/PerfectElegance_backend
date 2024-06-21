package com.perfectElegance.modal;

import jakarta.persistence.*;
import lombok.Data;

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

    @OneToOne
    private User user;


}
