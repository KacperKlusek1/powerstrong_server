package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.*;
import org.mockito.*;
import pl.pollub.powerstrong_server.model.*;
import pl.pollub.powerstrong_server.repository.*;

import java.lang.reflect.InvocationTargetException;
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
    void init() {
        MockitoAnnotations.openMocks(this);
        service = mock(TrainingService.class, CALLS_REAL_METHODS);
        // Inject repository
        org.springframework.test.util.ReflectionTestUtils.setField(service, "userExerciseMaxRepository", userExerciseMaxRepository);

        try {
            updateUserMaxMethod = TrainingService.class.getDeclaredMethod("updateUserMaxIfNeeded", User.class, Exercise.class, double.class, int.class);
        } catch (NoSuchMethodException e) {
            System.err.println("Method not found: " + e.getMessage());
        }

        updateUserMaxMethod.setAccessible(true);
    }

    @Test
    void updateUserMax_creates_new_record_when_none_exists() {
        Exercise ex = new Exercise();
        ex.setId(1);
        User u = new User();
        u.setId(22);

        when(userExerciseMaxRepository.findByUserIdAndExerciseId(22, 1))
                .thenReturn(Optional.empty());

        updateUserMaxIfNeededExecution(u, ex, 100.0, 1);

        verify(userExerciseMaxRepository).save(any(UserExerciseMax.class));
    }

    @Test
    void updateUserMax_updates_only_if_new_max_higher() {
        Exercise ex = new Exercise();
        ex.setId(1);
        User u = new User();
        u.setId(22);

        UserExerciseMax max = new UserExerciseMax();
        max.setCurrentOneRepMax(80.0);

        when(userExerciseMaxRepository.findByUserIdAndExerciseId(22, 1))
                .thenReturn(Optional.of(max));

        updateUserMaxIfNeededExecution(u, ex, 100.0, 1);

        assertEquals(100.0, max.getCurrentOneRepMax());
        verify(userExerciseMaxRepository).save(max);
    }

    @Test
    void updateUserMax_does_nothing_when_value_lower() {
        Exercise ex = new Exercise();
        ex.setId(1);
        User u = new User();
        u.setId(22);

        UserExerciseMax max = new UserExerciseMax();
        max.setCurrentOneRepMax(120.0);

        when(userExerciseMaxRepository.findByUserIdAndExerciseId(22, 1))
                .thenReturn(Optional.of(max));

        updateUserMaxIfNeededExecution(u, ex, 100.0, 1);

        verify(userExerciseMaxRepository, never()).save(any());
        assertEquals(120.0, max.getCurrentOneRepMax());
    }

    private void updateUserMaxIfNeededExecution(User u, Exercise ex, double weight, int reps) {
        try {
            updateUserMaxMethod.invoke(TrainingService.class, u, ex, weight, reps);
        } catch (IllegalAccessException e) {
            System.err.println("Cannot access method: " + e.getMessage());
        } catch (InvocationTargetException e) {
            System.err.println("Method threw an exception: " + e.getCause());
        }
    }
}
