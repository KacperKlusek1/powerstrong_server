package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.ExecutedSet;
import com.example.projekt_serwer.model.UserTrainingPlanKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExecutedSetRepository extends JpaRepository<ExecutedSet, Long> {

    /**
     * Znajdź wszystkie wykonane sety dla danego planu usera, posortowane po dacie
     */
    List<ExecutedSet> findByUserTrainingPlan_IdOrderByExecutionDateDesc(UserTrainingPlanKey userTrainingPlanId);

    /**
     * Znajdź wykonane sety dla konkretnego zaplanowanego ćwiczenia
     */
    List<ExecutedSet> findByPlannedExercise_IdOrderBySetNumberAsc(Long plannedExerciseId);

    /**
     * Znajdź wykonane sety w przedziale dat
     */
    List<ExecutedSet> findByExecutionDateBetweenOrderByExecutionDateDesc(
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    /**
     * Własne zapytanie: Znajdź ostatnie wykonane sety dla danego usera i ćwiczenia
     */
    @Query("SELECT es FROM ExecutedSet es " +
            "WHERE es.userTrainingPlan.user.id = :userId " +
            "AND es.plannedExercise.exercise.id = :exerciseId " +
            "ORDER BY es.executionDate DESC")
    List<ExecutedSet> findRecentSetsByUserAndExercise(
            @Param("userId") Long userId,
            @Param("exerciseId") Integer exerciseId
    );
}