package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.UserTrainingPlan;
import com.example.projekt_serwer.model.UserTrainingPlanKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTrainingPlanRepository extends JpaRepository<UserTrainingPlan, UserTrainingPlanKey> {

    /**
     * Znajdź wszystkie plany danego usera
     */
    List<UserTrainingPlan> findByUser_Id(Long userId);

    /**
     * Znajdź aktywny plan usera
     */
    Optional<UserTrainingPlan> findByUser_IdAndIsActiveTrue(Long userId);

    /**
     * Znajdź wszystkich userów korzystających z danego planu
     */
    List<UserTrainingPlan> findByTrainingPlan_Id(Long trainingPlanId);
}