package com.example.projekt_serwer.service;

import com.example.projekt_serwer.dto.UserDto;
import com.example.projekt_serwer.exception.ResourceNotFoundException;
import com.example.projekt_serwer.mapper.EntityDtoMapper;
import com.example.projekt_serwer.model.User;
import com.example.projekt_serwer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityDtoMapper mapper;

    public UserDto findUserDetailsById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return mapper.toUserDto(user);
    }

    // TODO: Dodaj logikę rejestracji i logowania
}