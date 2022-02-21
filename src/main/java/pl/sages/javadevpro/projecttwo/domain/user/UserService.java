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

    public void removeById(String id) {
        userRepository.remove(id);
    } //TODO dodac exception na poziomie domeny

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    } //TODO dodac wyjatek "UserNotFoundException" zamiast nulla

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    } //TODO dodac wyjatek "UserNotFoundException" zamiast nulla


    public List<User> findAll() {
        return userRepository.findAll();
    } //TODO dodac pagowanie
    
}
