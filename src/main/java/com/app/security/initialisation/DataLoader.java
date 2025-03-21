package com.app.security.initialisation;

import com.app.security.tableBDD.User;
import com.app.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin")==null) {
            User admin = new User(null, "admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN");
            userRepository.save(admin);
        }
    }
}
