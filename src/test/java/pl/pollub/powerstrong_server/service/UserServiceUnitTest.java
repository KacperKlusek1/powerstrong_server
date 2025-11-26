package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.pollub.powerstrong_server.model.*;
import pl.pollub.powerstrong_server.repository.*;
import pl.pollub.powerstrong_server.utils.EntityDtoMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceUnitTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserTrainingPlanRepository userTrainingPlanRepository;
    @Mock
    UserExerciseMaxRepository userExerciseMaxRepository;
    @Mock
    TrainingPlanRepository trainingPlanRepository;
    @Mock
    ExecutedSetRepository executedSetRepository;
    @InjectMocks
    UserService userService;
    @Spy
    EntityDtoMapper mapper = new EntityDtoMapper();

    @BeforeEach void init() { MockitoAnnotations.openMocks(this); }

    @Test
    void findUserDetailsById_not_found_throws() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());
        var ex = assertThrows(RuntimeException.class, () -> userService.findUserDetailsById(99));
        assertTrue(ex.getMessage().contains("User not found"));
    }

    @Test
    void deleteUser_deletes_all_related_entities() {
        User u = new User(); u.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(u));
        when(userTrainingPlanRepository.findAllByUserId(1)).thenReturn(List.of());
        when(userExerciseMaxRepository.findAllByUserId(1)).thenReturn(List.of());
        when(trainingPlanRepository.findByCreatedBy(u)).thenReturn(List.of());

        userService.deleteUser(1);

        verify(userRepository).delete(u);
    }

    @Test
    void deleteUser_with_history_deletes_history_and_executedsets() {
        User u = new User(); u.setId(2);
        UserTrainingPlan utp = new UserTrainingPlan();
        when(userRepository.findById(2)).thenReturn(Optional.of(u));
        when(userTrainingPlanRepository.findAllByUserId(2)).thenReturn(List.of(utp));
        when(userExerciseMaxRepository.findAllByUserId(2)).thenReturn(List.of());
        when(trainingPlanRepository.findByCreatedBy(u)).thenReturn(List.of());

        userService.deleteUser(2);

        verify(executedSetRepository).deleteByUserTrainingPlanIn(anyList());
        verify(userTrainingPlanRepository).deleteAll(anyList());
        verify(userRepository).delete(u);
    }
}
