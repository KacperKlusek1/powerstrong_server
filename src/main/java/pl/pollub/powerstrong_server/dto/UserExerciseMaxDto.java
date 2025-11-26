package pl.pollub.powerstrong_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExerciseMaxDto {
    private Integer exerciseId;
    private String exerciseName;
    private Double currentOneRepMax;
    private String lastUpdatedDate;
    private boolean isBodyweight;
}
