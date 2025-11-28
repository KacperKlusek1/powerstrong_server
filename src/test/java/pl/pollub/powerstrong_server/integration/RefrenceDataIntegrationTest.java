package pl.pollub.powerstrong_server.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.config.TestSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MySQL",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.sql.init.mode=never",
        "spring.jpa.show-sql=true"
})
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
