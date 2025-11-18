package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.MovementPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementPatternRepository extends JpaRepository<MovementPattern, Integer> {
    // Pobiera listę wzorców ruchu
}