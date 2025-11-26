package pl.pollub.powerstrong_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.powerstrong_server.dto.auth.AuthResponse;
import pl.pollub.powerstrong_server.dto.auth.LoginRequest;
import pl.pollub.powerstrong_server.dto.auth.RegisterRequest;
import pl.pollub.powerstrong_server.model.User;
import pl.pollub.powerstrong_server.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        User user = createUserEntity(request);

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .userId(user.getId())
                .build();
    }

    private User createUserEntity(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreateDate(LocalDateTime.now());
        return user;
    }

    @Transactional
    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsernameOrEmail(request.getLogin())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .userId(user.getId())
                .build();
    }
}