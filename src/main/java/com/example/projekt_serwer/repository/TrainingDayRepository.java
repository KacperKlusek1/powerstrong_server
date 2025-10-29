package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.TrainingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingDayRepository extends JpaRepository<TrainingDay, Long> {

    /**
     * Znajdź wszystkie dni treningowe dla danego planu, posortowane po kolejności
     * SQL: SELECT * FROM training_day WHERE training_plan_id = ? ORDER BY day_order
     */
    List<TrainingDay> findByTrainingPlan_IdOrderByDayOrderAsc(Long trainingPlanId);

    /**
     * Znajdź dni treningowe po numerze tygodnia
     */
    List<TrainingDay> findByWeekNumber(Integer weekNumber);

    /**
     * Znajdź dni treningowe dla planu i tygodnia
     */
    List<TrainingDay> findByTrainingPlan_IdAndWeekNumberOrderByDayOrderAsc(Long trainingPlanId, Integer weekNumber);
}
