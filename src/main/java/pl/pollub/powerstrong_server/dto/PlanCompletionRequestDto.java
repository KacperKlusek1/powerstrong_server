package pl.pollub.powerstrong_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanCompletionRequestDto implements Serializable {
    private boolean trackingNutrition;
    private boolean trackingSleep;
    private Double averageHoursOfSleep;
    private Integer personalEvaluation;
}
