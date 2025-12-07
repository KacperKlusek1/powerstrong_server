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
public class TrainingDayDto {
    private Integer id;
    private Integer trainingPlanId;
    private String dayName;
    private Integer dayOrder;
    private Integer daysGap;
    private Integer weekNumber;
    private List<PlannedExerciseDto> plannedExercises;
}