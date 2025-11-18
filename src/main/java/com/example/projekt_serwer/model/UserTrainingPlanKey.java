package com.example.projekt_serwer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserTrainingPlanKey implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "training_plan_id")
    private Integer trainingPlanId;
}
