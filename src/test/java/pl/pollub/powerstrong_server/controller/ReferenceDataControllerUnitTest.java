package pl.pollub.powerstrong_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.config.TestSecurityConfig;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.service.JwtService;
import pl.pollub.powerstrong_server.service.ReferenceDataService;
import pl.pollub.powerstrong_server.utils.JwtAuthenticationFilter;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReferenceDataController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class ReferenceDataControllerUnitTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    ReferenceDataService service;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    JwtService jwtService;


    @Test
    void getExercises_returns_list() throws Exception {
        when(service.getAllExercises()).thenReturn(List.of(new ExerciseDto()));
        mvc.perform(get("/api/reference/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getCategories_returns_list() throws Exception {

        ExerciseCategoryDto dto = new ExerciseCategoryDto();
        dto.setId(1);
        dto.setName("Klatka");

        Mockito.when(service.getAllExerciseCategories())
                .thenReturn(List.of(dto));

        mvc.perform(get("/api/reference/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Klatka"));
    }

}
