package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "effort_type")
@Getter
@Setter
@NoArgsConstructor
public class EffortType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String description;

    @OneToMany(mappedBy = "effortType")
    private Set<PlannedExercise> plannedExercises;
}
