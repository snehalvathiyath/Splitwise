package com.scaler.splitwisejan2023.services.user;

import com.scaler.splitwisejan2023.models.User;
import com.scaler.splitwisejan2023.repositories.UserRepository;
import com.scaler.splitwisejan2023.services.passwordencoder.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String phoneNUmber,
                             String password,
                             String username) {
        User user = new User();
        user.setUsername(username);
        user.setPhoneNumber(phoneNUmber);
        user.setHashedPassword(passwordEncoder.getEncodedPassword(password));

        User user1 = userRepository.save(user);

        return user1;
    }

    public User updateProfile(Long userId, String newPassword) {
        User user = userRepository.findUserById(userId);
        user.setHashedPassword(newPassword);
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    private void logIn(Long userId, String password) {
        User user = userRepository.findUserById(userId);

        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            System.out.println("Password Error");
        } else {
            System.out.println("Login Successful");
        }
    }
}

// User, Group -> SettleUpService.settleUp(groupId)
// settleUpService -> connect to DB (Using repositories)
// service -> strategy implementation class.
