package com.perfectElegance.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime subscriptionStartDate;

    private LocalDateTime subscriptionEndDate;

    private String status;

    private String stripeSubscriptionId;

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", subscriptionStartDate=" + subscriptionStartDate +
                ", subscriptionEndDate=" + subscriptionEndDate +
                ", status='" + status + '\'' +
                ", stripeSubscriptionId='" + stripeSubscriptionId + '\'' +
                '}';
    }
}