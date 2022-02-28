package pl.sages.javadevpro.projecttwo.domain.user;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
//        Optional<User> saved = userRepository.save(user);
//        return saved.orElseThrow(() -> new UserAlreadyExistsException("User already exists"));
    }

    public User update(User user) {
        Optional<User> updated = userRepository.update(user);
        return updated.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User removeById(String id) {
        Optional<User> removed = userRepository.remove(id);
        return removed.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User findByEmail(String email) {
        Optional<User> founded = userRepository.findByEmail(email);
        return founded.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User findById(String id) {
        Optional<User> founded = userRepository.findById(id);
        return founded.orElseThrow(() -> new UserNotFoundException("User not found"));
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

}
