package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.MovementPattern;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementPatternRepository extends JpaRepository<MovementPattern, Integer> {
}