package com.app.security.service;

import org.springframework.stereotype.Service;
import com.app.security.tableBDD.User;
import com.app.security.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);  // Implémentez cette méthode dans votre repository
    }
}