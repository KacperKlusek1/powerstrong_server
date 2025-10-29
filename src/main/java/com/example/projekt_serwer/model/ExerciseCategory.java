package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "exercise_category")
@Getter
@Setter
@NoArgsConstructor
public class ExerciseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "exerciseCategory")
    private Set<Exercise> exercises;
}
