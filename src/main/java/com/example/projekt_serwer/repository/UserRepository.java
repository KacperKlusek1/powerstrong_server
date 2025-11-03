package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository dla encji User
 * Dziedziczy po JpaRepository<User, Long> gdzie:
 * - User = typ encji
 * - Long = typ klucza głównego (ID)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // METODY AUTOMATYCZNE (z JpaRepository):
    // - findAll() - List<User>
    // - findById(Long id) - Optional<User>
    // - save(User user) - User
    // - deleteById(Long id) - void
    // - count() - long
    // - existsById(Long id) - boolean

    // WŁASNE METODY (Spring generuje SQL automatycznie!):

    /**
     * Znajdź usera po username
     * Spring automatycznie generuje: SELECT * FROM user WHERE username = ?
     */
    Optional<User> findByUsername(String username);

    /**
     * Znajdź usera po email
     * Spring automatycznie generuje: SELECT * FROM user WHERE email = ?
     */
    Optional<User> findByEmail(String email);

    /**
     * Sprawdź czy istnieje user z danym username
     * Spring automatycznie generuje: SELECT COUNT(*) > 0 FROM user WHERE username = ?
     */
    boolean existsByUsername(String username);

    /**
     * Sprawdź czy istnieje user z danym email
     */
    boolean existsByEmail(String email);
}