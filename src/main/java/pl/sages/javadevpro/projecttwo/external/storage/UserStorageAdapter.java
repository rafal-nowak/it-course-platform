package pl.sages.javadevpro.projecttwo.external.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.sages.javadevpro.projecttwo.domain.exception.RecordNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserEntity;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserEntityMapper;

import java.util.Optional;

@RequiredArgsConstructor
@Log
public class UserStorageAdapter implements UserRepository {

    private final MongoRepository userRepository;
    private final UserEntityMapper mapper;


    @Override
    public User save(User user) {
       userRepository.insert(mapper.toEntity(user));
       UserEntity saved = mapper.toEntity(user);
        log.info("Saved entity " + saved.toString());
        return mapper.toDomain(saved);
    }

    @Override
    public User update(User user) {
        Optional<UserEntity> userEmailId = userRepository.findById(user.getEmail());
        if (userEmailId.isPresent()) {
            userRepository.save(user);
        }
        throw new RecordNotFoundException("User not found");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findById(email);
        if (userEntity.isPresent()) {
            log.info("Found entity " + userEntity.get().toString());
            return userEntity
                .map(mapper::toDomain);
        }
        throw new RecordNotFoundException("User not found");
    }
}
