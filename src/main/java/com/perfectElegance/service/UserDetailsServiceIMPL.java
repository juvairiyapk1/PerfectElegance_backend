package com.perfectElegance.service;

import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceIMPL implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found or blocked"));
        if (user.isBlocked()) {
            throw new UsernameNotFoundException("User is blocked");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

        public User updateUser(Integer id, User user) {
        System.out.println(user+"hello");
        User existingUser=userRepository.findById(id).get();
        existingUser.setName(user.getName());
        existingUser.setGender(user.getGender());
        existingUser.setCreateProfileFor(user.getCreateProfileFor());
        existingUser.setDOB(user.getDOB());
        existingUser.setMaritalStatus(user.getMaritalStatus());
        existingUser.setPhysicaleStatus(user.getPhysicaleStatus());
        existingUser.setEducation(user.getEducation());
        existingUser.setProfession(user.getProfession());
        existingUser.setBodyType(user.getBodyType());
        existingUser.setSkinTone(user.getSkinTone());
        return userRepository.save(existingUser);
    }

    public User updateLocation(Integer userId, User locationData) {
        User existingUser = userRepository.findById(userId).get();
        existingUser.setCurrentLocation(locationData.getCurrentLocation());
        existingUser.setHomeLocation(locationData.getHomeLocation());
        existingUser.setResidentialStatus(locationData.getResidentialStatus());
        existingUser.setEmail(locationData.getEmail());
        existingUser.setPhoneNumber(locationData.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    public User findUserByHeader(String authHeader) {
        String jwt = "";
        String userEmail ="";
        User user = new User();
        if(authHeader != null ) {
            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);
            System.out.println("userEMail from auth =" +userEmail);
            user = userRepository.findByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("user not found"));
            System.out.println("user from auth =" +user);
            return user;
        }
        return user;
    }


    public User findUserById(Integer userId) {
        Optional<User> optionalUser= userRepository.findById(userId);
       return optionalUser.get();
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean isUserSubscribed(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("user is not found"));
        return user.isSubscribed();
    }

    public void disconnectUser(User user){

    }

    public List<User> findConnectedUsers(){

        return null;
    }



}
