package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.*;
import org.mockito.*;
import pl.pollub.powerstrong_server.model.*;
import pl.pollub.powerstrong_server.repository.*;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TrainingServiceRecordCalculationTest {

    @Mock
    UserExerciseMaxRepository userExerciseMaxRepository;

    TrainingService service;

    Method updateUserMaxMethod;

    @BeforeEach
    void init() throws Exception {
        MockitoAnnotations.openMocks(this);
        service = mock(TrainingService.class, CALLS_REAL_METHODS);

        org.springframework.test.util.ReflectionTestUtils.setField(service, "userExerciseMaxRepository", userExerciseMaxRepository);

        updateUserMaxMethod = TrainingService.class.getDeclaredMethod("updateUserMaxIfNeeded", User.class, Exercise.class, double.class, int.class);
        updateUserMaxMethod.setAccessible(true);
    }

    @Test
    void updateUserMax_creates_new_record_when_none_exists() throws Exception {
        Exercise ex = new Exercise();
        ex.setId(1);
        ex.setIsBodyweight(false);
        User u = new User();
        u.setId(22);

        when(userExerciseMaxRepository.findByUserIdAndExerciseId(22, 1))
                .thenReturn(Optional.empty());

        updateUserMaxMethod.invoke(service, u, ex, 100.0, 1);

        verify(userExerciseMaxRepository).save(any(UserExerciseMax.class));
    }

    @Test
    void updateUserMax_updates_only_if_new_max_higher() throws Exception {
        Exercise ex = new Exercise();
        ex.setId(1);
        ex.setIsBodyweight(false);
        User u = new User();
        u.setId(22);

        UserExerciseMax max = new UserExerciseMax();
        max.setCurrentOneRepMax(80.0);

        when(userExerciseMaxRepository.findByUserIdAndExerciseId(22, 1))
                .thenReturn(Optional.of(max));

        updateUserMaxMethod.invoke(service, u, ex, 100.0, 1);

        assertEquals(103.5, max.getCurrentOneRepMax());
        verify(userExerciseMaxRepository).save(max);
    }

    @Test
    void updateUserMax_does_nothing_when_value_lower() throws Exception {
        Exercise ex = new Exercise();
        ex.setId(1);
        ex.setIsBodyweight(false);
        User u = new User();
        u.setId(22);

        UserExerciseMax max = new UserExerciseMax();
        max.setId(99);
        max.setCurrentOneRepMax(120.0);

        when(userExerciseMaxRepository.findByUserIdAndExerciseId(22, 1))
                .thenReturn(Optional.of(max));

        updateUserMaxMethod.invoke(service, u, ex, 100.0, 1);

        verify(userExerciseMaxRepository, never()).save(any());
        assertEquals(120.0, max.getCurrentOneRepMax());
    }
}