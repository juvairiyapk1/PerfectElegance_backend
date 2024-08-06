package com.perfectElegance.controller;


import com.perfectElegance.Dto.UserProfileDto;
import com.perfectElegance.modal.Partner;
import com.perfectElegance.modal.Profile;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import com.perfectElegance.service.*;
import com.perfectElegance.utils.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FileUploadImpl fileUpload;
    private final UserDetailsServiceIMPL userDetailsServiceIMPL;
    @Autowired
    private ModelMapper modelMapper;

    @PutMapping("/updateProfile/{userId}")
    public ResponseEntity<Map<String,String>> updateProfile(@PathVariable("userId") Integer userId, @RequestBody Profile profileDet){
        profileService.updateProfile(userId,profileDet);
        Map<String,String> response = new HashMap<>();
        response.put("message","Profile updated successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatePhysicalAttr/{userId}")
    public ResponseEntity<Map<String,String>>updatePhysicalAttributes(@PathVariable("userId")Integer userId,
                                                                      @RequestBody Profile physicalData){
        profileService.updatePhysicalAttributes(userId,physicalData);
        Map<String,String> response = new HashMap<>();
        response.put("message","Physical Attributes updated");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateLocationAndContact/{userId}")
    public ResponseEntity<Map<String,String>>updateLocationAndContact(@PathVariable("userId")Integer userId,
                                                                      @RequestBody User locationData){
        User updatedUser = userDetailsServiceIMPL.updateLocation(userId,locationData);
        Map<String,String> response = new HashMap<>();

        if (updatedUser != null){
            response.put("message","User updated successfully!");
            return ResponseEntity.ok(response);
        }else{
            response.put("message","User updated failed!");

            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/updateFamilyInfo/{userId}")
    public ResponseEntity<Map<String,String>>updateFamilyInfo(@PathVariable("userId")Integer userId,
                                                              @RequestBody Profile familyData){
        profileService.uapdataFamily(userId,familyData);
        Map<String,String> response = new HashMap<>();
        response.put("message","Physical Attributes updated");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/editUser/{id}")
    public ResponseEntity<Map<String,String>>editUser(@PathVariable Integer id, @RequestBody User user){
        System.out.println(id+"hihi");
        System.out.println(user);
        User updatedUser=userDetailsServiceIMPL.updateUser(id,user);
        System.out.println(updatedUser!=null);
        Map<String, String> response = new HashMap<>();

        if (updatedUser != null){
            response.put("message","User updated successfully!");
            return ResponseEntity.ok(response);
        }else{
            response.put("message","User updated failed!");

            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable Integer userId) {
        System.out.println("inside profilr");
        try {
            User user = profileService.getUserByUserId(userId);
            if (user != null) {
                UserProfileDto profileDTO = modelMapper.map(user, UserProfileDto.class);
                return ResponseEntity.ok(profileDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "An error occurred while fetching the user profile");
            response.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }




    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("multipartFile") MultipartFile multipartFile,
            @RequestParam("userId") Integer userId) throws IOException {

        System.out.println(multipartFile + " image");
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid image!");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Map<String, Object> result = fileUpload.upload(multipartFile);
        String imageUrl = (String) result.get("url");

        User user = profileService.findById(userId);
        if (user == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "User not found!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Profile profile = profileService.findByUserId(userId);
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }

        profile.setImage(imageUrl);
        profileService.saveProfile(profile);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Image uploaded successfully!");
        response.put("secure_url", imageUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @GetMapping("/getProfile/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        Profile profile = user.getProfile();
        boolean isSubscribed = user.isSubscribed();

        if (profile != null) {
            ProfileResponse profileResponse = new ProfileResponse(profile, isSubscribed);
            return ResponseEntity.ok(profileResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getPartnerProfile/{userId}")
    public ResponseEntity<Partner>getPartnerPreference(@PathVariable("userId")Integer userId){
        User user = userRepository.findById(userId).orElseThrow(()->
                new UsernameNotFoundException("User is not fount"));
        System.out.println("User found: " + user);
        Partner partner=user.getPartner();
        System.out.println(partner+"ppppppppppppppppppppppppppp");
        if(partner != null){

            return ResponseEntity.ok(partner);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/updatePartnerPref/{userId}")
    public ResponseEntity<Map<String,String>>partnerPreferences(@PathVariable("userId")Integer userId,
                                                                @RequestBody Partner partnerData){
        Partner updatedPartner = profileService.updatePartnerPref(userId,partnerData);
         Map<String,String>response=new HashMap<>();

        if (updatedPartner != null){
            response.put("message","Partner updated successfully!");
            return ResponseEntity.ok(response);
        }else{
            response.put("message"," updated failed!");

            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/updatePartnerPrefEdu/{userId}")
    public ResponseEntity<Map<String,String>>partnerPreferEdu(@PathVariable("userId")Integer userId,
                                                              @RequestBody Partner partnerData){
        Partner updatedPartner = profileService.updatePartnerEducation(userId,partnerData);

        Map<String,String>response = new HashMap<>();

        if(updatedPartner != null){
            response.put("message","Partner updated successfully!");
            return ResponseEntity.ok(response);
        }else {
            response.put("message","partner Updating failed");
            return ResponseEntity.notFound().build();
        }
    }



}
