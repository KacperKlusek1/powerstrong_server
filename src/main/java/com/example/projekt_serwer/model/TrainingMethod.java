package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "training_method")
@Getter
@Setter
@NoArgsConstructor
public class TrainingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "duration_of_cycle")
    private Integer durationOfCycle;

    private String description;

    @OneToMany(mappedBy = "trainingMethod")
    private Set<TrainingPlan> trainingPlans;
}
