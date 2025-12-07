package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.TrainingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingMethodRepository extends JpaRepository<TrainingMethod, Integer> {
}