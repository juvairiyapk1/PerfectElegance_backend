package com.perfectElegance.Dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyRevenueDto {
    private List<String> months;
    private List<Double> revenue;

}
