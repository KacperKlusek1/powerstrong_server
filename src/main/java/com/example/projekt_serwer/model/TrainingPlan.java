package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "training_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "training_method_id")
    private TrainingMethod trainingMethod;

    @Column(name = "is_preset", nullable = false)
    private Boolean isPreset = false;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL)
    private Set<TrainingDay> trainingDays;

    @OneToMany(mappedBy = "trainingPlan")
    private Set<UserTrainingPlan> userTrainingPlans;
}
