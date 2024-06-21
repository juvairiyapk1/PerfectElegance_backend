package com.perfectElegance.service;

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
    public List<UserDto> findAllExceptAdminAndLoggedInUser(Integer loggedInUserId){
        List<User> users= userRepository.findAllExceptAdminAndLoggedInUserAndBlocked(loggedInUserId);

        return users.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setName(user.getName());
                    userDto.setDOB(user.getDOB());
                    userDto.setHomeLocation(user.getHomeLocation());
                    userDto.setEducation(user.getEducation());
                    userDto.setProfession(user.getProfession());
                    return userDto;
                })
                .collect(Collectors.toList());
    }
}
