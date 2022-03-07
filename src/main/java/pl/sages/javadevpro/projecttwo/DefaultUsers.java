package pl.sages.javadevpro.projecttwo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.domain.user.UserRole;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class DefaultUsers implements CommandLineRunner {

    private final UserRepository userRepository;

    private final User adminUser = new User(
        null,
        "jan@example.com",
        "Jan Kowalski",
        "MyPassword",
        List.of(UserRole.ADMIN)
    );

    private final User studentUser = new User(
        null,
        "stefan@example.com",
        "Stefan Burczymucha",
        "password",
        List.of(UserRole.STUDENT)
    );

    @Override
    public void run(String... args) throws Exception {
        addUser(adminUser);
        addUser(studentUser);
    }

    private void addUser(User user) {
        Optional<User> found = userRepository.findByEmail(user.getEmail());
        if(found.isEmpty()) {
            userRepository.save(user);
        }
    }
}