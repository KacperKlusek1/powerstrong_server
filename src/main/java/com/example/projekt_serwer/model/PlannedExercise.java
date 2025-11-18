package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "planned_exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "exercise_order")
    private Integer exerciseOrder;

    @Column(name = "planned_sets")
    private Integer plannedSets;

    @Column(name = "planned_reps")
    private Integer plannedReps;

    @Column(name = "planned_weight")
    private Double plannedWeight;

    @ManyToOne
    @JoinColumn(name = "effort_type_id")
    private EffortType effortType;

    @OneToMany(mappedBy = "plannedExercise", cascade = CascadeType.ALL)
    private Set<ExecutedSet> executedSets;
}
