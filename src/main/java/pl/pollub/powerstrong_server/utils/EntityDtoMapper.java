package pl.pollub.powerstrong_server.utils;

import org.springframework.stereotype.Component;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.enums.PlanStatus;
import pl.pollub.powerstrong_server.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole() != null ? user.getRole().name() : null)
                .status(user.getStatus() != null ? user.getStatus().name() : null)
                .createDate(user.getCreateDate() != null ? user.getCreateDate().toString() : null)
                .completedTrainingPlansCount(user.getUserTrainingPlans() != null ? user.getUserTrainingPlans().size() : 0)
                .createdTrainingPlansCount(user.getCreatedTrainingPlans() != null ? user.getCreatedTrainingPlans().size() : 0)
                .build();
    }

    public ExerciseDto toExerciseDto(Exercise exercise) {
        return ExerciseDto.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .description(exercise.getDescription())
                .isBodyweight(exercise.getIsBodyweight())
                .categoryName(exercise.getExerciseCategory() != null ? exercise.getExerciseCategory().getName() : null)
                .movementPatternIds(exercise.getMovementPatterns() != null ?
                        exercise.getMovementPatterns().stream()
                                .map(MovementPattern::getId)
                                .collect(Collectors.toList()) :
                        List.of())
                .targetMuscleGroupIds(exercise.getTargetMuscleGroups() != null ?
                        exercise.getTargetMuscleGroups().stream()
                                .map(TargetMuscleGroup::getId)
                                .collect(Collectors.toList()) :
                        List.of())
                .build();
    }

    public ExerciseCategoryDto toExerciseCategoryDto(ExerciseCategory exerciseCategory) {
        return ExerciseCategoryDto.builder()
                .id(exerciseCategory.getId())
                .name(exerciseCategory.getName())
                .build();
    }

    public MovementPatternDto toMovementPatternDto(MovementPattern movementPattern) {
        return MovementPatternDto.builder()
                .id(movementPattern.getId())
                .name(movementPattern.getName())
                .build();
    }

    public TargetMuscleGroupDto toTargetMuscleGroupDto(TargetMuscleGroup targetMuscleGroup) {
        return TargetMuscleGroupDto.builder()
                .id(targetMuscleGroup.getId())
                .name(targetMuscleGroup.getName())
                .build();
    }

    public EffortTypeDto toEffortTypeDto(EffortType effortType) {
        return EffortTypeDto.builder()
                .id(effortType.getId())
                .name(effortType.getName())
                .description(effortType.getDescription())
                .build();
    }

    public TrainingMethodDto toTrainingMethodDto(TrainingMethod trainingMethod) {
        return TrainingMethodDto.builder()
                .id(trainingMethod.getId())
                .name(trainingMethod.getName())
                .durationOfCycle(trainingMethod.getDurationOfCycle())
                .description(trainingMethod.getDescription())
                .build();
    }

    public TrainingPlanFullDto toTrainingPlanFullDto(TrainingPlan trainingPlan, UserTrainingPlan userTrainingPlan) {
        String startDate = null;
        PlanStatus status = null;

        if (userTrainingPlan != null) {
            startDate = userTrainingPlan.getStartDate() != null ? userTrainingPlan.getStartDate().toString() : null;
            if (userTrainingPlan.getStatus() != null) {
                status = userTrainingPlan.getStatus();
            }
        }

        Integer duration = 0;
        if (trainingPlan.getTrainingMethod() != null) {
            duration = trainingPlan.getTrainingMethod().getDurationOfCycle();
        }

        List<TrainingDayDto> days = List.of();
        if (trainingPlan.getTrainingDays() != null) {
            days = trainingPlan.getTrainingDays().stream()
                    .map(this::toTrainingDayDto)
                    .collect(Collectors.toList());
        }

        return TrainingPlanFullDto.builder()
                .id(trainingPlan.getId())
                .name(trainingPlan.getName())
                .startDate(startDate)
                .status(status)
                .durationOfCycle(duration)
                .trainingDays(days)
                .build();
    }

    public TrainingDayDto toTrainingDayDto(TrainingDay trainingDay) {
        return TrainingDayDto.builder()
                .id(trainingDay.getId())
                .trainingPlanId(trainingDay.getTrainingPlan().getId())
                .dayName(trainingDay.getDayName())
                .dayOrder(trainingDay.getDayOrder())
                .daysGap(trainingDay.getDaysGap())
                .weekNumber(trainingDay.getWeekNumber())
                .plannedExercises(trainingDay.getPlannedExercises().stream()
                        .map(this::toPlannedExerciseDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public PlannedExerciseDto toPlannedExerciseDto(PlannedExercise plannedExercise) {
        return PlannedExerciseDto.builder()
                .id(plannedExercise.getId())
                .exerciseName(plannedExercise.getExercise().getName())
                .exerciseDescription(plannedExercise.getExercise().getDescription())
                .exerciseOrder(plannedExercise.getExerciseOrder())
                .plannedSets(plannedExercise.getPlannedSets())
                .plannedReps(plannedExercise.getPlannedReps())
                .effortType(plannedExercise.getEffortType() != null ? plannedExercise.getEffortType().getName() : null)
                .targetWeight(null)
                .suggestionType(null)
                .suggestionValue(null)
                .build();
    }
}