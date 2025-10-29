package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long> {

    /**
     * Znajdź wszystkie presetowe plany treningowe
     * SQL: SELECT * FROM training_plan WHERE is_preset = true
     */
    List<TrainingPlan> findByIsPreset(Boolean isPreset);

    /**
     * Znajdź plany stworzone przez danego usera
     * SQL: SELECT * FROM training_plan WHERE created_by_user_id = ?
     */
    List<TrainingPlan> findByCreatedBy_Id(Long userId);

    /**
     * Znajdź plany po metodzie treningowej
     */
    List<TrainingPlan> findByTrainingMethod_Id(Integer trainingMethodId);

    /**
     * Znajdź plany po nazwie (częściowe dopasowanie)
     */
    List<TrainingPlan> findByNameContainingIgnoreCase(String name);
}