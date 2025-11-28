package pl.pollub.powerstrong_server.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pl.pollub.powerstrong_server.enums.UserRole;
import pl.pollub.powerstrong_server.enums.UserStatus;
import pl.pollub.powerstrong_server.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

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
class UserRepositoryTest {      //Przed uruchomieniem należy zmienić nazwę tabeli w User.java

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsernameOrEmail_should_find_user() {
        User u = new User();
        u.setUsername("repoUser");
        u.setPassword("pass");
        u.setEmail("repo@test.pl");
        u.setRole(UserRole.USER);
        u.setStatus(UserStatus.ACTIVE);
        u.setCreateDate(LocalDateTime.now());

        userRepository.save(u);

        Optional<User> byUsername = userRepository.findByUsernameOrEmail("repoUser");
        Optional<User> byEmail = userRepository.findByUsernameOrEmail("repo@test.pl");

        assertTrue(byUsername.isPresent());
        assertEquals("repoUser", byUsername.get().getUsername());

        assertTrue(byEmail.isPresent());
        assertEquals("repoUser", byEmail.get().getUsername());
    }
}