package pl.pollub.powerstrong_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TrainingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "training_plan_id", nullable = false)
    private TrainingPlan trainingPlan;

    private Integer weekNumber;

    @NotBlank
    private String dayName;

    private Integer dayOrder;

    private Integer daysGap;

    @OneToMany(mappedBy = "trainingDay", cascade = CascadeType.ALL)
    private Set<PlannedExercise> plannedExercises;
}
