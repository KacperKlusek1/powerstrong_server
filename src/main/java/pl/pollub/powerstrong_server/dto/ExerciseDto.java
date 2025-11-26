package pl.pollub.powerstrong_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDto {
    private Integer id;
    private String name;
    private String description;
    private boolean isBodyweight;
    private String categoryName;
    private List<Integer> movementPatternIds;
    private List<Integer> targetMuscleGroupIds;
}