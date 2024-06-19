package com.perfectElegance.service;

import com.perfectElegance.Dto.UserDto;
import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserListService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setEmail(user.getEmail());
                    userDto.setName(user.getName());
                    userDto.setPhoneNumber(user.getPhoneNumber());
//                    userDto.setGender(user.getGender());
                    userDto.setBlocked(user.isBlocked());
                    userDto.setRole(user.getRole());
                    return userDto;
                })
                .collect(Collectors.toList());
    }

//    public User updateUser(Integer id, User user) {
//        System.out.println(user+"hello");
//        User existingUser=userRepository.findById(id).get();
//        existingUser.setName(user.getName());
//        existingUser.setPhoneNumber(user.getPhoneNumber());
//        existingUser.setEmail(user.getEmail());
//        existingUser.setRole(user.getRole());
//        existingUser.setGender(user.getGender());
//        return userRepository.save(existingUser);
//    }

    public void blockUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBlocked(true); // Set user blocked status
            userRepository.save(user); // Save updated user
        } else {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
    }

    public void unblockUser(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBlocked(false); // Set user blocked status
            userRepository.save(user); // Save updated user
        } else {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
    }


}
