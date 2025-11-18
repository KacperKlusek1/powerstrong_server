package com.example.projekt_serwer.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDto implements Serializable {

    private Integer id;
    private String name;
    private String description;

    // Kategoria (spłaszczone, zgodnie z ustaleniami)
    private String categoryName;

    private List<Integer> movementPatternIds;
    private List<Integer> muscleGroupIds;
}