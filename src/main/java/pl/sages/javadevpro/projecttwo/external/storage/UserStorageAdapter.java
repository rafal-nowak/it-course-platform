package pl.sages.javadevpro.projecttwo.external.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.sages.javadevpro.projecttwo.domain.user.User;
import pl.sages.javadevpro.projecttwo.domain.user.UserRepository;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserEntity;
import pl.sages.javadevpro.projecttwo.external.storage.user.UserEntityMapper;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log
public class UserStorageAdapter implements UserRepository {

    private final MongoRepository userRepository;
    private final MongoTemplate mongoTemplate;
    private final UserEntityMapper mapper;


    @Override
    public User save(User user) {
       userRepository.insert(mapper.toEntity(user));
       UserEntity saved = mapper.toEntity(user);
        log.info("Saved entity " + saved.toString());
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<User> findByEmail(String email) {
      //  userRepository.findById(email);
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
     //  List<UserEntity> students = mongoTemplate.find(query, UserEntity.class);
        UserEntity userEntity = mongoTemplate.findOne(query, UserEntity.class);
        Optional<UserEntity> entity = Optional.ofNullable(userEntity);
        log.info("Found entity " + entity.map(Object::toString).orElse("none"));
        if (entity.isPresent()) {
            return entity
                .map(mapper::toDomain);
        }

        return Optional.empty();
    }
}
