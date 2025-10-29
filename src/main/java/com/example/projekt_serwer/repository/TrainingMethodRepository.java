package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.TrainingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainingMethodRepository extends JpaRepository<TrainingMethod, Integer> {

    Optional<TrainingMethod> findByName(String name);
}