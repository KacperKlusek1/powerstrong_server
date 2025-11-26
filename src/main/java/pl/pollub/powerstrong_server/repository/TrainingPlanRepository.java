package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.TrainingPlan;
import pl.pollub.powerstrong_server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {

    List<TrainingPlan> findByIsPresetTrue();

    List<TrainingPlan> findByCreatedBy(User createdBy);
}