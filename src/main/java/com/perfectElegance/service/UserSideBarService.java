package com.perfectElegance.service;

import com.perfectElegance.modal.User;
import com.perfectElegance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSideBarService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void toggleProfileVisibility(Integer userId, boolean hidden) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(user+"user");

        user.setHidden(hidden);
        userRepository.save(user);
    }

    public boolean getProfileVisibility(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.isHidden();
    }

    public void delete(Integer userId,String reason,String details) {
        User user =userRepository.findById(userId)
                        .orElseThrow(()->new UsernameNotFoundException("User not found"));
//        logDeletionReason(userId,reason,details);
        userRepository.delete(user);
    }

    public void logout(String token) {
        String userName=  jwtService.extractUsername(token);

        System.out.println("Extracted username: " + userName);
        if(userName != null){
            User user = userRepository.findByEmail(userName)
                    .orElseThrow(()->new UsernameNotFoundException("user is not found"));
            user.setOnline(false);
            userRepository.save(user);
            System.out.println("User status after logout: " + user.isOnline());

            jwtService.invalidToken(token);
        }else {
            System.out.println("Could not extract user information from the token");
        }



    }

//    private User getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()){
//            Object principal = authentication.getPrincipal();
//            if(principal instanceof UserDetails){
//                return (User)principal;
//            }
//        }
//        return null;
//    }

}
