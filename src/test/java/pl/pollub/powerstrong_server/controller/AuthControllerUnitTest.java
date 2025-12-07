package pl.pollub.powerstrong_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pollub.powerstrong_server.config.TestSecurityConfig;
import pl.pollub.powerstrong_server.dto.auth.*;
import pl.pollub.powerstrong_server.service.AuthenticationService;
import pl.pollub.powerstrong_server.service.JwtService;
import pl.pollub.powerstrong_server.utils.JwtAuthenticationFilter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerUnitTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    AuthenticationService service;

    @Autowired
    ObjectMapper om;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    JwtService jwtService;

    @Test
    void register_returns_ok_with_token() throws Exception {
        RegisterRequest rq = new RegisterRequest("u","e","p");
        AuthResponse ar = new AuthResponse("t","u",1);
        when(service.register(any())).thenReturn(ar);

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(rq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("t"))
                .andExpect(jsonPath("$.username").value("u"));
    }

    @Test
    void login_returns_token() throws Exception {
        LoginRequest req = new LoginRequest("l","p");
        AuthResponse ar = new AuthResponse("tok","l",2);
        when(service.authenticate(any())).thenReturn(ar);

        mvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("tok"));
    }
}
