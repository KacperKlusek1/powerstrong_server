package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    @Query("SELECT DISTINCT e FROM Exercise e " +
            "LEFT JOIN FETCH e.exerciseCategory " +
            "LEFT JOIN FETCH e.movementPatterns " +
            "LEFT JOIN FETCH e.targetMuscleGroups")
    List<Exercise> findAllWithDetails();
}