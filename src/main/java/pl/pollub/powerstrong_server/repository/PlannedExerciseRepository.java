package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.PlannedExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannedExerciseRepository extends JpaRepository<PlannedExercise, Integer> {
}