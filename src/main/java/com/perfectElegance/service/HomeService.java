package com.perfectElegance.service;

import com.perfectElegance.Dto.HomeDto;
import com.perfectElegance.Dto.PageRequestDto;
import com.perfectElegance.Dto.ProfileByUserDto;
import com.perfectElegance.Dto.UserProfileDto;
import com.perfectElegance.modal.Profile;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.ProfileRepository;
import com.perfectElegance.repository.UserRepository;
import org.antlr.v4.runtime.misc.MultiMap;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProfileRepository profileRepository;


    public Integer getUserIdByEmail(String email){
        User user=userRepository.findByEmail(email).get();
        return user != null ? user.getId() : null;

    }


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
                .filter(user -> user.getRelegion().equals(loggedInUserReligion))
                .filter(user -> !user.isHidden())
                .map(user -> {
                    HomeDto homeDto = new HomeDto();
                    homeDto.setId(user.getId());
                    homeDto.setName(user.getName());
                    homeDto.setDOB(user.getDOB());
                    homeDto.setRelegion(user.getRelegion());
                    homeDto.setHomeLocation(user.getHomeLocation());
                    homeDto.setEducation(user.getEducation());
                    homeDto.setProfession(user.getProfession());
                    homeDto.setOnline(user.isOnline());
                    if (user.getProfile() != null) {
                        homeDto.setImage(user.getProfile().getImage());
                    }

                    System.out.println("HomeDto: " + homeDto);
                    return homeDto;
                })
                .collect(Collectors.toList());
    }


    public Optional<ProfileByUserDto> getUserById(Integer userId) {
        System.out.println("Inside service: " + userId);
        Profile profile = profileRepository.findByUserId(userId);

        if (profile != null) {
            ProfileByUserDto profileByUserDto = new ProfileByUserDto();
            modelMapper.map(profile, profileByUserDto);
            modelMapper.map(profile.getUser(),profileByUserDto);
            return Optional.of(profileByUserDto);
        }
        return Optional.empty();
    }


}
