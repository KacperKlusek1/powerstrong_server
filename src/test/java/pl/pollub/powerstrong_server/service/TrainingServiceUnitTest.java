package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.enums.PlanStatus;
import pl.pollub.powerstrong_server.exception.ResourceNotFoundException;
import pl.pollub.powerstrong_server.model.*;
import pl.pollub.powerstrong_server.repository.*;
import pl.pollub.powerstrong_server.utils.EntityDtoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainingServiceUnitTest {

    @Mock UserTrainingPlanRepository userTrainingPlanRepository;
    @Mock PlannedExerciseRepository plannedExerciseRepository;
    @Mock ExecutedSetRepository executedSetRepository;
    @Mock TrainingPlanRepository trainingPlanRepository;
    @Mock UserRepository userRepository;
    @Mock UserExerciseMaxRepository userExerciseMaxRepository;
    @Mock ExerciseRepository exerciseRepository;
    @Mock TrainingMethodRepository trainingMethodRepository;
    @Mock EffortTypeRepository effortTypeRepository;
    @Mock TrainingCalculationService calculationService;
    @InjectMocks TrainingService service;
    @Spy EntityDtoMapper mapper = new EntityDtoMapper();

    @BeforeEach void init() { MockitoAnnotations.openMocks(this); }

    @Test
    void getActivePlanForUser_not_found_throws() {
        when(userTrainingPlanRepository.findByUserIdAndStatus(1, PlanStatus.ACTIVE)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getActivePlanForUser(1));
    }

    @Test
    void cancelActivePlan_sets_cancelled_and_endDate() {
        UserTrainingPlan utp = new UserTrainingPlan();
        utp.setStatus(PlanStatus.ACTIVE);
        when(userTrainingPlanRepository.findByUserIdAndStatus(1, PlanStatus.ACTIVE)).thenReturn(Optional.of(utp));

        service.cancelActivePlan(1);
        assertEquals(PlanStatus.CANCELLED, utp.getStatus());
        assertNotNull(utp.getEndDate());
        verify(userTrainingPlanRepository).save(utp);
    }

    @Test
    void completeActivePlan_changes_status_and_populates_fields() {
        UserTrainingPlan utp = new UserTrainingPlan();
        utp.setStatus(PlanStatus.ACTIVE);
        when(userTrainingPlanRepository.findByUserIdAndStatus(1, PlanStatus.ACTIVE)).thenReturn(Optional.of(utp));
        PlanCompletionRequestDto dto = new PlanCompletionRequestDto(true, true, 7.3, 4);

        service.completeActivePlan(1, dto);

        assertEquals(PlanStatus.COMPLETED, utp.getStatus());
        assertEquals(7, utp.getAverageHoursOfSleep());
        assertEquals(4, utp.getPersonalEvaluation());
        verify(userTrainingPlanRepository).save(utp);
    }

    @Test
    void saveExecutedSets_when_no_sets_returns_immediately() {
        service.saveExecutedSets(1, Collections.emptyList());
        verifyNoInteractions(executedSetRepository);
    }

    @Test
    void saveExecutedSets_when_user_missing_throws() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        ExecutedSetDto dto = new ExecutedSetDto(1,1,1,10.0);
        assertThrows(ResourceNotFoundException.class, () -> service.saveExecutedSets(1, List.of(dto)));
    }

    @Test
    void saveExecutedSets_saves_and_updates_user_max() {
        User user = new User(); user.setId(1);
        UserTrainingPlan utp = new UserTrainingPlan(); utp.setId(99L);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userTrainingPlanRepository.findByUserIdAndStatus(1, PlanStatus.ACTIVE)).thenReturn(Optional.of(utp));

        PlannedExercise planned = new PlannedExercise();
        Exercise ex = new Exercise();
        ex.setId(5);
        ex.setIsBodyweight(false);
        planned.setExercise(ex);
        when(plannedExerciseRepository.findById(1)).thenReturn(Optional.of(planned));

        when(userExerciseMaxRepository.findByUserIdAndExerciseId(1, 5)).thenReturn(Optional.empty());

        ExecutedSetDto dto = new ExecutedSetDto(1,1,5,100.0);
        service.saveExecutedSets(1, List.of(dto));

        verify(executedSetRepository).saveAll(anyList());
        verify(userExerciseMaxRepository, atLeastOnce()).save(any());
    }

    @Test
    void assignPlanToUser_when_user_has_active_plan_throws() {
        when(userTrainingPlanRepository.findByUserIdAndStatus(1, PlanStatus.ACTIVE)).thenReturn(Optional.of(new UserTrainingPlan()));
        assertThrows(IllegalStateException.class, () -> service.assignPlanToUser(1,1,null));
    }

    @Test
    void createCustomPlan_builds_and_assigns() {
        User u = new User();
        u.setId(2);
        when(userRepository.findById(2)).thenReturn(Optional.of(u));

        TrainingMethod tm = new TrainingMethod();
        tm.setId(5);
        tm.setDurationOfCycle(1);
        when(trainingMethodRepository.findById(5)).thenReturn(Optional.of(tm));

        Exercise ex = new Exercise(); ex.setId(1); ex.setName("E");
        when(exerciseRepository.findById(1)).thenReturn(Optional.of(ex));

        when(trainingPlanRepository.save(any(TrainingPlan.class))).thenAnswer(inv -> {
            TrainingPlan p = inv.getArgument(0);
            p.setId(100);
            return p;
        });

        when(trainingPlanRepository.findById(100)).thenAnswer(inv -> {
            TrainingPlan p = new TrainingPlan();
            p.setId(100);
            return Optional.of(p);
        });

        PlannedExerciseDto ped = new PlannedExerciseDto(1, "E", null,1,5,null,null,null,1,"Volume");
        TrainingDayDto dayDto = new TrainingDayDto(1, 1, "D", 1, 0, 1, List.of(ped));
        TrainingPlanFullDto dto = new TrainingPlanFullDto(1, "Custom", "2025-01-01", null, null, 5, List.of(dayDto));

        service.createCustomPlan(dto, 2);

        verify(userTrainingPlanRepository, atLeastOnce()).save(any(UserTrainingPlan.class));
    }
}
