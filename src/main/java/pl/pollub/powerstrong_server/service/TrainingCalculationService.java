package pl.pollub.powerstrong_server.service;

import org.springframework.stereotype.Service;
import pl.pollub.powerstrong_server.dto.PlannedExerciseDto;
import pl.pollub.powerstrong_server.enums.SuggestionType;
import pl.pollub.powerstrong_server.model.EffortType;
import pl.pollub.powerstrong_server.model.PlannedExercise;
import pl.pollub.powerstrong_server.model.UserExerciseMax;

import java.util.Optional;

@Service
public class TrainingCalculationService {
    public void calculateTargetWeightForDto(PlannedExerciseDto plannedExerciseDto, PlannedExercise plannedExercise, Optional<UserExerciseMax> userExerciseMax) {
        if (plannedExercise == null) return;

        if (Boolean.TRUE.equals(plannedExercise.getExercise().getIsBodyweight())) {
            plannedExerciseDto.setTargetWeight(0.0);
            plannedExerciseDto.setSuggestionType(SuggestionType.BODYWEIGHT);
            plannedExerciseDto.setSuggestionValue(mapEffortToRpeValue(plannedExercise.getEffortType()));
            return;
        }

        if (plannedExercise.getIntensityPercentage() == null) {
            plannedExerciseDto.setTargetWeight(null);
            plannedExerciseDto.setSuggestionType(SuggestionType.FIND_MAX);
            plannedExerciseDto.setSuggestionValue((double) plannedExercise.getPlannedReps());
            return;
        }

        if (userExerciseMax.isPresent()) {
            double max = userExerciseMax.get().getCurrentOneRepMax();
            plannedExerciseDto.setTargetWeight(roundToNearest2_5(max * plannedExercise.getIntensityPercentage()));
            plannedExerciseDto.setSuggestionType(SuggestionType.PERCENT);
            plannedExerciseDto.setSuggestionValue(plannedExercise.getIntensityPercentage());
            return;
        }

        plannedExerciseDto.setTargetWeight(null);
        plannedExerciseDto.setSuggestionType(SuggestionType.RPE);
        plannedExerciseDto.setSuggestionValue(mapEffortToRpeValue(plannedExercise.getEffortType()));

    }

    public Double mapEffortToRpeValue(EffortType effortType) {
        if (effortType == null) return 8.0;
        return switch (effortType.getName()) {
            case "Max" -> 10.0;
            case "Volume" -> 8.0;
            case "Dynamic" -> 6.5;
            case "Speed" -> 6.0;
            default -> 8.0;
        };
    }

    private double roundToNearest2_5(double weight) {
        return Math.round(weight / 2.5) * 2.5;
    }
}