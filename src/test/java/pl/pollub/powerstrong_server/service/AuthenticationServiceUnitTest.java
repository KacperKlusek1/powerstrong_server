package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.pollub.powerstrong_server.dto.auth.*;
import pl.pollub.powerstrong_server.model.User;
import pl.pollub.powerstrong_server.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceUnitTest {

    @Mock
    UserRepository repository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtService jwtService;
    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks AuthenticationService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_should_save_user_and_return_token() {
        RegisterRequest req = new RegisterRequest("u","e@x.pl","pwd");
        when(passwordEncoder.encode("pwd")).thenReturn("enc");
        when(jwtService.generateToken(any())).thenReturn("token123");

        AuthResponse resp = service.register(req);

        assertEquals("token123", resp.getToken());
        assertEquals("u", resp.getUsername());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void authenticate_successful_should_return_token() {
        LoginRequest req = new LoginRequest("login","pwd");
        User user = new User();
        user.setUsername("login");
        when(repository.findByUsernameOrEmail("login")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("tok");

        AuthResponse res = service.authenticate(req);
        assertEquals("tok", res.getToken());
        assertEquals("login", res.getUsername());
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    void createUserEntity_encodes_password_and_sets_date() throws Exception {
        RegisterRequest req = new RegisterRequest("x","a@b","12");
        when(passwordEncoder.encode("12")).thenReturn("e12");
        AuthResponse r = service.register(req);
        verify(repository).save(argThat(u -> u.getPassword().equals("e12") && u.getUsername().equals("x")));
    }
}
