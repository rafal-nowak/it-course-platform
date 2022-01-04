package pl.sages.javadevpro.projecttwo.external.storage.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepository extends MongoRepository<UserEntity, String> {
}
