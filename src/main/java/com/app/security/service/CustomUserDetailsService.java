package com.app.security.service;

import com.app.security.tableBDD.User;
import com.app.security.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Tentative de connexion avec l'utilisateur : " + username);

        User user = userRepository.findByUsername(username);  // Récupérer l'utilisateur directement

        if (user == null) {
            System.out.println("Utilisateur non trouvé : " + username);
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }

        System.out.println("Utilisateur trouvé : " + user.getUsername());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}