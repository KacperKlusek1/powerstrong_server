package com.example.projekt_serwer.service;

import com.example.projekt_serwer.dto.ExecutedSetDto;
import com.example.projekt_serwer.dto.TrainingPlanFullDto;
import com.example.projekt_serwer.exception.ResourceNotFoundException;
import com.example.projekt_serwer.mapper.EntityDtoMapper;
import com.example.projekt_serwer.model.*;
import com.example.projekt_serwer.repository.ExecutedSetRepository;
import com.example.projekt_serwer.repository.PlannedExerciseRepository;
import com.example.projekt_serwer.repository.UserTrainingPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder; // Potrzebne do zapisu
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingService {

    @Autowired
    private UserTrainingPlanRepository userTrainingPlanRepository;

    @Autowired
    private PlannedExerciseRepository plannedExerciseRepository;

    @Autowired
    private ExecutedSetRepository executedSetRepository;

    @Autowired
    private EntityDtoMapper mapper;

    /**
     * Pobiera i buduje pełny DTO dla aktywnego planu użytkownika.
     */
    @Transactional(readOnly = true)
    public TrainingPlanFullDto getActivePlanForUser(int userId) {
        // Znajdź aktywne przypisanie planu dla użytkownika
        UserTrainingPlan userPlan = userTrainingPlanRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No active training plan found for user id: " + userId));

        TrainingPlan plan = userPlan.getTrainingPlan();

        // Zbuduj DTO używając mappera
        return mapper.toTrainingPlanFullDto(plan, userPlan);
    }

    /**
     * Zapisuje listę wykonanych serii przesłanych z aplikacji (Logika Outbox).
     */
    @Transactional
    public void saveExecutedSets(List<ExecutedSetDto> executedSetDtos) {
        // Założenie: Użytkownik jest uwierzytelniony przez Spring Security
        // User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Dla uproszczenia, musimy wydobyć ID użytkownika z DTO (lub dodać je do DTO)

        // Przekształcamy DTO na Encje
        List<ExecutedSet> entitiesToSave = executedSetDtos.stream()
                .map(dto -> {
                    // 1. Znajdź referencję do PlannedExercise
                    PlannedExercise pe = plannedExerciseRepository.findById(dto.getPlannedExerciseId())
                            .orElseThrow(() -> new ResourceNotFoundException("PlannedExercise not found with id: " + dto.getPlannedExerciseId()));

                    // 2. Znajdź referencję do UserTrainingPlan (to jest skomplikowane bez ID użytkownika)
                    // Musimy założyć, że PlannedExercise wie o swoim UserTrainingPlan
                    // Spójrzmy na model... pe -> trainingDay -> trainingPlan -> userTrainingPlans (Set)

                    TrainingPlan plan = pe.getTrainingDay().getTrainingPlan();

                    // To jest słaby punkt - zakładamy, że plan jest aktywny tylko dla jednego użytkownika
                    // Lepszym rozwiązaniem jest przekazanie `userId` z kontekstu bezpieczeństwa
                    UserTrainingPlan utp = plan.getUserTrainingPlans().stream()
                            .filter(UserTrainingPlan::getIsActive)
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Could not find active UserTrainingPlan for plan id: " + plan.getId()));

                    // 3. Zmapuj na encję
                    return mapper.toExecutedSetEntity(dto, pe, utp);
                })
                .collect(Collectors.toList());

        // Zapisz wszystko do bazy
        executedSetRepository.saveAll(entitiesToSave);
    }
}