package pl.pollub.powerstrong_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pollub.powerstrong_server.enums.PlanStatus;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanFullDto implements Serializable {
    private Integer id;
    private String name;
    private String startDate;
    private Integer durationOfCycle;
    private PlanStatus status;
    private Integer trainingMethodId;
    private List<TrainingDayDto> trainingDays;
}