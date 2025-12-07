package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.enums.PlanStatus;
import pl.pollub.powerstrong_server.model.UserTrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTrainingPlanRepository extends JpaRepository<UserTrainingPlan, Long> {
    Optional<UserTrainingPlan> findByUserIdAndStatus(int userId, PlanStatus status);

    List<UserTrainingPlan> findAllByUserId(int userId);
}