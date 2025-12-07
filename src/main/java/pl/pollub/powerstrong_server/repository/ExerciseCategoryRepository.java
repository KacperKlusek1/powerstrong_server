package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.ExerciseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseCategoryRepository extends JpaRepository<ExerciseCategory, Integer> {
}