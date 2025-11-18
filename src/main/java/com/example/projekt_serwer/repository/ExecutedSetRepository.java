package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.ExecutedSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExecutedSetRepository extends JpaRepository<ExecutedSet, Integer> {
    // Używane w TrainingService do zapisu danych z Outbox

    // Przykładowa metoda statystyczna (np. do pobierania PR dla danego ćwiczenia)
    List<ExecutedSet> findByPlannedExerciseIdOrderByExecutionDateDesc(int plannedExerciseId);
}