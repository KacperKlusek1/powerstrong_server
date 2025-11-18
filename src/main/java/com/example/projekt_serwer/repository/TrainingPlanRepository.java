package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.TrainingPlan;
import com.example.projekt_serwer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Integer> {

    List<TrainingPlan> findByIsPresetTrue();
    List<TrainingPlan> findByCreatedBy(User createdBy);
}