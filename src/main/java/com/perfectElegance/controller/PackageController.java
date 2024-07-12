package com.perfectElegance.controller;

import com.perfectElegance.Dto.PlanDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Plan;
import com.stripe.model.PlanCollection;
import com.stripe.model.Product;
import com.stripe.param.PlanListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class PackageController {



    @GetMapping("/getPackages")
    public List<PlanDto> getPackages() throws StripeException {
        PlanListParams params = PlanListParams.builder()
                .setLimit(3L)
                .addExpand("data.product")
                .build();
        PlanCollection plans = Plan.list(params);

        return plans.getData().stream()
                .map(plan -> {
                    PlanDto dto = new PlanDto();
                    try {
                        Product product = Product.retrieve(plan.getProduct());
                        dto.setPackageName(product.getName());

                    } catch (StripeException e) {
                        System.out.println("Error fetching product: " + e.getMessage());
                        dto.setPackageName("Unnamed Package");
                    }
                    dto.setDuration(formatInterval(plan.getInterval(), plan.getIntervalCount()));
                    dto.setPrice(plan.getAmount() / 100);
                    dto.setPriceId(plan.getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    private String formatInterval(String interval, Long intervalCount) {
        if (intervalCount == null || intervalCount == 1) {
            return  "Every"+ interval;
        }
        return   intervalCount + " " + interval + "s";
    }
}
