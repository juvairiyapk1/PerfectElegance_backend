package com.perfectElegance.controller;

import com.perfectElegance.exceptions.UserNotVerifiedException;
import com.perfectElegance.modal.ForgotPassword;
import com.perfectElegance.repository.ForgotPasswordRepository;
import com.perfectElegance.repository.UserRepository;
import com.perfectElegance.utils.RegisterRequest;
import com.perfectElegance.exceptions.EmailAlreadyExistsException;
import com.perfectElegance.exceptions.InvalidPasswordException;
import com.perfectElegance.utils.AuthenticationResponse;
import com.perfectElegance.utils.LoginResponse;
import com.perfectElegance.modal.User;
import com.perfectElegance.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = authenticationService.register(request);
            System.out.println("hi");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request) {
        try {
            LoginResponse response = authenticationService.authenticate(request);
            System.out.println(response + " Response");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserNotVerifiedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (InvalidPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String,String>> logout(HttpServletRequest request , Authentication authentication){
        String token=request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")){
            token=token.substring(7);
          authenticationService.logout(token);
        }
        Map<String,String>response=new HashMap<>();
        response.put("message","Logged out successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    }






