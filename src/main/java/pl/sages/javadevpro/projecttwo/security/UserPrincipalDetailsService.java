package pl.sages.javadevpro.projecttwo.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;

@AllArgsConstructor
@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .map(UserPrincipal::new)
            .orElseThrow( () -> new UsernameNotFoundException("User " + username + " not found"));
    }
}