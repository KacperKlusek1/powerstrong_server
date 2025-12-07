package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceUnitTest {

    JwtService jwtService;

    @BeforeEach
    void init() {
        jwtService = new JwtService();
        String secret = "cG93ZXJzdHJvbmdzdXBlcm1lZ2F1bHRyYXBvd2VyZnVsdW5rbm93bnNlY3JldHBhc3N3b3Jk";
        ReflectionTestUtils.setField(jwtService, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 1000 * 60 * 60L);
    }

    @Test
    void generate_and_validate_token() {
        UserDetails u = User.withUsername("user1").password("x").roles("USER").build();
        String token = jwtService.generateToken(Map.of("k","v"), u);
        assertNotNull(token);

        assertEquals("user1", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token, u));
    }

    @Test
    void extractClaim_returns_expected() {
        UserDetails u = User.withUsername("abc").password("p").roles("USER").build();
        String token = jwtService.generateToken(u);
        assertEquals("abc", jwtService.extractUsername(token));
    }
}
