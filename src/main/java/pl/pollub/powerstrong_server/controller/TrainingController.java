package pl.pollub.powerstrong_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.pollub.powerstrong_server.dto.ExecutedSetDto;
import pl.pollub.powerstrong_server.dto.PlanCompletionRequestDto;
import pl.pollub.powerstrong_server.dto.TrainingPlanFullDto;
import pl.pollub.powerstrong_server.model.User;
import pl.pollub.powerstrong_server.service.TrainingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping("/plans/templates")
    public ResponseEntity<List<TrainingPlanFullDto>> getPlanTemplates() {
        return ResponseEntity.ok(trainingService.getAllPlanTemplates());
    }

    @PostMapping("/plans/{templateId}/assign")
    public ResponseEntity<Void> assignPlanToUser(
            @PathVariable int templateId,
            @RequestParam(required = false) LocalDate startDate,
            Authentication authentication
    ) {
        trainingService.assignPlanToUser(templateId, getUser(authentication).getId(), startDate);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/plans/custom")
    public ResponseEntity<Void> createCustomPlan(
            @RequestBody TrainingPlanFullDto planDto,
            Authentication authentication
    ) {
        trainingService.createCustomPlan(planDto, getUser(authentication).getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/plans/active")
    public ResponseEntity<TrainingPlanFullDto> getFullActiveTrainingPlan(Authentication authentication) {
        TrainingPlanFullDto planDto = trainingService.getActivePlanForUser(getUser(authentication).getId());
        return ResponseEntity.ok(planDto);
    }

    @PostMapping("/plans/active/complete")
    public ResponseEntity<Void> completeActivePlan(
            @RequestBody PlanCompletionRequestDto completionData,
            Authentication authentication
    ) {
        trainingService.completeActivePlan(getUser(authentication).getId(), completionData);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/plans/active/cancel")
    public ResponseEntity<Void> cancelActivePlan(Authentication authentication) {
        trainingService.cancelActivePlan(getUser(authentication).getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/executed-sets")
    public ResponseEntity<Void> receiveExecutedSets(
            @RequestBody List<ExecutedSetDto> executedSets,
            Authentication authentication
    ) {
        trainingService.saveExecutedSets(getUser(authentication).getId(), executedSets);
        return ResponseEntity.ok().build();
    }

    private User getUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}