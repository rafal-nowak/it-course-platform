package pl.sages.javadevpro.projecttwo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.PasswordEncoding;

@Component
@RequiredArgsConstructor
public class PasswordService implements PasswordEncoding {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String str) {
        return passwordEncoder.encode(str);
    }
}