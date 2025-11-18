package com.example.projekt_serwer.mapper;

import com.example.projekt_serwer.dto.*;
import com.example.projekt_serwer.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus(),
                user.getCreateDate() != null ? user.getCreateDate().toString() : null,
                user.getUserTrainingPlans() != null ? user.getUserTrainingPlans().size() : 0,
                user.getCreatedTrainingPlans() != null ? user.getCreatedTrainingPlans().size() : 0
        );
    }

    public ExerciseDto toExerciseDto(Exercise exercise) {
        return new ExerciseDto(
                exercise.getId(),
                exercise.getName(),
                exercise.getDescription(),
                exercise.getExerciseCategory() != null ? exercise.getExerciseCategory().getName() : null,
                exercise.getMovementPatterns() != null ?
                        exercise.getMovementPatterns().stream()
                                .map(pattern -> pattern.getId())
                                .collect(Collectors.toList()) :
                        List.of(),
                exercise.getTargetMuscleGroups() != null ?
                        exercise.getTargetMuscleGroups().stream()
                                .map(group -> group.getId())
                                .collect(Collectors.toList()) :
                        List.of()
        );
    }

    public MovementPatternDto toMovementPatternDto(MovementPattern pattern) {
        return new MovementPatternDto(
                pattern.getId(),
                pattern.getName()
        );
    }

    public TargetMuscleGroupDto toTargetMuscleGroupDto(TargetMuscleGroup group) {
        return new TargetMuscleGroupDto(
                group.getId(),
                group.getName()
        );
    }

    public PlannedExerciseDto toPlannedExerciseDto(PlannedExercise pe) {
        return new PlannedExerciseDto(
                pe.getId(),
                pe.getExercise().getName(),
                pe.getExercise().getDescription(),
                pe.getPlannedSets(),
                pe.getPlannedReps(),
                pe.getPlannedWeight(),
                pe.getExerciseOrder(),
                pe.getEffortType() != null ? pe.getEffortType().getName() : null
        );
    }

    public TrainingDayDto toTrainingDayDto(TrainingDay day) {
        TrainingDayDto dto = new TrainingDayDto();
        dto.setId(day.getId());
        dto.setTrainingPlanId(day.getTrainingPlan().getId());
        dto.setDayName(day.getDayName());
        dto.setDayOrder(day.getDayOrder());
        dto.setWeekNumber(day.getWeekNumber());
        // Mapowanie pod-listy
        dto.setPlannedExercises(day.getPlannedExercises().stream()
                .map(this::toPlannedExerciseDto)
                .collect(Collectors.toList()));
        return dto;
    }

    /**
     * Główny mapper, który buduje TrainingPlanFullDto na podstawie encji JPA.
     */
    public TrainingPlanFullDto toTrainingPlanFullDto(TrainingPlan plan, UserTrainingPlan userPlan) {
        TrainingPlanFullDto dto = new TrainingPlanFullDto();
        dto.setId(plan.getId());
        dto.setName(plan.getName());
        dto.setStartDate(userPlan.getStartDate().toString());
        if (plan.getTrainingMethod() != null) {
            dto.setDurationOfCycle(plan.getTrainingMethod().getDurationOfCycle());
        }
        // Mapowanie listy dni
        dto.setTrainingDays(plan.getTrainingDays().stream()
                .map(this::toTrainingDayDto)
                .collect(Collectors.toList()));

        return dto;
    }


    // =================================================================================
    // MAPOWANIE Z DTO (ZAPISYWANIE DANYCH Z APLIKACJI ANDROID)
    // =================================================================================

    /**
     * Konwertuje DTO na Encję. Wymaga referencji do encji powiązanych.
     */
    public ExecutedSet toExecutedSetEntity(ExecutedSetDto dto, PlannedExercise pe, UserTrainingPlan utp) {
        ExecutedSet entity = new ExecutedSet();
        entity.setPlannedExercise(pe);
        entity.setUserTrainingPlan(utp);
        entity.setSetNumber(dto.getSetNumber());
        entity.setExecutedReps(dto.getExecutedReps());
        entity.setWeightUsed(dto.getWeightUsed());
        entity.setExecutionDate(LocalDateTime.now()); // Użyj czasu serwera
        return entity;
    }
}