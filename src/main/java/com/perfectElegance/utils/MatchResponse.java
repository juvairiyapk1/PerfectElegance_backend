package com.perfectElegance.utils;

import com.perfectElegance.Dto.PartnerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponse {
    private List<PartnerDto> matches;
    private boolean isSubscribed;
}
