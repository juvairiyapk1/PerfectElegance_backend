package com.perfectElegance.controller;

import com.perfectElegance.Dto.MonthlyRevenueDto;
import com.perfectElegance.Dto.MonthlyUserSubscriptionDTO;
import com.perfectElegance.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getUserCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("totalUsers", dashBoardService.getUserCount());
        counts.put("blockedUsers", dashBoardService.getBlockedUserCount());
        counts.put("subscribers", dashBoardService.getSubscribersCount());
        counts.put("unsubscribers", dashBoardService.getUnsubscribersCount());
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/user-subscription")
    public ResponseEntity<MonthlyUserSubscriptionDTO> getUserSubscriptionData() {
        System.out.println("inside the dashboard chart");
        MonthlyUserSubscriptionDTO data = dashBoardService.getMonthlyUserAndSubscriptionData();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/monthly")
    public ResponseEntity<MonthlyRevenueDto> getMonthlyRevenue() {
        return ResponseEntity.ok(dashBoardService.getMonthlySubscriptionRevenue());
    }

}
