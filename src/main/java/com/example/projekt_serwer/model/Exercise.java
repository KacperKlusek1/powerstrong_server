package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "exercise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "exercise_category_id")
    private ExerciseCategory exerciseCategory;

    @ManyToMany
    @JoinTable(
            name = "exercise_has_movement_pattern",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "movement_pattern_id")
    )
    private Set<MovementPattern> movementPatterns;

    @ManyToMany
    @JoinTable(
            name = "exercise_target_muscle_group",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "target_muscle_group_id")
    )
    private Set<TargetMuscleGroup> targetMuscleGroups;

    @OneToMany(mappedBy = "exercise")
    private Set<PlannedExercise> plannedExercises;
}
