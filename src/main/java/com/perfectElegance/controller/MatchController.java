package com.perfectElegance.controller;

import com.perfectElegance.Dto.PartnerDto;
import com.perfectElegance.modal.Partner;
import com.perfectElegance.service.PartnerService;
import com.perfectElegance.service.UserDetailsServiceIMPL;
import com.perfectElegance.utils.MatchResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class MatchController {

    private final PartnerService partnerService;
    private final UserDetailsServiceIMPL userService;



    @GetMapping("/findMatch")
    public ResponseEntity<MatchResponse> getMatches(@RequestParam Integer userId) {
        boolean isSubscribed = userService.isUserSubscribed(userId);
        List<PartnerDto> matches = partnerService.findMatchScore(userId);
        MatchResponse response = new MatchResponse(matches, isSubscribed);
        return ResponseEntity.ok(response);
    }


}
