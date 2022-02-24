package pl.sages.javadevpro.projecttwo.domain.user;

import lombok.RequiredArgsConstructor;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintAlreadyExist;
import pl.sages.javadevpro.projecttwo.domain.task.TaskBlueprintNotFound;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        Optional<User> saved = userRepository.save(user);
        if(saved.isEmpty()){
            throw new UserAlreadyExist("User already exist");
        }
        return saved.get();
    }

    public User update(User user) {
        Optional<User> updated = userRepository.update(user);
        if(updated.isEmpty()){
            throw new UserNotFound("User not found");
        }
        return updated.get();
    }

    public User removeById(String id) {
        Optional<User> removed = userRepository.remove(id);
        if (removed.isEmpty()) {
            throw new UserNotFound("User not found");
        }
        return removed.get();
    }

    public User findByEmail(String email) {
        Optional<User> founded = userRepository.findByEmail(email);
        if (founded.isEmpty()) {
            throw new UserNotFound("User not found");
        }
        return founded.get();
    }

    public User findById(String id) {
        Optional<User> founded = userRepository.findById(id);
        if (founded.isEmpty()) {
            throw new UserNotFound("User not found");
        }
        return founded.get();
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

}
