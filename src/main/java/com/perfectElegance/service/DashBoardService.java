package com.perfectElegance.service;

import com.perfectElegance.Dto.MonthlyRevenueDto;
import com.perfectElegance.Dto.MonthlyUserSubscriptionDTO;
import com.perfectElegance.repository.SubscriptionRepository;
import com.perfectElegance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashBoardService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    public Long getUserCount() {
        return userRepository.count();
    }

    public Long getBlockedUserCount() {
        return userRepository.countByBlocked(true);
    }

    public Long getSubscribersCount() {
        return userRepository.countByIsSubscribed(true);
    }

    public Long getUnsubscribersCount() {
        return userRepository.countByIsSubscribed(false);
    }

    public MonthlyUserSubscriptionDTO getMonthlyUserAndSubscriptionData() {
        System.out.println("inside the dashboard service");
        MonthlyUserSubscriptionDTO dto = new MonthlyUserSubscriptionDTO();
        List<String> months = new ArrayList<>();
        List<Long> users = new ArrayList<>();
        List<Long> subscriptions = new ArrayList<>();

        LocalDate now = LocalDate.now();
        for (int i = 11; i >=0 ; i--) {
          LocalDate date = now.minusMonths(i);
          String monthYear = date.format(DateTimeFormatter.ofPattern("MMM yyyy"));
          months.add(monthYear);

          LocalDateTime startOfMonth = date.withDayOfMonth(1).atStartOfDay();
          LocalDateTime endOfMonth = date.withDayOfMonth(date.lengthOfMonth()).atStartOfDay();

            long userCount = userRepository.countByCreatedAtBetween(startOfMonth, endOfMonth);
            long subscriptionCount = subscriptionRepository.countBySubscriptionStartDateBetween(startOfMonth, endOfMonth);
            users.add(userCount);
            subscriptions.add(subscriptionCount);
        }
        dto.setMonths(months);
        dto.setUsers(users);
        dto.setSubscriptions(subscriptions);
        System.out.println(dto);
        return dto;

    }


    public MonthlyRevenueDto getMonthlySubscriptionRevenue() {
        MonthlyRevenueDto dto = new MonthlyRevenueDto();
        List<String> months = new ArrayList<>();
        List<Double> revenue = new ArrayList<>();

        LocalDate now = LocalDate.now();
        for (int i = 11; i >= 0; i--) {
            LocalDate date = now.minusMonths(i);
            String monthYear = date.format(DateTimeFormatter.ofPattern("MMM yyyy"));
            months.add(monthYear);

            LocalDateTime startOfMonth = date.withDayOfMonth(1).atStartOfDay();
            LocalDateTime endOfMonth = date.withDayOfMonth(date.lengthOfMonth()).atTime(LocalTime.MAX);

            Double monthlyRevenue = subscriptionRepository.calculateRevenueForPeriod(startOfMonth, endOfMonth);
            revenue.add(monthlyRevenue);
        }

        dto.setMonths(months);
        dto.setRevenue(revenue);
        return dto;
    }
}
