package com.hoony0321.URL_Shortcut.repository;

import com.hoony0321.URL_Shortcut.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
