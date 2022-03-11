package pl.sages.javadevpro.projecttwo.external.storage.assigment;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AssigmentMongoRepository extends MongoRepository<AssigmentEntity, String> {

    Optional<AssigmentEntity> findByTaskId(String taskId);

}
