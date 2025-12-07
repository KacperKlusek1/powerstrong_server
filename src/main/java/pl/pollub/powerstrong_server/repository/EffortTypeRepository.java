package pl.pollub.powerstrong_server.repository;

import pl.pollub.powerstrong_server.model.EffortType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EffortTypeRepository extends JpaRepository<EffortType, Integer> {
    Optional<EffortType> findByName(String Name);
}