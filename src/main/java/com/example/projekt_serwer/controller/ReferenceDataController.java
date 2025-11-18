package com.example.projekt_serwer.controller;

import com.example.projekt_serwer.dto.ExerciseDto;
import com.example.projekt_serwer.dto.MovementPatternDto;
import com.example.projekt_serwer.dto.TargetMuscleGroupDto;
import com.example.projekt_serwer.service.ReferenceDataService; // Twój serwis Spring Boot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api") // Wspólny prefix
public class ReferenceDataController {

    @Autowired
    private ReferenceDataService referenceDataService;

    /**
     * Mapuje się do getAllExercises() z Androida.
     */
    @GetMapping("/exercises")
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        List<ExerciseDto> exercises = referenceDataService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    /**
     * Mapuje się do getAllMovementPatterns() z Androida.
     */
    @GetMapping("/movement-patterns")
    public ResponseEntity<List<MovementPatternDto>> getAllMovementPatterns() {
        List<MovementPatternDto> patterns = referenceDataService.getAllMovementPatterns();
        return ResponseEntity.ok(patterns);
    }

    /**
     * Mapuje się do getAllTargetMuscleGroups() z Androida.
     */
    @GetMapping("/target-muscle-groups")
    public ResponseEntity<List<TargetMuscleGroupDto>> getAllTargetMuscleGroups() {
        List<TargetMuscleGroupDto> groups = referenceDataService.getAllTargetMuscleGroups();
        return ResponseEntity.ok(groups);
    }
}