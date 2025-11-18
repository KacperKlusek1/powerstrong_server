package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.UserTrainingPlan;
import com.example.projekt_serwer.model.UserTrainingPlanKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Używamy złożonego klucza UserTrainingPlanId
public interface UserTrainingPlanRepository extends JpaRepository<UserTrainingPlan, UserTrainingPlanKey> {

    /**
     * Kluczowa metoda: Szuka aktywnego planu treningowego dla danego użytkownika.
     * Używana w TrainingService.
     */
    Optional<UserTrainingPlan> findByUserIdAndIsActiveTrue(int userId);

    // Metoda pomocnicza, jeśli chcemy plan dla danego ID planu
    Optional<UserTrainingPlan> findByTrainingPlanId(int planId);
}