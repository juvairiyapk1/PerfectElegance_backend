package com.perfectElegance.controller;

import com.perfectElegance.Dto.HomeDto;
import com.perfectElegance.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/OtherUsers")
    public List<HomeDto> getAllUsersExceptAdminAndLoggedInUser(){
        System.out.println("inside GetAllUsers");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails  = (UserDetails)authentication.getPrincipal();
        String loggedInEmail = userDetails.getUsername();

        Integer loggedInUserId = homeService.getUserIdByEmail(loggedInEmail);

        return homeService.findAllExceptAdminAndLoggedInUser(loggedInUserId);
    }

}
