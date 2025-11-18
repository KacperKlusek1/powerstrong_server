package com.example.projekt_serwer.controller;

import com.example.projekt_serwer.dto.ExecutedSetDto;
import com.example.projekt_serwer.dto.TrainingPlanFullDto;
import com.example.projekt_serwer.service.TrainingService; // Twój serwis Spring Boot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Wspólny prefix dla obu endpointów
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    /**
     * Endpoint do pobierania całego aktywnego planu (dla synchronizacji z Room).
     * Mapuje się do getFullActiveTrainingPlan() z Androida.
     */
    @GetMapping("/training-plans/active/{userId}")
    public ResponseEntity<TrainingPlanFullDto> getFullActiveTrainingPlan(@PathVariable int userId) {
        TrainingPlanFullDto planDto = trainingService.getActivePlanForUser(userId);
        return ResponseEntity.ok(planDto);
    }

    /**
     * Endpoint do przyjmowania paczki wykonanych serii (logika Outbox).
     * Mapuje się do sendExecutedSets() z Androida.
     */
    @PostMapping("/executed-sets/bulk")
    public ResponseEntity<Void> receiveExecutedSets(@RequestBody List<ExecutedSetDto> executedSets) {
        // Serwis Spring Boot zajmie się zapisaniem tej listy do bazy danych JPA
        trainingService.saveExecutedSets(executedSets);

        // Zwracamy 200 OK bez żadnej treści
        return ResponseEntity.ok().build();
    }
}