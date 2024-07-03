package com.perfectElegance.modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Packages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String packageName;
    private String duration;
    private Integer price;

    @OneToOne
    private User user;


}
