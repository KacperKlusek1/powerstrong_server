package pl.pollub.powerstrong_server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EffortType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(name = "effort_type_name")
    private String name;

    private String description;

    @OneToMany(mappedBy = "effortType")
    private Set<PlannedExercise> plannedExercises;
}
