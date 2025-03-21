package com.app.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.security.tableBDD.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
