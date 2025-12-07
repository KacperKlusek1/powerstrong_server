package pl.pollub.powerstrong_server.service;

import org.junit.jupiter.api.Test;
import pl.pollub.powerstrong_server.dto.PlannedExerciseDto;
import pl.pollub.powerstrong_server.model.EffortType;
import pl.pollub.powerstrong_server.model.Exercise;
import pl.pollub.powerstrong_server.model.PlannedExercise;
import pl.pollub.powerstrong_server.model.UserExerciseMax;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TrainingCalculationServiceUnitTest {

    TrainingCalculationService svc = new TrainingCalculationService();

    @Test
    void null_planned_exercise_does_nothing() {
        PlannedExerciseDto dto = new PlannedExerciseDto();
        svc.calculateTargetWeightForDto(dto, null, Optional.empty());
        assertNull(dto.getTargetWeight());
    }

    @Test
    void bodyweight_sets_zero_and_bodyweight_suggestion() {
        PlannedExercise p = new PlannedExercise();
        Exercise ex = new Exercise();
        ex.setIsBodyweight(true);
        p.setExercise(ex);
        EffortType ef = new EffortType();
        ef.setName("Volume");
        p.setEffortType(ef);

        PlannedExerciseDto dto = new PlannedExerciseDto();
        svc.calculateTargetWeightForDto(dto, p, Optional.empty());

        assertEquals(0.0, dto.getTargetWeight());
        assertNotNull(dto.getSuggestionType());
        assertEquals(8.0, dto.getSuggestionValue());
    }

    @Test
    void intensity_null_sets_find_max() {
        PlannedExercise p = new PlannedExercise();
        Exercise ex = new Exercise();
        ex.setIsBodyweight(false);
        p.setExercise(ex);
        p.setIntensityPercentage(null);
        p.setPlannedReps(5);

        PlannedExerciseDto dto = new PlannedExerciseDto();
        svc.calculateTargetWeightForDto(dto, p, Optional.empty());
        assertNull(dto.getTargetWeight());
        assertEquals(5.0, dto.getSuggestionValue());
    }

    @Test
    void has_user_max_uses_percent_and_rounding() {
        PlannedExercise p = new PlannedExercise();
        Exercise ex = new Exercise();
        ex.setIsBodyweight(false);
        p.setExercise(ex);
        p.setIntensityPercentage(0.8);
        p.setPlannedReps(5);

        UserExerciseMax max = new UserExerciseMax();
        max.setCurrentOneRepMax(101.0);

        PlannedExerciseDto dto = new PlannedExerciseDto();
        svc.calculateTargetWeightForDto(dto, p, Optional.of(max));

        assertEquals(80.0, dto.getTargetWeight());
        assertEquals(0.8, dto.getSuggestionValue());
    }

    @Test
    void mapEffortToRpeValue_defaults_and_knowns() {
        assertEquals(8.0, svc.mapEffortToRpeValue(null));
        EffortType e = new EffortType();
        e.setName("Max");
        assertEquals(10.0, svc.mapEffortToRpeValue(e));
        e.setName("Dynamic");
        assertEquals(6.5, svc.mapEffortToRpeValue(e));
    }
}
