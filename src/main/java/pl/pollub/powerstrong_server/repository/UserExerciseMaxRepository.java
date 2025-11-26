package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.UserExerciseMax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserExerciseMaxRepository extends JpaRepository<UserExerciseMax, Integer> {
    Optional<UserExerciseMax> findByUserIdAndExerciseId(Integer userId, Integer exerciseId);

    List<UserExerciseMax> findAllByUserId(int userId);
}