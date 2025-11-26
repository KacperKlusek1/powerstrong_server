package pl.pollub.powerstrong_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.controller.ReferenceDataController;
import pl.pollub.powerstrong_server.dto.ExerciseDto;
import pl.pollub.powerstrong_server.service.ReferenceDataService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReferenceDataController.class)
class ReferenceDataControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ReferenceDataService referenceDataService;

    @Test
    void getExercisesReturnsList() throws Exception {
        when(referenceDataService.getAllExercises()).thenReturn(List.of(new ExerciseDto(1,"Squat","",false,"Legs",null,null)));
        mockMvc.perform(get("/api/reference/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Squat"));
    }
}
