package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.TargetMuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetMuscleGroupRepository extends JpaRepository<TargetMuscleGroup, Integer> {
    // Pobiera listę grup mięśniowych
}