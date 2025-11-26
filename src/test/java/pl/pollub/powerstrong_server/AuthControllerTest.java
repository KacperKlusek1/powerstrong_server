package pl.pollub.powerstrong_server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.controller.AuthController;
import pl.pollub.powerstrong_server.dto.auth.AuthResponse;
import pl.pollub.powerstrong_server.dto.auth.RegisterRequest;
import pl.pollub.powerstrong_server.service.AuthenticationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    AuthenticationService authService;

    @Test
    void registerReturnsAuthResponse() throws Exception {
        RegisterRequest req = new RegisterRequest("user","u@e.com","pass");
        AuthResponse resp = AuthResponse.builder().token("jwt").username("user").userId(1).build();
        when(authService.register(any())).thenReturn(resp);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt"))
                .andExpect(jsonPath("$.username").value("user"));
    }
}