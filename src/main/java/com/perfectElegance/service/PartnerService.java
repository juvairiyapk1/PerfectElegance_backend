package com.perfectElegance.service;

import com.perfectElegance.Dto.PartnerDto;
import com.perfectElegance.modal.Partner;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.PartnerRepository;
import com.perfectElegance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PartnerService {

    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;

    public List<PartnerDto> findMatchScore(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("user is not found"));

        Partner userPreferences = user.getPartner();
        String oppositeGender = user.getGender().equalsIgnoreCase("male") ? "female" : "male";

        List<Partner> allPartners =partnerRepository.findAll();
        System.out.println(allPartners);
        return allPartners.stream()
                .filter(partner -> partner.getUser().getGender().equalsIgnoreCase(oppositeGender))
                .filter(partner -> partner.getReligion().equals(userPreferences.getReligion())) // Filter by religion
                .map(partner -> {
                    Double score = calculateMatchScore(userPreferences,partner);
                    return new PartnerDto(partner,score);
                })
                .filter(partner -> partner.getMatchScore() >0.5)
                .sorted(Comparator.comparing(PartnerDto::getMatchScore).reversed())
                .collect(Collectors.toList());

    }

    private double calculateMatchScore(Partner preferences, Partner candidate) {

        int points = 0;
        int totalPoint =  140;

        if (preferences.getHeight().equals(candidate.getHeight())) points += 10;
        if (preferences.getPhysicalStatus().equals(candidate.getPhysicalStatus())) points += 10;
        if (preferences.isDrinkingHabits() == candidate.isDrinkingHabits()) points += 10;
        if (preferences.getAppearance().equals(candidate.getAppearance())) points += 10;
        if (preferences.getEducation().equals(candidate.getEducation())) points += 10;
        if (preferences.getMaritalStatus().equals(candidate.getMaritalStatus())) points += 10;
        if (preferences.getComplexion().equals(candidate.getComplexion())) points += 5;
        if (preferences.getLanguagesSpoken().equals(candidate.getLanguagesSpoken())) points += 5;
        if (preferences.getReligion().equals(candidate.getReligion())) points += 15;
        if (isAgeInRange(preferences.getAge(), candidate.getAge())) points += 15;
        if (preferences.getMotherTongue().equals(candidate.getMotherTongue())) points += 10;
        if (preferences.getProfession().equals(candidate.getProfession())) points += 10;
        if (preferences.getCountry().equals(candidate.getCountry())) points += 10;
        if (preferences.getCity().equals(candidate.getCity())) points += 10;

        return Math.round((double) points/totalPoint * 10 * 10.0)/10.0;
    }

    private boolean isAgeInRange(String preferredAge, String candidateAge) {
        int preferred = Integer.parseInt(preferredAge);
        int candidate = Integer.parseInt(candidateAge);
        return Math.abs(preferred - candidate) <= 5; // 5 year range
    }
}
