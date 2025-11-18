package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "executed_set")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecutedSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_training_plan_user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "user_training_plan_plan_id", referencedColumnName = "training_plan_id")
    })
    private UserTrainingPlan userTrainingPlan;

    @ManyToOne
    @JoinColumn(name = "planned_exercise_id", nullable = false)
    private PlannedExercise plannedExercise;

    @NotNull
    @Column(name = "set_number", nullable = false)
    private int setNumber;

    @NotNull
    @Column(name = "executed_reps", nullable = false)
    private int executedReps;

    @NotNull
    @Column(name = "weight_used", nullable = false)
    private double weightUsed;

    @Column(name = "execution_date", nullable = false)
    private LocalDateTime executionDate;
}