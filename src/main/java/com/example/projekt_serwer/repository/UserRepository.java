package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Używane do pobierania szczegółów użytkownika
    Optional<User> findByUsername(String username);

    // Potrzebne do logowania (jeśli implementujesz Spring Security)
    // Optional<User> findByEmail(String email);
}