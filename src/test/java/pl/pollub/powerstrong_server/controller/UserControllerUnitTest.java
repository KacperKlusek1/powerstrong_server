package pl.pollub.powerstrong_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import pl.pollub.powerstrong_server.config.TestSecurityConfig;
import pl.pollub.powerstrong_server.dto.UserDto;
import pl.pollub.powerstrong_server.model.User;
import pl.pollub.powerstrong_server.service.JwtService;
import pl.pollub.powerstrong_server.service.TrainingService;
import pl.pollub.powerstrong_server.service.UserService;
import pl.pollub.powerstrong_server.utils.JwtAuthenticationFilter;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerUnitTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    UserService userService;
    @MockBean
    TrainingService trainingService;
    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    JwtService jwtService;

    @Test
    void getCurrentUser_returns_userDto() throws Exception {
        Authentication auth = mock(Authentication.class);
        User u = new User(); u.setId(1);
        when(auth.getPrincipal()).thenReturn(u);
        when(userService.findUserDetailsById(1)).thenReturn(new UserDto(1, "u","e","USER","ACTIVE",null,0,0));

        mvc.perform(get("/api/user/me").principal(auth))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("u"));
    }

    @Test
    void deleteUser_calls_service_and_returns_ok() throws Exception {
        Authentication auth = mock(Authentication.class);
        User u = new User(); u.setId(2);
        when(auth.getPrincipal()).thenReturn(u);

        mvc.perform(delete("/api/user/me").principal(auth))
                .andExpect(status().isOk());

        verify(userService).deleteUser(2);
    }

    @Test
    void getRecords_calls_trainingService() throws Exception {
        Authentication auth = mock(Authentication.class);
        User u = new User(); u.setId(3);
        when(auth.getPrincipal()).thenReturn(u);
        when(trainingService.getUserRecords(3)).thenReturn(List.of());
        mvc.perform(get("/api/user/records").principal(auth))
                .andExpect(status().isOk());
    }
}
