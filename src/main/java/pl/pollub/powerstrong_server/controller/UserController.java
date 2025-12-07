package pl.pollub.powerstrong_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.pollub.powerstrong_server.dto.TrainingPlanFullDto;
import pl.pollub.powerstrong_server.dto.UserDto;
import pl.pollub.powerstrong_server.dto.UserExerciseMaxDto;
import pl.pollub.powerstrong_server.model.User;
import pl.pollub.powerstrong_server.service.TrainingService;
import pl.pollub.powerstrong_server.service.UserService;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TrainingService trainingService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserDto userDto = userService.findUserDetailsById(user.getId());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/records")
    public ResponseEntity<List<UserExerciseMaxDto>> getUserRecords(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(trainingService.getUserRecords(user.getId()));
    }

    @GetMapping("/plans/history")
    public ResponseEntity<List<TrainingPlanFullDto>> getPlanHistory(Authentication auth) {
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(trainingService.getPlanHistory(user.getId()));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(Authentication auth) {
        User user = (User) auth.getPrincipal();
        userService.deleteUser(user.getId());
        return ResponseEntity.ok().build();
    }
}