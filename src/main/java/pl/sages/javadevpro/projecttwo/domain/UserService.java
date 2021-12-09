package pl.sages.javadevpro.projecttwo.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;

@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getUser(String login) {
        return userRepository.findUserByLogin(login).orElse(null);
    }
}
