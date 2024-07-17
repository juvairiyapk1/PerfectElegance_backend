package com.perfectElegance.controller;

import com.perfectElegance.Dto.HomeDto;
import com.perfectElegance.Dto.PageRequestDto;
import com.perfectElegance.Dto.ProfileByUserDto;
import com.perfectElegance.modal.User;
import com.perfectElegance.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class HomeController {

    @Autowired
    private HomeService homeService;


    @GetMapping("/OtherUsersByProfession")
    public List<HomeDto>getAllUsersByProfession(
            @RequestParam(required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String profession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String loggedInEmail = userDetails.getUsername();

        Integer loggedInUserId = homeService.getUserIdByEmail(loggedInEmail);
        String loggedInUserGender = homeService.getUserGenderByEmail(loggedInEmail);
        String loggedInUserReligion = homeService.getUserReligionByEmail(loggedInEmail);

        PageRequestDto pageRequestDto = new PageRequestDto();
        pageRequestDto.setPageNo(pageNo);
        pageRequestDto.setPageSize(pageSize);

       return homeService.findUserByProfession(loggedInUserId, loggedInUserGender, loggedInUserReligion, profession, pageRequestDto.getPageable());
    }

    @GetMapping("profileByUser")
    public ResponseEntity<ProfileByUserDto>getUserById(@RequestParam Integer userId){
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhinhg");
        return homeService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }







}
