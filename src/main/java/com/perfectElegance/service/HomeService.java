package com.perfectElegance.service;

import com.perfectElegance.Dto.HomeDto;
import com.perfectElegance.Dto.UserDto;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private UserRepository userRepository;


    public Integer getUserIdByEmail(String email){
        User user=userRepository.findByEmail(email).get();
        return user != null ? user.getId() : null;

    }
    public List<HomeDto> findAllExceptAdminAndLoggedInUser(Integer loggedInUserId,String gender,String loggedInUserReligion){
        List<User> users= userRepository.findAllExceptAdminAndLoggedInUserAndBlocked(loggedInUserId,gender);

        return users.stream()
                .filter(user -> !user.getRelegion().equals(loggedInUserReligion))
                .map(user -> {
                    HomeDto homeDto = new HomeDto();
                    homeDto.setName(user.getName());
                    homeDto.setDOB(user.getDOB());
                    homeDto.setRelegion(user.getRelegion());
                    homeDto.setHomeLocation(user.getHomeLocation());
                    homeDto.setEducation(user.getEducation());
                    homeDto.setProfession(user.getProfession());
                    if (user.getProfile() != null) {
                        homeDto.setImage(user.getProfile().getImage());
                    }
                    return homeDto;
                })
                .collect(Collectors.toList());
    }

    public String getUserGenderByEmail(String loggedInEmail) {
        User user=userRepository.findByEmail(loggedInEmail).get();
        return user.getGender();
    }

    private boolean matchesDOB(User user, Date minDOB, Date maxDOB) {
        if (minDOB == null && maxDOB == null) {
            return true;
        }
        Date userDOB = user.getDOB();
        if (userDOB == null) {
            return false;
        }
        if (minDOB != null && userDOB.before(minDOB)) {
            return false;
        }
        if (maxDOB != null && userDOB.after(maxDOB)) {
            return false;
        }
        return true;
    }

    private boolean matchesLocation(User user, String locationFilter) {
        if (locationFilter == null || locationFilter.isEmpty()) {
            return true;
        }
        return user.getHomeLocation().toLowerCase().contains(locationFilter.toLowerCase());
    }

    private boolean matchesEducation(User user, String educationFilter) {
        if (educationFilter == null || educationFilter.isEmpty()) {
            return true;
        }
        return user.getEducation().toLowerCase().contains(educationFilter.toLowerCase());
    }

    private boolean matchesProfession(User user, String professionFilter) {
        if (professionFilter == null || professionFilter.isEmpty()) {
            return true;
        }
        return user.getProfession().toLowerCase().contains(professionFilter.toLowerCase());
    }

    public String getUserReligionByEmail(String loggedInEmail) {
        User user=userRepository.findByEmail(loggedInEmail).get();
        return user.getRelegion();

    }
}
