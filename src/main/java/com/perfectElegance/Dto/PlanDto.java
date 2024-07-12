package com.perfectElegance.Dto;

import com.stripe.param.PlanCreateParams;
import lombok.Data;

@Data
public class PlanDto {
    private String packageName;
    private String duration;
    private Long price;
    private String product;
    private String priceId;
}
