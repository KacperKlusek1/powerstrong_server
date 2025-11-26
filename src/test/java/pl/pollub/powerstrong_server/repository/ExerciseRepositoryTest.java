package pl.pollub.powerstrong_server.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.pollub.powerstrong_server.model.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExerciseRepositoryTest {

    @Autowired ExerciseRepository exerciseRepo;
    @Autowired MovementPatternRepository movementRepo;
    @Autowired ExerciseCategoryRepository categoryRepo;

    @Test
    void findAllWithDetails_returns_joined_data() {
        MovementPattern mp = new MovementPattern();
        mp.setName("Squat");
        movementRepo.save(mp);

        ExerciseCategory cat = new ExerciseCategory();
        cat.setName("General");
        categoryRepo.save(cat);

        Exercise ex = new Exercise();
        ex.setName("Back Squat");
        ex.setMovementPatterns(Set.of(mp));
        ex.setExerciseCategory(cat);
        ex.setIsBodyweight(false);

        exerciseRepo.save(ex);

        List<Exercise> list = exerciseRepo.findAllWithDetails();
        assertEquals(1, list.size());
        assertEquals("Back Squat", list.get(0).getName());
        assertEquals("Squat", list.get(0).getMovementPatterns().stream().toList().get(0).getName());
    }
}
