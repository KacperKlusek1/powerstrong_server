package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    /**
     * Znajdź ćwiczenia po nazwie (dokładne dopasowanie)
     * SQL: SELECT * FROM exercise WHERE name = ?
     */
    List<Exercise> findByName(String name);

    /**
     * Znajdź ćwiczenia po części nazwy (LIKE '%nazwa%')
     * SQL: SELECT * FROM exercise WHERE name LIKE %?%
     */
    List<Exercise> findByNameContainingIgnoreCase(String name);

    /**
     * Znajdź ćwiczenia po kategorii
     * SQL: SELECT * FROM exercise WHERE exercise_category_id = ?
     */
    List<Exercise> findByExerciseCategory_Id(Integer categoryId);

    /**
     * Znajdź ćwiczenia po kategorii, posortowane alfabetycznie
     */
    List<Exercise> findByExerciseCategory_IdOrderByNameAsc(Integer categoryId);

    /**
     * Własne zapytanie JPQL (bardziej skomplikowane)
     * Znajdź ćwiczenia które angażują daną grupę mięśniową
     */
    @Query("SELECT DISTINCT e FROM Exercise e JOIN e.targetMuscleGroups tmg WHERE tmg.id = :muscleGroupId")
    List<Exercise> findByTargetMuscleGroup(@Param("muscleGroupId") Integer muscleGroupId);
}