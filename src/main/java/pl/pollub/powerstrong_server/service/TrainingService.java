package pl.pollub.powerstrong_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.powerstrong_server.dto.*;
import pl.pollub.powerstrong_server.enums.PlanStatus;
import pl.pollub.powerstrong_server.exception.ResourceNotFoundException;
import pl.pollub.powerstrong_server.model.*;
import pl.pollub.powerstrong_server.repository.*;
import pl.pollub.powerstrong_server.utils.EntityDtoMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private static final int DEFAULT_TRAINING_METHOD_ID = 5;

    private final UserTrainingPlanRepository userTrainingPlanRepository;
    private final PlannedExerciseRepository plannedExerciseRepository;
    private final ExecutedSetRepository executedSetRepository;
    private final TrainingPlanRepository trainingPlanRepository;
    private final UserRepository userRepository;
    private final UserExerciseMaxRepository userExerciseMaxRepository;
    private final ExerciseRepository exerciseRepository;
    private final TrainingMethodRepository trainingMethodRepository;
    private final EffortTypeRepository effortTypeRepository;

    private final TrainingCalculationService calculationService;
    private final EntityDtoMapper mapper;

    @Transactional(readOnly = true)
    public TrainingPlanFullDto getActivePlanForUser(int userId) {
        UserTrainingPlan userPlan = userTrainingPlanRepository.findByUserIdAndStatus(userId, PlanStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Brak aktywnego planu dla usera: " + userId));

        TrainingPlanFullDto planDto = mapper.toTrainingPlanFullDto(userPlan.getTrainingPlan(), userPlan);

        enrichPlanWithCalculations(planDto, userId);
        return planDto;
    }

    private void enrichPlanWithCalculations(TrainingPlanFullDto planDto, int userId) {
        if (planDto.getTrainingDays() == null) return;

        for (TrainingDayDto dayDto : planDto.getTrainingDays()) {
            if (dayDto.getPlannedExercises() == null) continue;

            for (PlannedExerciseDto exDto : dayDto.getPlannedExercises()) {
                PlannedExercise plannedEx = plannedExerciseRepository.findById(exDto.getId()).orElse(null);
                if (plannedEx != null) {
                    Optional<UserExerciseMax> userMaxOpt = userExerciseMaxRepository
                            .findByUserIdAndExerciseId(userId, plannedEx.getExercise().getId());
                    calculationService.calculateTargetWeightForDto(exDto, plannedEx, userMaxOpt);
                }
            }
        }
    }

    @Transactional
    public void cancelActivePlan(int userId) {
        userTrainingPlanRepository.findByUserIdAndStatus(userId, PlanStatus.ACTIVE)
                .ifPresent(p -> {
                    p.setStatus(PlanStatus.CANCELLED);
                    p.setEndDate(LocalDate.now());
                    userTrainingPlanRepository.save(p);
                });
    }

    @Transactional
    public void completeActivePlan(int userId, PlanCompletionRequestDto dto) {
        UserTrainingPlan userPlan = userTrainingPlanRepository.findByUserIdAndStatus(userId, PlanStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Brak aktywnego planu do zakończenia."));

        userPlan.setStatus(PlanStatus.COMPLETED);
        userPlan.setEndDate(LocalDate.now());

        userPlan.setWasTrackingNutrition(dto.isTrackingNutrition());
        userPlan.setWasTrackingSleep(dto.isTrackingSleep());

        if (dto.isTrackingSleep() && dto.getAverageHoursOfSleep() != null) {
            userPlan.setAverageHoursOfSleep((int) Math.round(dto.getAverageHoursOfSleep()));
        } else {
            userPlan.setAverageHoursOfSleep(null);
        }

        userPlan.setPersonalEvaluation(dto.getPersonalEvaluation());
        userTrainingPlanRepository.save(userPlan);
    }

    @Transactional(readOnly = true)
    public List<UserExerciseMaxDto> getUserRecords(int userId) {
        return userExerciseMaxRepository.findAllByUserId(userId).stream()
                .map(record -> UserExerciseMaxDto.builder()
                        .exerciseId(record.getExercise().getId())
                        .exerciseName(record.getExercise().getName())
                        .currentOneRepMax(record.getCurrentOneRepMax())
                        .isBodyweight(record.getExercise().getIsBodyweight())
                        .lastUpdatedDate(LocalDate.now().toString())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TrainingPlanFullDto> getPlanHistory(int userId) {
        return userTrainingPlanRepository.findAllByUserId(userId).stream()
                .filter(utp -> utp.getStatus() == PlanStatus.COMPLETED || utp.getStatus() == PlanStatus.CANCELLED)
                .map(utp -> mapper.toTrainingPlanFullDto(utp.getTrainingPlan(), utp))
                .toList();
    }

    @Transactional
    public void saveExecutedSets(int userId, List<ExecutedSetDto> executedSetDtos) {
        if (executedSetDtos.isEmpty()) return;

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserTrainingPlan activePlan = userTrainingPlanRepository
                .findByUserIdAndStatus(userId, PlanStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Nie można zapisać serii: Brak aktywnego planu!"));

        List<ExecutedSet> entitiesToSave = new ArrayList<>();

        for (ExecutedSetDto dto : executedSetDtos) {
            PlannedExercise plannedExercise = plannedExerciseRepository.findById(dto.getPlannedExerciseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Exercise not found"));

            ExecutedSet entity = new ExecutedSet();
            entity.setUserTrainingPlan(activePlan);
            entity.setPlannedExercise(plannedExercise);
            entity.setSetNumber(dto.getSetNumber());
            entity.setExecutedReps(dto.getExecutedReps());
            entity.setWeightUsed(dto.getWeightUsed());
            entity.setExecutionDate(LocalDateTime.now());

            entitiesToSave.add(entity);

            updateUserMaxIfNeeded(user, plannedExercise.getExercise(), dto.getWeightUsed(), dto.getExecutedReps());
        }
        executedSetRepository.saveAll(entitiesToSave);
    }

    private void updateUserMaxIfNeeded(User user, Exercise exercise, double weight, int reps) {
        if (reps <= 0) return;
        if (!exercise.getIsBodyweight() && weight <= 0) return;

        double newRecordValue;
        if (exercise.getIsBodyweight()) {
            newRecordValue = reps;
        } else {
            double rawValue = weight * (1 + (double) reps / 30.0);
            newRecordValue = Math.round(rawValue * 2) / 2.0;
        }

        UserExerciseMax userMax = userExerciseMaxRepository
                .findByUserIdAndExerciseId(user.getId(), exercise.getId())
                .orElse(new UserExerciseMax());

        double currentMax = userMax.getCurrentOneRepMax() != null ? userMax.getCurrentOneRepMax() : 0.0;

        if (userMax.getId() == null || newRecordValue > currentMax) {
            userMax.setUser(user);
            userMax.setExercise(exercise);
            userMax.setCurrentOneRepMax(newRecordValue);
            userExerciseMaxRepository.save(userMax);
        }
    }

    @Transactional
    public void assignPlanToUser(int trainingPlanId, int userId, LocalDate startDate) {
        if (userTrainingPlanRepository.findByUserIdAndStatus(userId, PlanStatus.ACTIVE).isPresent()) {
            throw new IllegalStateException("Użytkownik ma już aktywny plan.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        TrainingPlan template = trainingPlanRepository.findById(trainingPlanId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found"));

        UserTrainingPlan link = new UserTrainingPlan();
        link.setUser(user);
        link.setTrainingPlan(template);
        link.setStartDate(startDate != null ? startDate : LocalDate.now());
        link.setStatus(PlanStatus.ACTIVE);
        link.setWasTrackingNutrition(false);
        link.setWasTrackingSleep(false);

        userTrainingPlanRepository.save(link);
    }

    @Transactional
    public void createCustomPlan(TrainingPlanFullDto dto, int userId) {
        buildAndSaveCustomPlan(dto, userId);
    }

    private void buildAndSaveCustomPlan(TrainingPlanFullDto dto, int userId) {
        if (userTrainingPlanRepository.findByUserIdAndStatus(userId, PlanStatus.ACTIVE).isPresent()) {
            throw new IllegalStateException("Nie można utworzyć planu. Masz już aktywny plan treningowy.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        TrainingPlan plan = new TrainingPlan();
        plan.setName(dto.getName());
        plan.setIsPreset(false);
        plan.setCreatedBy(user);
        plan.setCreatedDate(LocalDateTime.now());

        TrainingMethod method = null;
        if (dto.getTrainingMethodId() != null) {
            method = trainingMethodRepository.findById(dto.getTrainingMethodId()).orElse(null);
        }
        if (method == null) {
            method = trainingMethodRepository.findById(DEFAULT_TRAINING_METHOD_ID)
                    .orElseThrow(() -> new ResourceNotFoundException("Default Training Method not found"));
        }
        plan.setTrainingMethod(method);

        plan.setTrainingDays(new HashSet<>());

        if (dto.getTrainingDays() != null) {
            for (TrainingDayDto dayDto : dto.getTrainingDays()) {
                TrainingDay day = new TrainingDay();
                day.setDayName(dayDto.getDayName());
                day.setWeekNumber(dayDto.getWeekNumber());
                day.setDayOrder(dayDto.getDayOrder());
                day.setDaysGap(dayDto.getDaysGap());
                day.setTrainingPlan(plan);
                day.setPlannedExercises(new HashSet<>());

                if (dayDto.getPlannedExercises() != null) {
                    for (PlannedExerciseDto exDto : dayDto.getPlannedExercises()) {
                        PlannedExercise ex = new PlannedExercise();
                        Exercise exerciseDef = exerciseRepository.findById(exDto.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Exercise definition not found"));
                        ex.setExercise(exerciseDef);
                        ex.setExerciseOrder(exDto.getExerciseOrder());
                        ex.setPlannedSets(exDto.getPlannedSets());
                        ex.setPlannedReps(exDto.getPlannedReps());
                        if (exDto.getEffortType() != null) {
                            effortTypeRepository.findByName(exDto.getEffortType())
                                    .ifPresent(ex::setEffortType);
                        }
                        ex.setIntensityPercentage(null);
                        ex.setTrainingDay(day);
                        day.getPlannedExercises().add(ex);
                    }
                }
                plan.getTrainingDays().add(day);
            }
        }

        TrainingPlan savedPlan = trainingPlanRepository.save(plan);

        LocalDate startDate = LocalDate.now();
        if (dto.getStartDate() != null && !dto.getStartDate().isEmpty()) {
            try {
                startDate = LocalDate.parse(dto.getStartDate());
            } catch (Exception e) {
            }
        }
        assignPlanToUser(savedPlan.getId(), userId, startDate);
    }

    public List<TrainingPlanFullDto> getAllPlanTemplates() {
        return trainingPlanRepository.findByIsPresetTrue().stream()
                .map(p -> mapper.toTrainingPlanFullDto(p, null))
                .toList();
    }
}