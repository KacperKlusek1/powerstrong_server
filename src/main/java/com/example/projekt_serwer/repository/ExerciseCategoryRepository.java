package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.ExerciseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseCategoryRepository extends JpaRepository<ExerciseCategory, Integer> {

    /**
     * Znajdź kategorię po nazwie
     */
    Optional<ExerciseCategory> findByName(String name);
}