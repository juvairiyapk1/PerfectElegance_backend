package com.perfectElegance.controller;

import com.perfectElegance.modal.User;
import com.perfectElegance.service.UserSideBarService;
import com.perfectElegance.utils.ToggleProfileRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @DeleteMapping("/profile")
    public ResponseEntity<?>profileDeleted(@RequestParam Integer userId,
                                           @RequestParam String reason,
                                           @RequestParam(required = false)String details){
        System.out.println("inside delete");
      userSideBarService.delete(userId,reason,details);
      return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String,String>> logout(HttpServletRequest request, Authentication authentication) {
        String token = request.getHeader("Authorization");
        System.out.println("Received token: " + token);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println("Token after removing 'Bearer ': " + token);

            userSideBarService.logout(token); // Invalidate token and update user status
        } else {
            System.out.println("Token is missing or doesn't start with 'Bearer '");
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
