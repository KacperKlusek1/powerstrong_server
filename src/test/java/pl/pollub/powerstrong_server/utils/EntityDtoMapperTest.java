package pl.pollub.powerstrong_server.utils;

import org.junit.jupiter.api.Test;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.model.*;

import java.util.Set;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntityDtoMapperTest {

    EntityDtoMapper mapper = new EntityDtoMapper();

    @Test
    void mapTrainingPlanToDto_maps_fields_and_children() {
        TrainingPlan plan = new TrainingPlan();
        plan.setId(1);
        plan.setName("Plan A");

        TrainingDay day = new TrainingDay();
        day.setId(10);
        day.setDayName("Day A");
        day.setTrainingPlan(plan);

        Exercise ex = new Exercise();
        ex.setId(100);
        ex.setName("Bench Press");
        ex.setIsBodyweight(false);

        PlannedExercise pe = new PlannedExercise();
        pe.setId(50);
        pe.setExercise(ex);
        pe.setExerciseOrder(1);
        pe.setPlannedReps(5);
        pe.setIntensityPercentage(0.75);

        day.setPlannedExercises(Set.of(pe));
        plan.setTrainingDays(Set.of(day));

        TrainingPlanFullDto dto = mapper.toTrainingPlanFullDto(plan, null);

        assertEquals(1, dto.getId());
        assertEquals("Plan A", dto.getName());
        assertEquals(1, dto.getTrainingDays().size());
        assertEquals("Day A", dto.getTrainingDays().get(0).getDayName());
        assertEquals("Bench Press", dto.getTrainingDays().get(0).getPlannedExercises().get(0).getExerciseName());
    }

    @Test
    void mapExerciseEntityToDto_maps_all_fields() {
        MovementPattern mp = new MovementPattern();
        mp.setName("Squat");
        mp.setId(17);
        ExerciseCategory cat = new ExerciseCategory();
        cat.setName("General");

        Exercise ex = new Exercise();
        ex.setId(1);
        ex.setName("Front Squat");
        ex.setDescription("desc");
        ex.setMovementPatterns(Set.of(mp));
        ex.setExerciseCategory(cat);
        ex.setIsBodyweight(false);
        TargetMuscleGroup tmg = new TargetMuscleGroup();
        tmg.setName("Quads");
        ex.setTargetMuscleGroups(Set.of(tmg));

        ExerciseDto dto = mapper.toExerciseDto(ex);

        assertEquals("Front Squat", dto.getName());
        assertFalse(dto.isBodyweight());
        assertEquals(17, dto.getMovementPatternIds().get(0));
        assertEquals("General", dto.getCategoryName());
        assertEquals(1, dto.getTargetMuscleGroupIds().size());
    }
}