package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.PlannedExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlannedExerciseRepository extends JpaRepository<PlannedExercise, Long> {

    /**
     * Znajdź wszystkie zaplanowane ćwiczenia dla danego dnia, posortowane po kolejności
     * SQL: SELECT * FROM planned_exercise WHERE training_day_id = ? ORDER BY exercise_order
     */
    List<PlannedExercise> findByTrainingDay_IdOrderByExerciseOrderAsc(Long trainingDayId);

    /**
     * Znajdź wszystkie wystąpienia danego ćwiczenia w planach
     */
    List<PlannedExercise> findByExercise_Id(Integer exerciseId);
}