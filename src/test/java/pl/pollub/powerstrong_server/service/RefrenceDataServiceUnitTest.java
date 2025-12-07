package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.pollub.powerstrong_server.repository.*;
import pl.pollub.powerstrong_server.utils.EntityDtoMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReferenceDataServiceUnitTest {

    @Mock
    ExerciseRepository exerciseRepository;
    @Mock
    MovementPatternRepository movementPatternRepository;
    @Mock
    TargetMuscleGroupRepository targetMuscleGroupRepository;
    @Mock
    ExerciseCategoryRepository exerciseCategoryRepository;
    @Mock
    EffortTypeRepository effortTypeRepository;
    @Mock
    TrainingMethodRepository trainingMethodRepository;

    @InjectMocks
    ReferenceDataService service;
    @Spy
    EntityDtoMapper mapper = new EntityDtoMapper();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllExercises_calls_repo_and_maps() {
        when(exerciseRepository.findAllWithDetails()).thenReturn(List.of());
        var res = service.getAllExercises();
        assertNotNull(res);
        verify(exerciseRepository).findAllWithDetails();
    }

    @Test
    void other_getters_call_their_repos() {
        when(exerciseCategoryRepository.findAll()).thenReturn(List.of());
        when(movementPatternRepository.findAll()).thenReturn(List.of());
        when(targetMuscleGroupRepository.findAll()).thenReturn(List.of());
        when(effortTypeRepository.findAll()).thenReturn(List.of());
        when(trainingMethodRepository.findAll()).thenReturn(List.of());

        assertNotNull(service.getAllExerciseCategories());
        assertNotNull(service.getAllMovementPatterns());
        assertNotNull(service.getAllTargetMuscleGroups());
        assertNotNull(service.getAllEffortTypes());
        assertNotNull(service.getAllTrainingMethods());

        verify(exerciseCategoryRepository).findAll();
        verify(movementPatternRepository).findAll();
        verify(targetMuscleGroupRepository).findAll();
        verify(effortTypeRepository).findAll();
        verify(trainingMethodRepository).findAll();
    }
}
