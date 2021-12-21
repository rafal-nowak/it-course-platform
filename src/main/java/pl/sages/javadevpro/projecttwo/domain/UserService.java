package pl.sages.javadevpro.projecttwo.domain;

import lombok.RequiredArgsConstructor;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
