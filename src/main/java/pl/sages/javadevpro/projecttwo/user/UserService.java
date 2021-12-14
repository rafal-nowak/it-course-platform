package pl.sages.javadevpro.projecttwo.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User findByUserName(String username) {
        return userRepository.findByUsername(username);//zwracamy uzytkownika
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));//ustawiamy hasło ale wczesniej je enkodujemy
        user.setEnabled(1);//ustawiamy aktywność uzytkownika
        user.setRole("ROLE_ADMIN");//ustawiamy role
        userRepository.save(user);//zapisujemy użytkownika
    }
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));//ustawiamy hasło ale wczesniej je enkodujemy
        user.setEnabled(1);//ustawiamy aktywność uzytkownika
        user.setRole("ROLE_USER");//ustawiamy role
        userRepository.save(user);//zapisujemy użytkownika
    }
}
