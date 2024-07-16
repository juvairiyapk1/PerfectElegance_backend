package com.perfectElegance.Dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyUserSubscriptionDTO {
    private List<String> months;
    private List<Long> users;
    private List<Long> subscriptions;

}
