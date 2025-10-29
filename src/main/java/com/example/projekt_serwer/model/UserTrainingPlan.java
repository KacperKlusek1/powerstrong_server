package com.example.projekt_serwer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user_training_plan")
@Getter
@Setter
@NoArgsConstructor
public class UserTrainingPlan {
    @EmbeddedId
    private UserTrainingPlanKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("trainingPlanId")
    @JoinColumn(name = "training_plan_id")
    private TrainingPlan trainingPlan;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @Column(name = "was_tracking_nutrition")
    private Boolean wasTrackingNutrition = null;

    @Column(name = "was_tracking_sleep")
    private Boolean wasTrackingSleep = null;

    @Column(name = "average_hours_of_sleep")
    private Integer averageHoursOfSleep = null;

    @Column(name = "personal_evaluation")
    private Integer personalEvaluation = null;

    @OneToMany(mappedBy = "userTrainingPlan")
    private Set<ExecutedSet> executedSets;
}
