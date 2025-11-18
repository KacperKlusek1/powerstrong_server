package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    // Pobiera listę wszystkich ćwiczeń do wysłania do aplikacji
}