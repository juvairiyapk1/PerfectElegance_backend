package com.perfectElegance.controller;

import com.perfectElegance.modal.User;
import com.perfectElegance.service.UserSideBarService;
import com.perfectElegance.utils.ToggleProfileRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserSideBarController {

    private final UserSideBarService userSideBarService;

    @GetMapping("/profileVisibility/{userId}")
    public ResponseEntity<Boolean> getProfileVisibility(@PathVariable Integer userId) {
        boolean isHidden = userSideBarService.getProfileVisibility(userId);
        return ResponseEntity.ok(isHidden);
    }

    @PostMapping("/toggleProfileVisibility")
    public ResponseEntity<?> toggleProfileVisibility(@RequestBody ToggleProfileRequest request) {
        System.out.println("Received request: " + request);
        userSideBarService.toggleProfileVisibility(request.getUserId(), request.isHidden());
        String message = request.isHidden() ? "User profile hidden" : "User profile unhidden";
        return ResponseEntity.ok().body(new HashMap<String, String>() {{
            put("message", message);
        }});
    }


}
