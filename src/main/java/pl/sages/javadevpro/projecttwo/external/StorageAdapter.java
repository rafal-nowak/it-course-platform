package pl.sages.javadevpro.projecttwo.external;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.JpaUserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.UserEntity;
import pl.sages.javadevpro.projecttwo.external.storage.UserEntityMapper;

import javax.persistence.Lob;
import java.util.Optional;

@RequiredArgsConstructor
@Log
public class StorageAdapter implements UserRepository {

    private final JpaUserRepository userRepository;
    private final UserEntityMapper mapper;

    @Override
    public User save(User user) {
        UserEntity saved = userRepository.save(mapper.toEntity(user));
        log.info("Saved entity " + saved.toString());
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        Optional<UserEntity> entity = userRepository.findByLogin(login);
        log.info("Found entity " + entity.map(Object::toString).orElse("none"));
        if (entity.isPresent()) {
            return entity
                .map(mapper::toDomain);
        }

        return Optional.empty();
    }
}
