package pl.pollub.powerstrong_server.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.mock.web.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import pl.pollub.powerstrong_server.service.JwtService;
import pl.pollub.powerstrong_server.utils.JwtAuthenticationFilter;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest {

    @Mock
    JwtService jwtService;
    @InjectMocks
    JwtAuthenticationFilter filter;

    MockHttpServletRequest req;
    MockHttpServletResponse res;
    FilterChain filterChain;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilter_sets_context_when_token_valid() throws Exception {
        req.addHeader("Authorization", "Bearer VALID");

        UserDetails user = User.withUsername("john").password("x").roles("USER").build();
        when(jwtService.extractUsername("VALID")).thenReturn("john");
        when(jwtService.isTokenValid("VALID", user)).thenReturn(true);
        //when(jwtService.loadUserByUsername("john")).thenReturn(user);

        filter.doFilter(req, res, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("john", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void doFilter_does_not_authenticate_when_header_missing() throws Exception {
        filter.doFilter(req, res, filterChain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilter_ignores_invalid_token() throws Exception {
        req.addHeader("Authorization", "Bearer INVALID");

        when(jwtService.extractUsername("INVALID")).thenThrow(new RuntimeException("bad token"));

        filter.doFilter(req, res, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
