package com.example.projekt_serwer.repository;

import com.example.projekt_serwer.model.EffortType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EffortTypeRepository extends JpaRepository<EffortType, Integer> {
}