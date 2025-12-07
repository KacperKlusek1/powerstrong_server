package pl.pollub.powerstrong_server.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.pollub.powerstrong_server.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=MySQL",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.sql.init.mode=never",
        "spring.jpa.show-sql=true"
})
class ExerciseRepositoryTest {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    MovementPatternRepository movementPatternRepository;

    @Autowired
    ExerciseCategoryRepository exerciseCategoryRepository;

    @Test
    void findAllWithDetails_returns_saved_exercise() {
        MovementPattern mp = new MovementPattern();
        mp.setName("SquatPattern");
        mp = movementPatternRepository.save(mp);
        ExerciseCategory cat = new ExerciseCategory();
        cat.setName("Strength");
        cat = exerciseCategoryRepository.save(cat);

        Exercise ex = new Exercise();
        ex.setName("Back Squat");
        ex.setMovementPatterns(new HashSet<>(Set.of(mp)));
        ex.setExerciseCategory(cat);
        ex.setIsBodyweight(false);

        exerciseRepository.save(ex);

        List<Exercise> list = exerciseRepository.findAllWithDetails();

        assertFalse(list.isEmpty());
        assertEquals("Back Squat", list.get(0).getName());
        assertNotNull(list.get(0).getMovementPatterns());
        assertEquals(1, list.get(0).getMovementPatterns().size());
        assertEquals("SquatPattern", list.get(0).getMovementPatterns().iterator().next().getName());
    }
}