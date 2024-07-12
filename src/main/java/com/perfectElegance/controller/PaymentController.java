package com.perfectElegance.controller;

import com.google.gson.Gson;
import com.perfectElegance.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class PaymentController {

    private static Gson gson=new Gson();


    @Autowired
    private JwtService jwtService;






}
