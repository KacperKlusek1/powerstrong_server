package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.ExecutedSet;
import pl.pollub.powerstrong_server.model.UserTrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutedSetRepository extends JpaRepository<ExecutedSet, Integer> {
    void deleteByUserTrainingPlanIn(List<UserTrainingPlan> plans);
}