package pl.pollub.powerstrong_server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlannedExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "training_day_id", nullable = false)
    private TrainingDay trainingDay;

    @ManyToOne
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    private Integer exerciseOrder;

    private Integer plannedSets;

    private Integer plannedReps;

    private Double intensityPercentage;

    @ManyToOne
    @JoinColumn(name = "effort_type_id")
    private EffortType effortType;

    @OneToMany(mappedBy = "plannedExercise", cascade = CascadeType.ALL)
    private Set<ExecutedSet> executedSets;
}
