package com.perfectElegance.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String paymentId;
    private String packageName;
    private String currency;
    private long amount;
    private String paymentMethod;

    @Temporal(TemporalType.DATE)
    private Date startingTime;

    @Temporal(TemporalType.DATE)
    private Date expirationTime;

    @OneToOne
    private User user;
}