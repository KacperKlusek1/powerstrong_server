package pl.pollub.powerstrong_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pollub.powerstrong_server.enums.PlanStatus;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserTrainingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_plan_id", nullable = false)
    private TrainingPlan trainingPlan;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PlanStatus status = PlanStatus.ACTIVE;

    private Boolean wasTrackingNutrition = null;

    private Boolean wasTrackingSleep = null;

    private Integer averageHoursOfSleep = null;

    private Integer personalEvaluation = null;

    @OneToMany(mappedBy = "userTrainingPlan")
    private Set<ExecutedSet> executedSets;
}
