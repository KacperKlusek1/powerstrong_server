package pl.pollub.powerstrong_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExecutedSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_training_plan_id", nullable = false)
    private UserTrainingPlan userTrainingPlan;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "planned_exercise_id")
    private PlannedExercise plannedExercise;

    @NotNull
    private int setNumber;

    @NotNull
    private int executedReps;

    @NotNull
    private double weightUsed;

    @NotNull
    private LocalDateTime executionDate;
}