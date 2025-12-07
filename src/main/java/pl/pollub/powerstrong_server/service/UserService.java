package pl.pollub.powerstrong_server.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.powerstrong_server.dto.UserDto;
import pl.pollub.powerstrong_server.exception.ResourceNotFoundException;
import pl.pollub.powerstrong_server.model.TrainingPlan;
import pl.pollub.powerstrong_server.model.User;
import pl.pollub.powerstrong_server.model.UserExerciseMax;
import pl.pollub.powerstrong_server.model.UserTrainingPlan;
import pl.pollub.powerstrong_server.repository.*;
import pl.pollub.powerstrong_server.utils.EntityDtoMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserTrainingPlanRepository userTrainingPlanRepository;
    private final UserExerciseMaxRepository userExerciseMaxRepository;
    private final TrainingPlanRepository trainingPlanRepository;
    private final ExecutedSetRepository executedSetRepository;
    private final EntityDtoMapper mapper;

    @Transactional(readOnly = true)
    public UserDto findUserDetailsById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapper.toUserDto(user);
    }

    @Transactional
    public void deleteUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<UserTrainingPlan> history = userTrainingPlanRepository.findAllByUserId(userId);
        if (!history.isEmpty()) {
            executedSetRepository.deleteByUserTrainingPlanIn(history);
            userTrainingPlanRepository.deleteAll(history);
        }

        List<UserExerciseMax> records = userExerciseMaxRepository.findAllByUserId(userId);
        userExerciseMaxRepository.deleteAll(records);

        List<TrainingPlan> createdPlans = trainingPlanRepository.findByCreatedBy(user);
        trainingPlanRepository.deleteAll(createdPlans);

        userRepository.delete(user);
    }
}