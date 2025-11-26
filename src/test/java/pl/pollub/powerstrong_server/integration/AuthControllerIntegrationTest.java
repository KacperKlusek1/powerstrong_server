package pl.pollub.powerstrong_server.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.config.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class AuthControllerIntegrationTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @Test
    void register_and_login_flow() throws Exception {
        String registerJson = "{\"username\":\"ituser\",\"email\":\"it@x.pl\",\"password\":\"p\"}";

        mvc.perform(post("/api/auth/register").contentType("application/json").content(registerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        String loginJson = "{\"login\":\"ituser\",\"password\":\"p\"}";
        mvc.perform(post("/api/auth/login").contentType("application/json").content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
