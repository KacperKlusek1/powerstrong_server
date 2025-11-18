package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.PlannedExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannedExerciseRepository extends JpaRepository<PlannedExercise, Integer> {

}