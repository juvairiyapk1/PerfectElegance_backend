package com.perfectElegance.service;

import com.perfectElegance.Dto.HomeDto;
import com.perfectElegance.Dto.PageRequestDto;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private UserRepository userRepository;


    public Integer getUserIdByEmail(String email){
        User user=userRepository.findByEmail(email).get();
        return user != null ? user.getId() : null;

    }
//    public List<HomeDto> findAllExceptAdminAndLoggedInUser(Integer loggedInUserId, String gender, String loggedInUserReligion,Pageable pageable){
//
//        List<User> users= userRepository.findAllExceptAdminAndLoggedInUserAndBlocked(loggedInUserId,gender,pageable);
//
//        return users.stream()
//                .filter(user -> !user.getRelegion().equals(loggedInUserReligion))
//                .map(user -> {
//                    HomeDto homeDto = new HomeDto();
//                    homeDto.setName(user.getName());
//                    homeDto.setDOB(user.getDOB());
//                    homeDto.setRelegion(user.getRelegion());
//                    homeDto.setHomeLocation(user.getHomeLocation());
//                    homeDto.setEducation(user.getEducation());
//                    homeDto.setProfession(user.getProfession());
//                    if (user.getProfile() != null) {
//                        homeDto.setImage(user.getProfile().getImage());
//                    }
//                    return homeDto;
//                })
//                .collect(Collectors.toList());
//    }

    public String getUserGenderByEmail(String loggedInEmail) {
        User user=userRepository.findByEmail(loggedInEmail).get();
        return user.getGender();
    }


    public String getUserReligionByEmail(String loggedInEmail) {
        User user=userRepository.findByEmail(loggedInEmail).get();
        return user.getRelegion();

    }

    public List<HomeDto> findUserByProfession(Integer loggedInUserId, String loggedInUserGender, String loggedInUserReligion, String profession, Pageable pageable) {
        List<User> users = userRepository.findAllExceptAdminAndLoggedInUserAndBlocked(loggedInUserId, loggedInUserGender, pageable);

        if (profession != null && !profession.equalsIgnoreCase("all professions") && !profession.isEmpty()) {
            users = users.stream()
                    .filter(user -> user.getProfession().equalsIgnoreCase(profession))
                    .collect(Collectors.toList());
        }

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



}
