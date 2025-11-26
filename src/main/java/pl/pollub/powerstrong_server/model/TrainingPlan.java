package pl.pollub.powerstrong_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TrainingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "training_method_id")
    private TrainingMethod trainingMethod;

    @NotNull
    private Boolean isPreset = false;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL)
    private Set<TrainingDay> trainingDays;

    @OneToMany(mappedBy = "trainingPlan")
    private Set<UserTrainingPlan> userTrainingPlans;
}
