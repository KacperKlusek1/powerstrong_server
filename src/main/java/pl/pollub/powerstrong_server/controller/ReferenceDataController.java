package pl.pollub.powerstrong_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.service.ReferenceDataService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/reference")
@RequiredArgsConstructor
public class ReferenceDataController {
    private final ReferenceDataService referenceDataService;

    @GetMapping("/exercises")
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        return ResponseEntity.ok(referenceDataService.getAllExercises());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ExerciseCategoryDto>> getAllCategories() {
        return ResponseEntity.ok(referenceDataService.getAllExerciseCategories());
    }

    @GetMapping("/movement-patterns")
    public ResponseEntity<List<MovementPatternDto>> getAllMovementPatterns() {
        return ResponseEntity.ok(referenceDataService.getAllMovementPatterns());
    }

    @GetMapping("/target-muscle-groups")
    public ResponseEntity<List<TargetMuscleGroupDto>> getAllTargetMuscleGroups() {
        return ResponseEntity.ok(referenceDataService.getAllTargetMuscleGroups());
    }

    @GetMapping("/effort-types")
    public ResponseEntity<List<EffortTypeDto>> getAllEffortTypes() {
        return ResponseEntity.ok(referenceDataService.getAllEffortTypes());
    }

    @GetMapping("/training-methods")
    public ResponseEntity<List<TrainingMethodDto>> getAllTrainingMethods() {
        return ResponseEntity.ok(referenceDataService.getAllTrainingMethods());
    }
}