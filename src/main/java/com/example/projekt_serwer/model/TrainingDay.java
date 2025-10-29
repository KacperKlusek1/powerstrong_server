package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "training_day")
@Getter
@Setter
@NoArgsConstructor
public class TrainingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "training_plan_id", nullable = false)
    private TrainingPlan trainingPlan;

    @Column(name = "week_number")
    private Integer weekNumber;

    @NotBlank
    @Column(name = "day_name", nullable = false)
    private String dayName;

    @Column(name = "day_order")
    private Integer dayOrder;

    @OneToMany(mappedBy = "trainingDay", cascade = CascadeType.ALL)
    private Set<PlannedExercise> plannedExercises;
}
