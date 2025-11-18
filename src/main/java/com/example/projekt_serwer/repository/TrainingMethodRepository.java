package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.TrainingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingMethodRepository extends JpaRepository<TrainingMethod, Integer> {
}