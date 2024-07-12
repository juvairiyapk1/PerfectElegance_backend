package com.perfectElegance.Dto;

import com.stripe.param.PlanCreateParams;
import lombok.Data;

@Data
public class PlanRequest {

    private Long amount;
    private String currency;
    private PlanCreateParams.Interval interval;
    private String product;
}
