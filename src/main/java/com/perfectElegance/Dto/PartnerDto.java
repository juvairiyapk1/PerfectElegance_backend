package com.perfectElegance.Dto;


import com.perfectElegance.modal.Partner;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PartnerDto {

    private Partner partner;
    private Double matchScore;


}
