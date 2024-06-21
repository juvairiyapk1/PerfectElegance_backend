package com.perfectElegance.service;

import com.perfectElegance.Dto.HomeDto;
import com.perfectElegance.Dto.UserDto;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<HomeDto> findAllExceptAdminAndLoggedInUser(Integer loggedInUserId){
        List<User> users= userRepository.findAllExceptAdminAndLoggedInUserAndBlocked(loggedInUserId);

        return users.stream()
                .map(user -> {
                    HomeDto homeDto = new HomeDto();
                    homeDto.setName(user.getName());
                    homeDto.setDOB(user.getDOB());
                    homeDto.setRelegion(user.getRelegion());
                    homeDto.setHomeLocation(user.getHomeLocation());
                    homeDto.setEducation(user.getEducation());
                    homeDto.setProfession(user.getProfession());
                    return homeDto;
                })
                .collect(Collectors.toList());
    }
}
