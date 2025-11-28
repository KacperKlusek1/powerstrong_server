package pl.pollub.powerstrong_server.security;

import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.mock.web.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.pollub.powerstrong_server.service.JwtService;
import pl.pollub.powerstrong_server.utils.JwtAuthenticationFilter;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest {

    @Mock
    JwtService jwtService;
    @Mock
    UserDetailsService userDetailsService;

    JwtAuthenticationFilter filter;

    MockHttpServletRequest req;
    MockHttpServletResponse res;
    FilterChain chain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new JwtAuthenticationFilter(jwtService, userDetailsService);
        req = new MockHttpServletRequest();
        res = new MockHttpServletResponse();
        chain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterSetsAuthentication_whenTokenValid() throws Exception {
        req.addHeader("Authorization", "Bearer VALID_TOKEN");
        UserDetails userDetails = User.withUsername("john").password("pass").roles("USER").build();

        when(jwtService.extractUsername("VALID_TOKEN")).thenReturn("john");
        when(userDetailsService.loadUserByUsername("john")).thenReturn(userDetails);
        when(jwtService.isTokenValid("VALID_TOKEN", userDetails)).thenReturn(true);

        filter.doFilter(req, res, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("john", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(chain).doFilter(req, res);
    }

    @Test
    void doFilterDoesNotAuthenticate_whenMissingHeader() throws Exception {
        filter.doFilter(req, res, chain);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(req, res);
    }
}