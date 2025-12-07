package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pollub.powerstrong_server.dto.auth.*;
import pl.pollub.powerstrong_server.model.User;
import pl.pollub.powerstrong_server.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceUnitTest {

    @Mock UserRepository userRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtService jwtService;
    @Mock AuthenticationManager authenticationManager;

    AuthenticationService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager);
    }

    @Test
    void register_should_save_user_and_return_token() {
        RegisterRequest req = new RegisterRequest("u","e@x.pl","pwd");
        when(passwordEncoder.encode("pwd")).thenReturn("enc");
        when(jwtService.generateToken(any())).thenReturn("token123");

        AuthResponse resp = service.register(req);

        assertEquals("token123", resp.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void authenticate_should_return_token_when_user_exists() {
        LoginRequest req = new LoginRequest("u","pwd");
        User user = new User();
        user.setUsername("u");

        when(userRepository.findByUsernameOrEmail("u")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwt");

        AuthResponse res = service.authenticate(req);
        assertEquals("jwt", res.getToken());
    }
}
