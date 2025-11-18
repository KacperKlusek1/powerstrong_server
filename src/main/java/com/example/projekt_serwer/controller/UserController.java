package com.example.projekt_serwer.controller;

import com.example.projekt_serwer.dto.UserDto;
import com.example.projekt_serwer.service.UserService; // Twój serwis Spring Boot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users") // Ustawiamy bazowy URL
public class UserController {

    // Wstrzykujemy serwis Spring Boot, który będzie zawierał logikę biznesową
    @Autowired
    private UserService userService;

    /**
     * Ten endpoint idealnie mapuje się do metody getUserDetails() w UserService na Androidzie.
     * @param userId Pobierane ze ścieżki
     * @return Zwraca UserDto (konwertowane automatycznie na JSON przez Jacksona)
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") int userId) {
        // 1. Logika biznesowa jest delegowana do serwisu
        UserDto user = userService.findUserDetailsById(userId);

        // 2. Zwracamy DTO z kodem 200 OK
        return ResponseEntity.ok(user);
    }

    // UWAGA: W przyszłości dodasz tu endpointy do logowania i rejestracji,
    // np. @PostMapping("/register")
}