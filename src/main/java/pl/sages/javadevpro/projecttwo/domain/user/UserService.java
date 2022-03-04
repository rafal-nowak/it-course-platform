package pl.sages.javadevpro.projecttwo.domain.user;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        Optional<User> updated = userRepository.update(user);
        return updated.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void removeById(String id) {
        userRepository.remove(id);
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
