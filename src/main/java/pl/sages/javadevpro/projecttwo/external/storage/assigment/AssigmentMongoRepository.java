package pl.sages.javadevpro.projecttwo.external.storage.assigment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssigmentMongoRepository extends MongoRepository<AssigmentEntity, String> {
}
