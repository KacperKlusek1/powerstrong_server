package pl.pollub.powerstrong_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.service.ReferenceDataService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReferenceDataController.class)
class ReferenceDataControllerUnitTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    ReferenceDataService service;

    @Test
    void getExercises_returns_list() throws Exception {
        when(service.getAllExercises()).thenReturn(List.of(new ExerciseDto()));
        mvc.perform(get("/api/reference/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getCategories_returns_list() throws Exception {
        when(service.getAllExerciseCategories()).thenReturn(List.of(new ExerciseCategoryDto()));
        mvc.perform(get("/api/reference/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists());
    }
}
