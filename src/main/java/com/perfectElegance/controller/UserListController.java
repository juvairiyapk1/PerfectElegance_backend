package com.perfectElegance.controller;

import com.perfectElegance.Dto.UserDto;
import com.perfectElegance.modal.User;
import com.perfectElegance.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
public class UserListController {
    @Autowired
    private UserListService userListService;

    @GetMapping("/user-list")
    public ResponseEntity<List<UserDto>>getAllUsers(){
        System.out.println("inside userLIst controller");
        List<UserDto>users=userListService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{id}/block")
    public ResponseEntity<Map<String,String>> blockUser(@PathVariable Integer id) {
        userListService.blockUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message","User blocked successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{id}/unblock")
    public ResponseEntity<Map<String,String>> unblockUser(@PathVariable Integer id) {
        userListService.unblockUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message","User unblocked successfully");
        return ResponseEntity.ok(response);
    }

}
