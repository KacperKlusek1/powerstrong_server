package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.TrainingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingDayRepository extends JpaRepository<TrainingDay, Integer> {
}