package com.perfectElegance.controller;

import com.perfectElegance.modal.Profile;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import com.perfectElegance.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/updateProfile/{id}")
    public ResponseEntity<String>updateProfile(@PathVariable Integer id, @RequestBody Profile profileDet){
        profileService.updateProfile(id,profileDet);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getUserByUserId(@PathVariable Integer userId) {
        System.out.println("inside profile");
        User profile = profileService.getUserByUserId(userId);
        System.out.println(profile);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<Profile> getProfile(@PathVariable Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        Profile profile = user.getProfile();

        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
