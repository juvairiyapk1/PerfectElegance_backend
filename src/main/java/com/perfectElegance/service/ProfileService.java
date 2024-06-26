package com.perfectElegance.service;

import com.perfectElegance.exceptions.EmailAlreadyExistsException;
import com.perfectElegance.modal.Profile;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.ProfileRepository;
import com.perfectElegance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public Profile saveProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public User getUserByUserId(Integer userId) {
        System.out.println("Finding profile for user ID: " + userId);
        User user = userRepository.findById(userId).get();
        System.out.println("Found profile: " + user);
        return user;
    }



    public Profile createProfile(Profile profile, User user){

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Profile createdProfile = modelMapper.map(profile,Profile.class);
        return profileRepository.save(profile);

    }


    public Profile updateProfile(Integer id,Profile profileDet){
        return profileRepository.findById(id).map(profile -> {
            modelMapper.map(profileDet,profile);
            return profileRepository.save(profile);
        }).orElseGet(()->{
            profileDet.setId(id);
            return profileRepository.save(profileDet);
        });
    }

    public User findById(Integer userId){
        return userRepository.findById(userId).orElse(null);
    }

    public Profile findByUserId(Integer userId) {
        return profileRepository.findByUserId(userId);
    }


}
