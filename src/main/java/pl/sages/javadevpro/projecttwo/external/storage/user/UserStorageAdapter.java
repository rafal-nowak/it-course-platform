package pl.sages.javadevpro.projecttwo.external.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.springframework.dao.DuplicateKeyException;
import pl.sages.javadevpro.projecttwo.domain.exception.DuplicateRecordException;
import pl.sages.javadevpro.projecttwo.domain.exception.RecordNotFoundException;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log
public class UserStorageAdapter implements UserRepository {

    private final MongoUserRepository userRepository;
    private final UserEntityMapper mapper;


    @Override
    public User save(User user) {
        try {
            UserEntity saved = userRepository.insert(mapper.toEntity(user));
            log.info("Saved entity " + saved);
            return mapper.toDomain(saved);
        } catch (DuplicateKeyException ex) {
            log.warning("User " +  user.getEmail() + " already exits in db");
            throw new DuplicateRecordException("User already exits");
        }
    }

    @Override
    public User update(User user) {
        Optional<UserEntity> entity = userRepository.findById(user.getId());
        if (entity.isEmpty()) {
            throw new RecordNotFoundException("Task not found");
        }
        UserEntity updated = userRepository.save(mapper.toEntity(user));
        log.info("Updating task "+ updated);
        return mapper.toDomain(updated);
    }

    @Override
    public void remove(String id) {
        Optional<UserEntity> entity = userRepository.findById(id);
        if(entity.isEmpty()) {
            throw new RecordNotFoundException("User not exist!");
        }
        userRepository.deleteById(id);
        log.info("Removing user " + entity);

    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> entity = userRepository.findByEmail(email);
        if (entity.isEmpty()) {
//            return Optional.empty();
            //TODO przenieść exception na wyższy poziom, tu zwrócić Optional.empty()
            throw new RecordNotFoundException("User not found");
        } else {
            log.info("Found entity " + entity.map(Object::toString).orElse("none"));
            return entity.map(mapper::toDomain);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<UserEntity> entity = userRepository.findById(id);
        if (entity.isPresent()) {
            log.info("Found entity " + entity.map(Object::toString).orElse("none"));
            return entity.map(mapper::toDomain);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

}