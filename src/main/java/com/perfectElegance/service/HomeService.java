package com.perfectElegance.service;

import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeService {

    @Autowired
    private UserRepository userRepository;


    public Integer getUserIdByEmail(String email){
        User user=userRepository.findByEmail(email).get();
        return user != null ? user.getId() : null;

    }
    public List<User> findAllExceptAdminAndLoggedInUser(Integer loggedInUserId){
        return userRepository.findAllExceptAdminAndLoggedInUserAndBlocked(loggedInUserId);
    }
}
