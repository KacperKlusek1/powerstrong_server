package pl.pollub.powerstrong_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.repository.*;
import pl.pollub.powerstrong_server.utils.EntityDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferenceDataService {

    private final ExerciseRepository exerciseRepository;
    private final MovementPatternRepository movementPatternRepository;
    private final TargetMuscleGroupRepository targetMuscleGroupRepository;
    private final ExerciseCategoryRepository exerciseCategoryRepository;
    private final EffortTypeRepository effortTypeRepository;
    private final TrainingMethodRepository trainingMethodRepository;

    private final EntityDtoMapper mapper;

    @Transactional(readOnly = true)
    public List<ExerciseDto> getAllExercises() {
        return exerciseRepository.findAllWithDetails().stream()
                .map(mapper::toExerciseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ExerciseCategoryDto> getAllExerciseCategories() {
        return exerciseCategoryRepository.findAll().stream()
                .map(mapper::toExerciseCategoryDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovementPatternDto> getAllMovementPatterns() {
        return movementPatternRepository.findAll().stream()
                .map(mapper::toMovementPatternDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TargetMuscleGroupDto> getAllTargetMuscleGroups() {
        return targetMuscleGroupRepository.findAll().stream()
                .map(mapper::toTargetMuscleGroupDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EffortTypeDto> getAllEffortTypes() {
        return effortTypeRepository.findAll().stream()
                .map(mapper::toEffortTypeDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TrainingMethodDto> getAllTrainingMethods() {
        return trainingMethodRepository.findAll().stream()
                .map(mapper::toTrainingMethodDto)
                .collect(Collectors.toList());
    }
}