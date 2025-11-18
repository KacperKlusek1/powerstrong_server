package com.example.projekt_serwer.service;

import com.example.projekt_serwer.dto.ExerciseDto;
import com.example.projekt_serwer.dto.MovementPatternDto;
import com.example.projekt_serwer.dto.TargetMuscleGroupDto;
import com.example.projekt_serwer.mapper.EntityDtoMapper;
import com.example.projekt_serwer.repository.ExerciseRepository;
import com.example.projekt_serwer.repository.MovementPatternRepository;
import com.example.projekt_serwer.repository.TargetMuscleGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReferenceDataService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private MovementPatternRepository movementPatternRepository;

    @Autowired
    private TargetMuscleGroupRepository targetMuscleGroupRepository;

    @Autowired
    private EntityDtoMapper mapper;

    public List<ExerciseDto> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(mapper::toExerciseDto)
                .collect(Collectors.toList());
    }

    public List<MovementPatternDto> getAllMovementPatterns() {
        return movementPatternRepository.findAll().stream()
                .map(mapper::toMovementPatternDto)
                .collect(Collectors.toList());
    }

    public List<TargetMuscleGroupDto> getAllTargetMuscleGroups() {
        return targetMuscleGroupRepository.findAll().stream()
                .map(mapper::toTargetMuscleGroupDto)
                .collect(Collectors.toList());
    }
}