package com.perfectElegance.controller;

import com.perfectElegance.modal.User;
import com.perfectElegance.service.UserDetailsServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class WebSocketController {

    private final UserDetailsServiceIMPL userService;

    @GetMapping("/chat")
    public ResponseEntity<List<User>>findConnectedUsers(){
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}
