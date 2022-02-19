package pl.sages.javadevpro.projecttwo.domain.user;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public void remove(User user) {
        userRepository.remove(user);
    } //TODO dodac exception na poziomie domeny

    public User findBy(String email) {
        return userRepository.findByEmail(email).orElse(null);
    } //TODO dodac wyjatek "UserNotFoundException" zamiast nulla

    public List<User> findAll() {
        return userRepository.findAll();
    } //TODO dodac pagowanie
    
}
