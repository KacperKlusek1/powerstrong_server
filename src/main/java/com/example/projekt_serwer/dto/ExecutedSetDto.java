package com.example.projekt_serwer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExecutedSetDto {
    private Integer plannedExerciseId;
    private int setNumber;
    private int executedReps;
    private double weightUsed;
}