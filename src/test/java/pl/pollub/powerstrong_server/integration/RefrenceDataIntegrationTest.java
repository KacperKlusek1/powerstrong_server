package pl.pollub.powerstrong_server.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.config.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
class ReferenceDataIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Test
    void get_reference_endpoints_return_200() throws Exception {
        mvc.perform(get("/api/reference/exercises")).andExpect(status().isOk());
        mvc.perform(get("/api/reference/categories")).andExpect(status().isOk());
        mvc.perform(get("/api/reference/movement-patterns")).andExpect(status().isOk());
        mvc.perform(get("/api/reference/target-muscle-groups")).andExpect(status().isOk());
        mvc.perform(get("/api/reference/effort-types")).andExpect(status().isOk());
        mvc.perform(get("/api/reference/training-methods")).andExpect(status().isOk());
    }
}
