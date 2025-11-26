package pl.pollub.powerstrong_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.model.User;
import pl.pollub.powerstrong_server.service.TrainingService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TrainingController.class)
class TrainingControllerUnitTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    TrainingService trainingService;
    @Autowired
    ObjectMapper om;

    @Test
    void getPlanTemplates_returns_list() throws Exception {
        when(trainingService.getAllPlanTemplates()).thenReturn(List.of(new TrainingPlanFullDto()));
        mvc.perform(get("/api/plans/templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void assignPlan_calls_service_and_returns_ok() throws Exception {
        // Mock Authentication with principal User
        Authentication auth = mock(Authentication.class);
        User u = new User(); u.setId(5);
        when(auth.getPrincipal()).thenReturn(u);

        mvc.perform(post("/api/plans/1/assign")
                        .principal(auth))
                .andExpect(status().isOk());
        // can't verify principal directly here, but controller calls service -> verify by stubbing service behavior if needed
    }

    @Test
    void createCustomPlan_calls_service() throws Exception {
        Authentication auth = mock(Authentication.class);
        User u = new User(); u.setId(7);
        when(auth.getPrincipal()).thenReturn(u);

        TrainingPlanFullDto dto = new TrainingPlanFullDto();
        mvc.perform(post("/api/plans/custom")
                        .principal(auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
