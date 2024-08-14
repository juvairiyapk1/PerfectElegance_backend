package com.perfectElegance.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class VideoCallController {

    @PostMapping("/video-call/create-room")
    public ResponseEntity<Map<String, String>> createRoom() {
        String roomName = "Room_" + System.currentTimeMillis();
        Map<String, String> response = new HashMap<>();
        response.put("roomName", roomName);
        return ResponseEntity.ok(response);
    }
}
