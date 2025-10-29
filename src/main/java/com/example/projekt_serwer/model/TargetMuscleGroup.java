package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "target_muscle_group")
@Getter
@Setter
@NoArgsConstructor
public class TargetMuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "targetMuscleGroups")
    private Set<Exercise> exercises;
}
