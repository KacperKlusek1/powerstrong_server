package com.example.powerstrong_server.dto;

import com.example.powerstrong_server.enums.SuggestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlannedExerciseDto {
    private Integer id;
    private String exerciseName;
    private String exerciseDescription;
    private Integer plannedSets;
    private Integer plannedReps;
    private Double targetWeight;
    private SuggestionType suggestionType;
    private Double suggestionValue;
    private Integer exerciseOrder;
    private String effortType;
}