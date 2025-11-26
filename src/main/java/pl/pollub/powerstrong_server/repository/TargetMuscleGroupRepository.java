package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.TargetMuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetMuscleGroupRepository extends JpaRepository<TargetMuscleGroup, Integer> {
}